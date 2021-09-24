package com.danylevych.hotel.entity;

public enum BookingStatus {

    PAID,
    UNPAID,
    EXPIRED,
    COMPLETED;
    
    private static final BookingStatus[] VALUES = values();

    public static BookingStatus fromInt(int id) {
	return VALUES[id];
    }
}
