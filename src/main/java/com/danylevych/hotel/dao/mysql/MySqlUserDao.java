package com.danylevych.hotel.dao.mysql;

import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.UserDao;
import com.danylevych.hotel.entity.User;

public class MySqlUserDao extends UserDao {

    public MySqlUserDao(DaoFactory daoFactory) {
	super(daoFactory);
    }

    @Override
    public User find(String email, String password) {
	String sql = "SELECT *"
	             + " FROM user"
	             + " WHERE email = ? AND password = SHA2(?, 512)";

	return find(sql, email, password);
    }

    @Override
    public void create(User t) {
	String sql = "INSERT INTO user"
	             + " (role_id, first_name, last_name, email, password)"
	             + " VALUES(?, ?, ?, ?, SHA2(?, 512))";

	create(sql, getValues(t));
    }
}
