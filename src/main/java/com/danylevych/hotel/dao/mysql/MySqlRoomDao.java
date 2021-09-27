package com.danylevych.hotel.dao.mysql;

import java.sql.Connection;
import java.util.List;

import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.RoomDao;
import com.danylevych.hotel.entity.Room;

public class MySqlRoomDao extends RoomDao {

    public MySqlRoomDao(DaoFactory daoFactory) {
	super(daoFactory);
    }

    @Override
    public void update(Connection c, List<Room> rooms) {
	for (Room room : rooms) {
	    update(c, room);
	}
    }

    @Override
    public List<Room> list(int limit, int offset, String orderBy,
            boolean isAscending, Object... values) {
	String sql = "SELECT *"
	             + " FROM room"
	             + " ORDER BY %s %s"
	             + " LIMIT ? OFFSET ?";

	sql = String.format(sql, orderBy, isAscending ? "ASC" : "DESC");

	return list(sql, limit, offset);
    }

}
