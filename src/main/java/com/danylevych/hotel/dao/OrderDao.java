package com.danylevych.hotel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.danylevych.hotel.entity.Order;

public abstract class OrderDao extends JdbcDao<Order> {

    protected OrderDetailsDao orderDetailsDao = daoFactory.getOrderDetailsDao();

    public static final String TABLE_NAME = "order";

    protected OrderDao(DaoFactory daoFactory) {
	super(daoFactory, TABLE_NAME);
    }

    public abstract void create(Order order) throws DaoException;

    public abstract void update(Order order) throws DaoException;

    public abstract Order find(long id) throws DaoException;

    @Override
    protected Order mapEntity(ResultSet resultSet) {
	try {
	    return new Order(resultSet);
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    }

}
