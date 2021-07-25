package com.avaloq.dicedistributionsimulation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avaloq.dicedistributionsimulation.entity.DiceRollEntity;

@Repository
public interface DiceRollRepository extends JpaRepository<DiceRollEntity, Long> {
	
	List<DiceRollEntity> findAllByNumberOfDiceAndNumberOfSidesOfDice(int numberOfDice, int numberOfSidesOfDice);
}