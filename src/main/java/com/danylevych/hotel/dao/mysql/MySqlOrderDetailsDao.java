package com.danylevych.hotel.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.OrderDetailsDao;
import com.danylevych.hotel.entity.OrderDetails;
import com.danylevych.hotel.entity.Room;

public class MySqlOrderDetailsDao extends OrderDetailsDao {

    protected MySqlOrderDetailsDao(DaoFactory daoFactory) {
	super(daoFactory);
    }

    @Override
    public List<Integer> getRoomNumbers(Long orderDetailsId) {
	final String sql = "SELECT room_number"
	                   + " FROM order_details_has_room"
	                   + " WHERE order_details_id = ?";

	List<Integer> list = new ArrayList<>();

	try (Connection c = getConnection();
	     PreparedStatement s = prepareStatement(c, sql, orderDetailsId);
	     ResultSet resultSet = s.executeQuery()) {

	    while (resultSet.next()) {
		final int roomNumber = resultSet.getInt("room_number");
		list.add(roomNumber);
	    }

	} catch (Exception e) {
	    throw new IllegalStateException(e);
	}

	return list;
    }

    @Override
    public void addRoomToOrder(Connection c, OrderDetails orderDetails) {
	final String sql = "INSERT INTO order_details_has_room"
	                   + " VALUES(?, ?, ?)";

	final long orderDetailsId = orderDetails.getId();

	Room room = orderDetails.getRooms().get(0);
	final int price = room.getPrice();
	final int roomNumber = room.getNumber();

	create(c, sql, orderDetailsId, roomNumber, price);
    }

    @Override
    public void addRoomToOrder(OrderDetails orderDetails) {
	final String sql = "INSERT INTO order_details_has_room"
	                   + " VALUES(?, ?, ?)";

	final long orderDetailsId = orderDetails.getId();

	Room room = orderDetails.getRooms().get(0);
	final int roomNumber = room.getNumber();
	final int price = room.getPrice();

	create(sql, orderDetailsId, roomNumber, price);
    }

}
