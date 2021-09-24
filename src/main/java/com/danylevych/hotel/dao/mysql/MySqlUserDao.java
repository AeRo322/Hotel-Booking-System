package com.danylevych.hotel.dao.mysql;

import static com.danylevych.hotel.util.Enums.getSequence;

import java.util.List;

import com.danylevych.hotel.dao.DaoException;
import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.UserDao;
import com.danylevych.hotel.entity.User;

public class MySqlUserDao extends UserDao {

    public MySqlUserDao(DaoFactory daoFactory) {
	super(daoFactory);
    }

    
    
    @Override
    public User find(String email, String password) throws DaoException {
	String sql = "SELECT %s"
	             + " FROM user"
	             + " WHERE email = ? AND password = SHA2(?, 512)";

	sql = String.format(sql, getSequence(User.Column.class));

	return find(sql, email, password);
    }

    @Override
    public List<User> list(int limit, int offset, String orderBy,
            boolean isAscending) throws DaoException {
	return null;
    }

}
