package com.danylevych.hotel.entity;

import static com.danylevych.hotel.entity.Room.Column.CAPACITY;
import static com.danylevych.hotel.entity.Room.Column.CLASS_ID;
import static com.danylevych.hotel.entity.Room.Column.NUMBER;
import static com.danylevych.hotel.entity.Room.Column.PRICE;
import static com.danylevych.hotel.entity.Room.Column.STATUS_ID;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Room implements Serializable {

    private static final long serialVersionUID = -7865967147829710619L;

    private RoomStatus roomStatus;
    private RoomClass roomClass;
    private int capacity;
    private int price;

    private int number;

    public enum Column {

	STATUS_ID,
	CLASS_ID,
	CAPACITY,
	PRICE,

	NUMBER;

	public final String v = name().toLowerCase();

    }

    public Room() {
    }

    public Room(ResultSet resultSet) throws SQLException {
	roomStatus = RoomStatus.fromInt(resultSet.getInt(STATUS_ID.v));
	roomClass = RoomClass.fromInt(resultSet.getInt(CLASS_ID.v));
	capacity = resultSet.getInt(CAPACITY.v);
	number = resultSet.getInt(NUMBER.v);
	price = resultSet.getInt(PRICE.v);
    }
    
    @Override
    public String toString() {
        return String.valueOf(number);
    }
    
    @Override
    public boolean equals(Object obj) {
	if (obj instanceof Room) {
	    Room room = (Room) obj;
	    return number == room.number;
	}
	return false;
    }

    @Override
    public int hashCode() {
	return Objects.hashCode(number);
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

}
