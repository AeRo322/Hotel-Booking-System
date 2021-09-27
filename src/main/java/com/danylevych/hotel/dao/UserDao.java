package com.danylevych.hotel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.danylevych.hotel.entity.User;

public abstract class UserDao extends JdbcDao<User> {

    public static final String TABLE_NAME = "user";

    protected UserDao(DaoFactory daoFactory) {
	super(daoFactory, TABLE_NAME, null, 0);
    }

    public abstract User find(String email, String password);

    @Override
    protected User mapEntity(ResultSet resultSet) {
	try {
	    return new User(resultSet);
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    }

    @Override
    public List<User> list(int limit, int offset, String orderBy,
            boolean isAscending, Object... values) {
	throw new UnsupportedOperationException();
    }

    @Override
    protected String generateSqlFind(int n) {
	throw new UnsupportedOperationException();
    }

    @Override
    protected Object[] getValues(User t) {
	throw new UnsupportedOperationException();
    }
}
