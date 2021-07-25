package com.avaloq.dicedistributionsimulation.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Response model for querying all results grouped by dice number and dice side")
@Data
public class TotalSimulationsAndRolls {

	@ApiModelProperty(value = "total number of rolls")
	private int totalNumberOfRolls;

	@ApiModelProperty(value = "total number of simulations")
	private int totalNumberOfSimulations;
}