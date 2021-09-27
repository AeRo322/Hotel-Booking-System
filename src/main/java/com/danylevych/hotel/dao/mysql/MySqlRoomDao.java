package com.danylevych.hotel.dao.mysql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.RoomDao;
import com.danylevych.hotel.entity.OrderDetails;
import com.danylevych.hotel.entity.Room;

public class MySqlRoomDao extends RoomDao {

    public MySqlRoomDao(DaoFactory daoFactory) {
	super(daoFactory);
    }

    @Override
    public List<Room> list(int limit, int offset, String orderBy,
            boolean isAscending, Object... values)  {
	String sql = "SELECT *"
	             + " FROM room"
	             + " ORDER BY %s %s"
	             + " LIMIT ? OFFSET ?";

	sql = String.format(sql, orderBy, isAscending ? "ASC" : "DESC");

	return list(sql, limit, offset);
    }

    @Override
    public List<Room> find(Integer... roomNumbers)  {
	String sql = "SELECT *"
	             + " FROM room"
	             + " WHERE number = ?";

	List<Room> rooms = new ArrayList<>();

	for (Integer roomNumber : roomNumbers) {
	    Room room = find(sql, roomNumber);
	    rooms.add(room);
	}

	return rooms;
    }

    @Override
    public void update(Connection c, OrderDetails details)  {
	for (Room room : details.getRooms()) {
	    update(c, room);
	}
    }

    @Override
    public void update(Connection c, Room room)  {
	String sql = "UPDATE room"
	             + " SET"
	             + " status_id = ?"
	             + " WHERE number = ?";

	final int ordinal = room.getRoomStatus().ordinal();
	final int number = room.getNumber();

	update(c, sql, ordinal, number);
    }

}
