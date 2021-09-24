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
}
