package com.danylevych.hotel.util;

public final class SQL {

    private SQL() {

    }

    public static String formatSql(Object[] objects) {
	StringBuilder stringBuilder = new StringBuilder();
	for (Object object : objects) {
	    stringBuilder.append(object).append(',');
	}
	stringBuilder.deleteCharAt(stringBuilder.length() - 1);
	return stringBuilder.toString();
    }
}
