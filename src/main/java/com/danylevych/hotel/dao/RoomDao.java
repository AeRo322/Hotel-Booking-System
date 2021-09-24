package com.danylevych.hotel.dao;

import static com.danylevych.hotel.entity.Room.Column.CAPACITY;
import static com.danylevych.hotel.entity.Room.Column.CLASS_ID;
import static com.danylevych.hotel.entity.Room.Column.NUMBER;
import static com.danylevych.hotel.entity.Room.Column.PRICE;
import static com.danylevych.hotel.entity.Room.Column.STATUS_ID;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.danylevych.hotel.entity.Room;
import com.danylevych.hotel.entity.RoomClass;
import com.danylevych.hotel.entity.RoomStatus;

public abstract class RoomDao extends JdbcDao<Room> {

    public static final String TABLE_NAME = "room";

    protected RoomDao(DaoFactory daoFactory) {
	super(daoFactory, TABLE_NAME);
    }

    @Override
    protected Room mapEntity(ResultSet resultSet) {
	Room room = new Room();

	try {
	    room.setPrice(resultSet.getInt(PRICE.v));
	    room.setNumber(resultSet.getInt(NUMBER.v));
	    room.setCapacity(resultSet.getInt(CAPACITY.v));
	    room.setRoomClass(RoomClass.fromInt(resultSet.getInt(CLASS_ID.v)));
	    room.setRoomStatus(
	            RoomStatus.fromInt(resultSet.getInt(STATUS_ID.v)));
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}

	return room;
    }
}
