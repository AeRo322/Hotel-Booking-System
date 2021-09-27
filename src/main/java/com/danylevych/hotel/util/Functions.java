package com.danylevych.hotel.util;

import java.util.function.Function;

public final class Functions {

    private Functions() {

    }

    public static <T, R> R applyOrDefault(T t, Function<T, R> f,
            R defaultValue) {

	if (t != null) {
	    try {
		return f.apply(t);
	    } catch (Exception e) {
		Loggers.log(e);
	    }
	}

	return defaultValue;
    }

    public static String formatSql(Object... values) {
	StringBuilder stringBuilder = new StringBuilder(values.length * 2 - 1);
	for (int i = 0; i < values.length; i++) {
	    stringBuilder.append('?').append(',');
	}
	stringBuilder.deleteCharAt(stringBuilder.length());
	return stringBuilder.toString();
    }

}
