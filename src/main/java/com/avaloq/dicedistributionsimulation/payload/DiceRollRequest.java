package com.avaloq.dicedistributionsimulation.payload;

import static com.avaloq.dicedistributionsimulation.constants.ApiConstraints.NUMBER_OF_DICE_MAX_VALUE;
import static com.avaloq.dicedistributionsimulation.constants.ApiConstraints.NUMBER_OF_DICE_MIN_VALUE;
import static com.avaloq.dicedistributionsimulation.constants.ApiConstraints.NUMBER_OF_ROLLS_MAX_VALUE;
import static com.avaloq.dicedistributionsimulation.constants.ApiConstraints.NUMBER_OF_ROLLS_MIN_VALUE;
import static com.avaloq.dicedistributionsimulation.constants.ApiConstraints.NUMBER_OF_SIDES_OF_DICE_MAX_VALUE;
import static com.avaloq.dicedistributionsimulation.constants.ApiConstraints.NUMBER_OF_SIDES_OF_DICE_MIN_VALUE;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Request model for rolling dice")
@Data
public class DiceRollRequest {

	@Min(NUMBER_OF_DICE_MIN_VALUE)
	@Max(NUMBER_OF_DICE_MAX_VALUE)
	@ApiModelProperty(value = "number of dice", required = true, allowableValues = NUMBER_OF_DICE_MIN_VALUE + "-" + NUMBER_OF_DICE_MAX_VALUE)
	private int numberOfDice;

	@Min(NUMBER_OF_SIDES_OF_DICE_MIN_VALUE)
	@Max(NUMBER_OF_SIDES_OF_DICE_MAX_VALUE)
	@ApiModelProperty(value = "number of dice", required = true, allowableValues = NUMBER_OF_SIDES_OF_DICE_MIN_VALUE + "-" + NUMBER_OF_SIDES_OF_DICE_MAX_VALUE)
	private int numberOfSidesOfDice;

	@Min(NUMBER_OF_ROLLS_MIN_VALUE)
	@Max(NUMBER_OF_ROLLS_MAX_VALUE)
	@ApiModelProperty(value = "number of dice", required = true, allowableValues = NUMBER_OF_ROLLS_MIN_VALUE + "-" + NUMBER_OF_ROLLS_MAX_VALUE)
	private int numberOfRolls;
}