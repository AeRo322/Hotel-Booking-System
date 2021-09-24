package com.danylevych.hotel.util;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Enums {

    private static final Map<Class<? extends Enum<?>>, String> SEQUENCES =
            new HashMap<>();

    private Enums() {

    }

    public static synchronized <T extends Enum<?>> String
           getSequence(Class<T> e) {
	return SEQUENCES.computeIfAbsent(e, Enums::createSequence);
    }

    private static String createSequence(Class<? extends Enum<?>> e) {
	final Enum<?>[] values = e.getEnumConstants();
	return Stream.of(values)
	             .map(Enum::name)
	             .collect(Collectors.joining(","));
    }

}
