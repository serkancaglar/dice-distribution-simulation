package com.avaloq.dicedistributionsimulation.controller;

import static com.avaloq.dicedistributionsimulation.constants.ApiConstraints.NUMBER_OF_DICE_MAX_VALUE;
import static com.avaloq.dicedistributionsimulation.constants.ApiConstraints.NUMBER_OF_DICE_MIN_VALUE;
import static com.avaloq.dicedistributionsimulation.constants.ApiConstraints.NUMBER_OF_SIDES_OF_DICE_MAX_VALUE;
import static com.avaloq.dicedistributionsimulation.constants.ApiConstraints.NUMBER_OF_SIDES_OF_DICE_MIN_VALUE;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaloq.dicedistributionsimulation.payload.TotalSimulationsAndRolls;
import com.avaloq.dicedistributionsimulation.service.DiceRollService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(path = "/get", produces = "application/json")
@Api(value = "Query Dice Roll Results")
@RestController
public class QueryResultsController {

	private final DiceRollService diceRollService;

	@GetMapping("/all")
	@ApiOperation(value = "Gets all results grouped by dice number and dice side", notes = "Result is of type map where key is diceNumber-diceSide and value is total number of rolls and simulations", response = Map.class, responseContainer = "ResponseEntity")
	public ResponseEntity<Map<String, TotalSimulationsAndRolls>> getAllGroupedByDiceNumberAndDiceSide() {
		Map<String, TotalSimulationsAndRolls> result = diceRollService.getAllGroupedByDiceNumberAndDiceSide();

		return ResponseEntity.ok(result);
	}

	@GetMapping("/relative-distribution/{numberOfDice}/{numberOfSidesOfDice}")
	@ApiOperation(value = "Gets the relative distribution compared to the rolls by given dice number and dice side combination", notes = "Result is of type map where key is sum of a roll and value is the relative distribution", response = Map.class, responseContainer = "ResponseEntity")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid request. Please provide appropriate numberOfDice and numberOfSidesOfDice") })
	public ResponseEntity<Map<Long, Double>> getRelativeDistribution(
			@Valid @Min(NUMBER_OF_DICE_MIN_VALUE) @Max(NUMBER_OF_DICE_MAX_VALUE) @ApiParam(value = "number of dice", required = true) @PathVariable("numberOfDice") int numberOfDice,
			@Valid @Min(NUMBER_OF_SIDES_OF_DICE_MIN_VALUE) @Max(NUMBER_OF_SIDES_OF_DICE_MAX_VALUE) @ApiParam(value = "number of sides of dice", required = true) @PathVariable("numberOfSidesOfDice") int numberOfSidesOfDice) {
		Map<Long, Double> result = diceRollService.getRelativeDistribution(numberOfDice, numberOfSidesOfDice);

		return ResponseEntity.ok(result);
	}
}