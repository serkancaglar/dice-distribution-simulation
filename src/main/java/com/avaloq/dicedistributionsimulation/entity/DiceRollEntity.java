package com.avaloq.dicedistributionsimulation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dice_roll", indexes = @Index(columnList = "number_of_dice, number_of_sides_of_dice"))
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiceRollEntity {

	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "number_of_dice")
	private int numberOfDice;

	@Column(name = "number_of_sides_of_dice")
	private int numberOfSidesOfDice;

	@Column(name = "number_of_rolls")
	private int numberOfRolls;

	@Column(name = "simulation_result")
	@Lob
	private String simulationResult;
}