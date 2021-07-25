package com.avaloq.dicedistributionsimulation.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.avaloq.dicedistributionsimulation.payload.DiceRollRequest;
import com.avaloq.dicedistributionsimulation.service.DiceRollService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(path = "/roll", produces = "application/json")
@Api(value = "Roll Dice")
@RestController
public class DiceRollController {

	private final DiceRollService diceRollService;

	@PostMapping("/custom")
	@ApiOperation(value = "Rolls dice with the input provided", notes = "Result is of type map where key is sum of the roll and value is the count for this sum. Saves result to db.", response = String.class, responseContainer = "ResponseEntity")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid request. Please provide appropriate numberOfDice, numberOfSidesOfDice and numberOfRolls") })
	public ResponseEntity<String> roll(@Valid @RequestBody DiceRollRequest request) {
		int numberOfDice = request.getNumberOfDice();
		int numberOfSidesOfDice = request.getNumberOfSidesOfDice();
		int numberOfRolls = request.getNumberOfRolls();

		String simulationResult = diceRollService.roll(numberOfDice, numberOfSidesOfDice, numberOfRolls);

		diceRollService.save(numberOfDice, numberOfSidesOfDice, numberOfRolls, simulationResult);

		return ResponseEntity.ok(simulationResult);
	}

	@PostMapping("/default")
	@ApiOperation(value = "Rolls dice with the default values. numberOfDice=3, numberOfSidesOfDice=6 and numberOfRolls=100", notes = "Result is of type map where key is sum of the roll and value is the count for this sum. Saves result to db.", response = String.class, responseContainer = "ResponseEntity")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid request. Please provide appropriate numberOfDice, numberOfSidesOfDice and numberOfRolls") })
	public ResponseEntity<String> rollWithDefaultValues() {
		DiceRollRequest request = new DiceRollRequest();
		request.setNumberOfDice(3);
		request.setNumberOfSidesOfDice(6);
		request.setNumberOfRolls(100);

		return roll(request);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}