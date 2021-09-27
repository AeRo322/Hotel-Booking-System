package com.danylevych.hotel.dao;

import java.sql.ResultSet;

import com.danylevych.hotel.entity.Order;

public abstract class OrderDao extends JdbcDao<Order> {

    public static final String TABLE_NAME = "order";

    protected OrderDao(DaoFactory daoFactory) {
	super(daoFactory, TABLE_NAME, Order.Column.values(), 3);
    }

    @Override
    protected Order mapEntity(ResultSet resultSet) {
	return new Order(resultSet);
    }

    @Override
    protected Object[] getValues(Order t) {
	return new Object[] {
	    t.getRoomClass().ordinal(),
	    t.getDetails().getId(),
	    t.getStatus().ordinal()
	};
    }
}
