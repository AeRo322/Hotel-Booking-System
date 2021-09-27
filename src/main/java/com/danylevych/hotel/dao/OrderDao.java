package com.danylevych.hotel.dao;

import java.sql.ResultSet;

import com.danylevych.hotel.entity.Order;

public abstract class OrderDao extends JdbcDao<Order> {

    public static final String TABLE_NAME = "order";

    protected OrderDao(DaoFactory daoFactory) {
	super(daoFactory, TABLE_NAME);
    }

    public abstract void create(Order order);

    public abstract void update(Order order);

    public abstract Order find(long id);

    @Override
    protected Order mapEntity(ResultSet resultSet) {
	return new Order(resultSet);
    }

}
