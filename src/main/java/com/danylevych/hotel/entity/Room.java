package com.danylevych.hotel.entity;

import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Room implements Entity {

    private static final long serialVersionUID = -7865967147829710619L;

    private RoomStatus roomStatus;
    private RoomClass roomClass;

    private int capacity;
    private int number;
    private int price;

    public enum Column {
	
	PRICE,
	NUMBER,
	CAPACITY,
	CLASS_ID,
	STATUS_ID;

	public final String v = name().toLowerCase();

	private static final String SEQUENCE =
	        Stream.of(values())
	              .map(Column::toString)
	              .collect(Collectors.joining(","));

	public static String toSequence() {
	    return SEQUENCE;
	}

    }

    public RoomClass getRoomClass() {
	return roomClass;
    }

    public void setRoomClass(RoomClass roomClass) {
	this.roomClass = roomClass;
    }

    public RoomStatus getRoomStatus() {
	return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
	this.roomStatus = roomStatus;
    }

    public int getCapacity() {
	return capacity;
    }

    public void setCapacity(int capacity) {
	this.capacity = capacity;
    }

    public int getNumber() {
	return number;
    }

    public void setNumber(int number) {
	this.number = number;
    }

    public int getPrice() {
	return price;
    }

    public void setPrice(int price) {
	this.price = price;
    }

    @Override
    public Object[] extract() {
	// TODO Auto-generated method stub
	return null;
    }

}
