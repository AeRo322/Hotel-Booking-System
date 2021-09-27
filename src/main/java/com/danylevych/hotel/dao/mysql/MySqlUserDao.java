package com.danylevych.hotel.dao.mysql;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.UserDao;
import com.danylevych.hotel.entity.User;

public class MySqlUserDao extends UserDao {

    public MySqlUserDao(DaoFactory daoFactory) {
	super(daoFactory);
    }

    @Override
    public User find(String email, String password) {
	String sql = "SELECT %s"
	             + " FROM user"
	             + " WHERE email = ? AND password = SHA2(?, 512)";

	final String selectColumns = Stream.of(User.Column.values())
	                                   .map(Enum::name)
	                                   .collect(Collectors.joining(","));

	sql = String.format(sql, selectColumns);

	return find(sql, email, password);
    }

}
