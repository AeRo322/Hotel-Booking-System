package com.danylevych.hotel.entity;

public enum OrderStatus {

    NEW,
    PENDING,
    DECLINED,
    ACCEPTED,
    CANCELED;

    private static final OrderStatus[] VALUES = values();

    public static OrderStatus fromInt(int id) {
	return VALUES[id];
    }

}
