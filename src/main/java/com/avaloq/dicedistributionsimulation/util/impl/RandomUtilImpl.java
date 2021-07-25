package com.avaloq.dicedistributionsimulation.util.impl;

import java.util.SplittableRandom;

import org.springframework.stereotype.Component;

import com.avaloq.dicedistributionsimulation.util.RandomUtil;

@Component
public class RandomUtilImpl implements RandomUtil {

	private final ThreadLocal<SplittableRandom> splittableRandom = new ThreadLocal<SplittableRandom>() {
		@Override
		protected SplittableRandom initialValue() {
			return new SplittableRandom();
		}
	};

	@Override
	public int generate(int bound) {
		return splittableRandom.get().nextInt(bound) + 1;
	}
}