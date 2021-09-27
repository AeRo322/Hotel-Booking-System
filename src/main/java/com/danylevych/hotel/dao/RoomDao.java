package com.danylevych.hotel.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.danylevych.hotel.entity.OrderDetails;
import com.danylevych.hotel.entity.Room;

public abstract class RoomDao extends JdbcDao<Room> {

    public static final String TABLE_NAME = "room";

    protected RoomDao(DaoFactory daoFactory) {
	super(daoFactory, TABLE_NAME);
    }

    @Override
    protected Room mapEntity(ResultSet resultSet) {
	try {
	    return new Room(resultSet);
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    }

    public abstract void update(Connection c, Room room);

    public abstract void update(Connection c, OrderDetails details);

    public abstract List<Room> find(Integer... roomNumbers);

}
