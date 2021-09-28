package com.danylevych.hotel.dao;

import java.sql.ResultSet;

import com.danylevych.hotel.entity.Cart;
import com.danylevych.hotel.util.SQL;

public abstract class CartDao extends JdbcDao<Cart> {

    public static final String TABLE_NAME = "cart";

    protected CartDao(DaoFactory daoFactory) {
	super(daoFactory, TABLE_NAME, Cart.Column.values(), 2);
    }

    public abstract void deleteExpiredCarts();

    @Override
    protected Cart mapEntity(ResultSet resultSet) {
	return new Cart(resultSet);
    }

    @Override
    protected String generateSqlFind(int n) {
	return SQL.generateSqlFind(TABLE_NAME
	                           + "."
	                           + Cart.Column.USER_ID,
	        n, TABLE_NAME, OrderDetailsDao.TABLE_NAME);
    }

    @Override
    protected Object[] getValues(Cart t) {
	return new Object[] {
	    t.getUserId(),
	    t.getOrderDetails().getId(),
	};
    }

    @Override
    protected Object getWhereValue(Cart t) {
	return t.getUserId();
    }
}
