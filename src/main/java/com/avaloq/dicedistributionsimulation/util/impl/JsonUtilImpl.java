package com.avaloq.dicedistributionsimulation.util.impl;

import java.lang.reflect.Type;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.avaloq.dicedistributionsimulation.util.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class JsonUtilImpl implements JsonUtil {

	// Since we won't be dealing with dates, using this gson instance is thread-safe
	private final Gson gson = new Gson();

	@Override
	public String convertToJson(Object obj) {
		return gson.toJson(obj);
	}

	@Override
	public Map<Long, Integer> convertToMap(String json) {
		Type type = new TypeToken<Map<Long, Integer>>() {
		}.getType();
		return gson.fromJson(json, type);
	}
}