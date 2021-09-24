package com.danylevych.hotel.entity;

public enum UserRole {

    MANAGER,
    CLIENT;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
    
    private static final UserRole[] VALUES = values();

    public static UserRole fromInt(int id) {
	return VALUES[id];
    }
}