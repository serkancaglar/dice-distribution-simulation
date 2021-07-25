package com.avaloq.dicedistributionsimulation.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.avaloq.dicedistributionsimulation.entity.DiceRollEntity;
import com.avaloq.dicedistributionsimulation.payload.TotalSimulationsAndRolls;
import com.avaloq.dicedistributionsimulation.repository.DiceRollRepository;
import com.avaloq.dicedistributionsimulation.service.DiceRollService;
import com.avaloq.dicedistributionsimulation.util.JsonUtil;
import com.avaloq.dicedistributionsimulation.util.RandomUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiceRollServiceImpl implements DiceRollService {

	private final DiceRollRepository diceRollRepository;
	private final JsonUtil jsonUtil;
	private final RandomUtil randomUtil;

	@Override
	public String roll(int numberOfDice, int numberOfSidesOfDice, int numberOfRolls) {
		// Key is sum of the rolled numbers in a roll. Value is the count of each possible sum.
		Map<Long, Integer> sumMap = new TreeMap<>();

		for (int i = 0; i < numberOfRolls; i++) {
			long sum = 0;
			for (int j = 0; j < numberOfDice; j++) {
				int rolledNumber = randomUtil.generate(numberOfSidesOfDice);
				sum += rolledNumber;
			}

			sumMap.compute(sum, (k, v) -> (v == null) ? 1 : ++v);
		}

		return jsonUtil.convertToJson(sumMap);
	}

	@Override
	public void save(int numberOfDice, int numberOfSidesOfDice, int numberOfRolls, String simulationResult) {
		// @formatter:off
		DiceRollEntity entity = DiceRollEntity.builder()
			.numberOfDice(numberOfDice)
			.numberOfSidesOfDice(numberOfSidesOfDice)
			.numberOfRolls(numberOfRolls)
			.simulationResult(simulationResult)
			.build();
		// @formatter:on

		diceRollRepository.save(entity);
	}

	@Override
	public Map<String, TotalSimulationsAndRolls> getAllGroupedByDiceNumberAndDiceSide() {
		List<DiceRollEntity> allEntities = diceRollRepository.findAll();

		Map<String, TotalSimulationsAndRolls> result = new LinkedHashMap<>();
		StringJoiner sj;
		for (DiceRollEntity entity : allEntities) {
			sj = new StringJoiner("-");
			String key = sj.add(String.valueOf(entity.getNumberOfDice())).add(String.valueOf(entity.getNumberOfSidesOfDice())).toString();
			if (result.containsKey(key)) {
				TotalSimulationsAndRolls value = result.get(key);
				value.setTotalNumberOfRolls(value.getTotalNumberOfRolls() + entity.getNumberOfRolls());
				value.setTotalNumberOfSimulations(value.getTotalNumberOfSimulations() + 1);
			} else {
				TotalSimulationsAndRolls value = new TotalSimulationsAndRolls();
				value.setTotalNumberOfRolls(entity.getNumberOfRolls());
				value.setTotalNumberOfSimulations(1);
				result.put(key, value);
			}
		}

		return result;
	}

	@Override
	public Map<Long, Double> getRelativeDistribution(int numberOfDice, int numberOfSidesOfDice) {
		List<DiceRollEntity> entities = diceRollRepository.findAllByNumberOfDiceAndNumberOfSidesOfDice(numberOfDice, numberOfSidesOfDice);

		long totalRolls = 0;

		Map<Long, Long> totalRollsSumMap = new HashMap<>();
		for (DiceRollEntity entity : entities) {
			totalRolls += entity.getNumberOfRolls();

			Map<Long, Integer> sumMap = jsonUtil.convertToMap(entity.getSimulationResult());
			for (Entry<Long, Integer> entry : sumMap.entrySet()) {
				if (totalRollsSumMap.containsKey(entry.getKey())) {
					totalRollsSumMap.put(entry.getKey(), totalRollsSumMap.get(entry.getKey()) + entry.getValue());
				} else {
					totalRollsSumMap.put(entry.getKey(), entry.getValue().longValue());
				}
			}
		}

		return computeRelativeDistribution(totalRollsSumMap, totalRolls);
	}

	private Map<Long, Double> computeRelativeDistribution(Map<Long, Long> totalRollsSumMap, final long totalRolls) {
		// @formatter:off
		return totalRollsSumMap
				.entrySet()
				.stream()
				.collect(
						Collectors.toMap(
								Entry::getKey,
								e -> e.getValue().doubleValue() / totalRolls,
								(oldValue, newValue) -> newValue,
								TreeMap::new));
		// @formatter:on
	}
}