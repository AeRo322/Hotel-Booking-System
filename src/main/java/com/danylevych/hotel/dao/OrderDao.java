package com.danylevych.hotel.dao;

import java.sql.ResultSet;

import com.danylevych.hotel.entity.Order;
import com.danylevych.hotel.util.SQL;

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

    @Override
    protected String generateSqlFind(int n) {
	return SQL.generateSqlFind(TABLE_NAME
	                           + "."
	                           + Order.Column.ID,
	        n, TABLE_NAME, OrderDetailsDao.TABLE_NAME);
    }

    @Override
    protected Object getWhereValue(Order t) {
	return t.getId();
    }
}
