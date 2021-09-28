package com.danylevych.hotel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.danylevych.hotel.entity.User;
import com.danylevych.hotel.util.SQL;

public abstract class UserDao extends JdbcDao<User> {

    public static final String TABLE_NAME = "user";

    protected UserDao(DaoFactory daoFactory) {
	super(daoFactory, TABLE_NAME, User.Column.values(), 5);
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
            boolean isAscending, User user) {
	throw new UnsupportedOperationException();
    }

    @Override
    protected String generateSqlFind(int n) {
	return SQL.generateSqlFind(User.Column.ID, n, TABLE_NAME);
    }

    @Override
    protected Object[] getValues(User t) {
	return new Object[] {
	    t.getRole().ordinal(),
	    t.getFirstName(),
	    t.getLastName(),
	    t.getEmail(),
	    t.getPassword()
	};
    }

    @Override
    protected Object getWhereValue(User t) {
	return t.getId();
    }
}
