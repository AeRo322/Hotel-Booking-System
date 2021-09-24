package com.danylevych.hotel.dao.mysql;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.danylevych.hotel.dao.DaoException;
import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.OrderDetailsDao;
import com.danylevych.hotel.entity.OrderDetails;

public class MySqlOrderDetailsDao extends OrderDetailsDao {

    protected MySqlOrderDetailsDao(DaoFactory daoFactory) {
	super(daoFactory);
    }

    @Override
    public long create(Connection connection, OrderDetails orderDetails) {
	String sql = "INSERT INTO order_details"
	             + " (user_id, guests, check_in, check_out)"
	             + " VALUES(?, ?, ?, ?)";

	int userId = orderDetails.getUserId();
	int guests = orderDetails.getGuests();
	Date checkIn = orderDetails.getCheckIn();
	Date checkOut = orderDetails.getCheckOut();

	try {
	    return create(connection, true, sql, userId, guests, checkIn,
	            checkOut);
	} catch (DaoException e) {
	    throw new IllegalStateException(e);
	}
    }

    @Override
    public List<OrderDetails> list(int limit, int offset, String orderBy,
            boolean isAscending) throws DaoException {
	return null;
    }

}
