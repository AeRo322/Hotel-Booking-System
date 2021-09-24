package com.danylevych.hotel.entity;

public enum RoomStatus {

    AVAILABLE,
    BOOKED,
    OCCUPIED,
    UNAVAILABLE;

    private static final RoomStatus[] VALUES = values();

    public static RoomStatus fromInt(int id) {
	return VALUES[id];
    }
}
