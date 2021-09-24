package com.danylevych.hotel.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Validator {

    private static final List<String> ORDER_VALID_SORT_FIELDS =
            Arrays.asList("create_time", "check_in", "check_out", "guests",
                    "room_class_id", "status_id");

    private static final List<String> ROOM_VALID_SORT_FIELDS = Arrays.asList(
            "number", "price", "status_id", "capacity", "class_id");

    private static final Map<String, List<String>> VALID_SORT_FIELDS =
            new HashMap<>();

    static {
	VALID_SORT_FIELDS.put("order", ORDER_VALID_SORT_FIELDS);
	VALID_SORT_FIELDS.put("room", ROOM_VALID_SORT_FIELDS);
    }

    private Validator() {

    }

    public static boolean validateEmail(String email) {
	return !email.isEmpty();
    }

    public static boolean validateSortField(String entity, String sortField) {
	return VALID_SORT_FIELDS.get(entity).contains(sortField);
    }
}
