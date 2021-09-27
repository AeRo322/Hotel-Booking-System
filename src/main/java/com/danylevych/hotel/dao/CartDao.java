package com.danylevych.hotel.dao;

import java.sql.ResultSet;

import com.danylevych.hotel.entity.Cart;
import com.danylevych.hotel.entity.User;

public abstract class CartDao extends JdbcDao<Cart> {

    public static final String TABLE_NAME = "cart";

    protected CartDao(DaoFactory daoFactory) {
	super(daoFactory, TABLE_NAME);
    }

    @Override
    protected Cart mapEntity(ResultSet resultSet) {
	    return new Cart(resultSet);
    }

    public abstract void deleteExpiredCarts();

    public abstract Long find(User user);

    public abstract void create(Cart cart);

}
