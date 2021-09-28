package com.danylevych.hotel.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.danylevych.hotel.entity.Room;
import com.danylevych.hotel.util.SQL;

public abstract class RoomDao extends JdbcDao<Room> {

    public static final String TABLE_NAME = "room";

    protected RoomDao(DaoFactory daoFactory) {
	super(daoFactory, TABLE_NAME, Room.Column.values(), 4);
    }

    public abstract void update(Connection c, List<Room> details);

    @Override
    protected Room mapEntity(ResultSet resultSet) {
	try {
	    return new Room(resultSet);
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    }

    @Override
    protected String generateSqlFind(int n) {
	return SQL.generateSqlFind(Room.Column.NUMBER, n, TABLE_NAME);
    }

    @Override
    protected Object[] getValues(Room t) {
	return new Object[] {
	    t.getRoomStatus().ordinal(),
	    t.getRoomClass().ordinal(),
	    t.getCapacity(),
	    t.getPrice()
	};
    }

    @Override
    protected Object getWhereValue(Room t) {
	return t.getNumber();
    }
}
