package com.avaloq.dicedistributionsimulation.service;

import java.util.Map;

import com.avaloq.dicedistributionsimulation.payload.TotalSimulationsAndRolls;

public interface DiceRollService {

	String roll(int numberOfDice, int numberOfSidesOfDice, int numberOfRolls);

	void save(int numberOfDice, int numberOfSidesOfDice, int numberOfRolls, String simulationResult);

	Map<String, TotalSimulationsAndRolls> getAllGroupedByDiceNumberAndDiceSide();

	Map<Long, Double> getRelativeDistribution(int numberOfDice, int numberOfSidesOfDice);
}