package com.danylevych.hotel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.danylevych.hotel.entity.User;

public abstract class UserDao extends JdbcDao<User> {

    public static final String TABLE_NAME = "user";

    protected UserDao(DaoFactory daoFactory) {
	super(daoFactory, TABLE_NAME);
    }

    public abstract User find(String email, String password)
            ;

    @Override
    protected User mapEntity(ResultSet resultSet) {
	try {
	    return new User(resultSet);
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    }

}
