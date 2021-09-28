package com.danylevych.hotel.dao.mysql;

import java.sql.Connection;
import java.util.List;

import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.RoomDao;
import com.danylevych.hotel.entity.Room;
import com.danylevych.hotel.entity.RoomStatus;
import com.danylevych.hotel.entity.User;
import com.danylevych.hotel.entity.UserRole;

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
            boolean isAscending, User user) {
	if (user != null && user.getRole() == UserRole.MANAGER) {
	    String sql = "SELECT *"
	                 + " FROM room"
	                 + " WHERE status_id = %d";

	    sql = String.format(sql, RoomStatus.AVAILABLE.ordinal());

	    return list(sql);
	} else {
	    String sql = "SELECT *"
	                 + " FROM room"
	                 + " ORDER BY %s %s"
	                 + " LIMIT ? OFFSET ?";

	    sql = String.format(sql, orderBy, isAscending ? "ASC" : "DESC");

	    return list(sql, limit, offset);
	}
    }

    @Override
    public int count(Object... values) {
	final String sql = "SELECT COUNT(*)"
	                   + " FROM room";

	return count(sql);
    }
}
