package com.danylevych.hotel.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.danylevych.hotel.entity.OrderDetails;

public abstract class OrderDetailsDao extends JdbcDao<OrderDetails> {

    public static final String TABLE_NAME = "order_details";

    protected OrderDetailsDao(DaoFactory daoFactory) {
	super(daoFactory, TABLE_NAME);
    }

    @Override
    protected OrderDetails mapEntity(ResultSet resultSet) {
	OrderDetails orderDetails = new OrderDetails();

	try {
	    resultSet.getDate("create_time");
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}

	return orderDetails;
    }

    public abstract long create(Connection connection,
            OrderDetails orderDetails);

}
