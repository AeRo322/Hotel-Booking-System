package com.danylevych.hotel.dao.mysql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.OrderDetailsDao;
import com.danylevych.hotel.entity.OrderDetails;
import com.danylevych.hotel.entity.Room;
import com.danylevych.hotel.entity.RoomStatus;

public class MySqlOrderDetailsDao extends OrderDetailsDao {

    protected MySqlOrderDetailsDao(DaoFactory daoFactory) {
	super(daoFactory);
    }

    @Override
    public long create(Connection connection, OrderDetails orderDetails) {
	final String sql = "INSERT INTO order_details"
	                   + " (user_id, guests, check_in, check_out)"
	                   + " VALUES(?, ?, ?, ?)";

	int userId = orderDetails.getUserId();
	int guests = orderDetails.getGuests();
	Date checkIn = orderDetails.getCheckIn();
	Date checkOut = orderDetails.getCheckOut();

	return create(connection, true, sql, userId, guests, checkIn, checkOut);
    }

    @Override
    public long create(OrderDetails orderDetails) {
	final String sql = "INSERT INTO order_details"
	                   + " (user_id)"
	                   + " VALUES(?)";

	Connection c = null;

	try {
	    c = daoFactory.getConnection();
	    c.setAutoCommit(false);

	    final int userId = orderDetails.getUserId();
	    final long orderDetailsId = create(c, true, sql, userId);
	    orderDetails.setId(orderDetailsId);

	    addRoomToOrder(c, orderDetails);

	    c.commit();
	    return orderDetailsId;
	} catch (Exception e) {
	    rollback(c);
	    throw new IllegalStateException(e);
	} finally {
	    close(c);
	}
    }

    @Override
    public void addRoomToOrder(Connection c, OrderDetails orderDetails) {
	final String sql = "INSERT INTO order_details_has_room"
	                   + " VALUES(?, ?, ?)";

	final long orderDetailsId = orderDetails.getId();

	final Room room = orderDetails.getRooms().get(0);
	final int price = room.getPrice();
	final int roomNumber = room.getNumber();

	create(c, sql, orderDetailsId, roomNumber, price);
    }

    @Override
    public void addRoomToOrder(OrderDetails orderDetails) {
	final String sql = "INSERT INTO order_details_has_room"
	                   + " VALUES(?, ?, ?)";

	transaction(c -> {
	    final long orderDetailsId = orderDetails.getId();

	    Room room = orderDetails.getRooms().get(0);
	    final int roomNumber = room.getNumber();
	    final int price = room.getPrice();

	    create(sql, orderDetailsId, roomNumber, price);
	});
    }

    @Override
    public List<OrderDetails> list(int limit, int offset, String orderBy,
            boolean isAscending, Object... values) {
	return new ArrayList<>();
    }

    @Override
    public void remove(Long orderdId) {
	final String sql = "DELETE FROM order_details"
	                   + " WHERE id = ?";

	transaction(c -> {
	    List<Integer> rooms = getRoomNumbers(orderdId);
	    for (Integer number : rooms) {
		Room room = new Room();
		room.setNumber(number);
		room.setRoomStatus(RoomStatus.AVAILABLE);
		daoFactory.getRoomDao().update(c, room);
	    }
	    update(c, sql, orderdId);
	});
    }

    @Override
    public void update(Connection c, OrderDetails orderDetails) {
	final String sql = "UPDATE order_details"
	                   + " SET"
	                   + " guests = ?,"
	                   + " check_in = ?,"
	                   + " check_out = ?"
	                   + " WHERE id = ?";

	int guests = orderDetails.getGuests();
	Date checkIn = orderDetails.getCheckIn();
	Date checkOut = orderDetails.getCheckOut();

	long id = orderDetails.getId();

	update(c, sql, guests, checkIn, checkOut, id);
    }

}
