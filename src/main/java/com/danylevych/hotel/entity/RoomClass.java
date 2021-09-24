package com.danylevych.hotel.entity;

public enum RoomClass {

    A,
    B,
    C,
    D;

    private static final RoomClass[] VALUES = values();

    public static RoomClass fromInt(int id) {
	return VALUES[id];
    }
}
