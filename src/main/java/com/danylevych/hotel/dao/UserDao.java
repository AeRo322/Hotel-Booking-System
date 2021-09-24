package com.danylevych.hotel.dao;

import static com.danylevych.hotel.entity.User.Column.CREATE_TIME;
import static com.danylevych.hotel.entity.User.Column.EMAIL;
import static com.danylevych.hotel.entity.User.Column.FIRST_NAME;
import static com.danylevych.hotel.entity.User.Column.ID;
import static com.danylevych.hotel.entity.User.Column.LAST_NAME;
import static com.danylevych.hotel.entity.User.Column.ROLE_ID;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.danylevych.hotel.entity.User;
import com.danylevych.hotel.entity.UserRole;

public abstract class UserDao extends JdbcDao<User> {

    public static final String TABLE_NAME = "user";

    protected UserDao(DaoFactory daoFactory) {
	super(daoFactory, TABLE_NAME);
    }

    public abstract User find(String email, String password)
            throws DaoException;

    @Override
    protected User mapEntity(ResultSet resultSet) {
	User user = new User();

	try {
	    user.setId(resultSet.getInt(ID.v));
	    user.setEmail(resultSet.getString(EMAIL.v));
	    user.setLastName(resultSet.getString(LAST_NAME.v));
	    user.setFirstName(resultSet.getString(FIRST_NAME.v));
	    user.setCreateTime(resultSet.getDate(CREATE_TIME.v));
	    user.setRole(UserRole.fromInt(resultSet.getInt(ROLE_ID.v)));
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}

	return user;
    }

}
