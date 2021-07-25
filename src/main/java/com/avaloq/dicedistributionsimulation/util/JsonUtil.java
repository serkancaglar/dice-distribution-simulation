package com.avaloq.dicedistributionsimulation.util;

import java.util.Map;

public interface JsonUtil {

	String convertToJson(Object obj);

	Map<Long, Integer> convertToMap(String json);
}