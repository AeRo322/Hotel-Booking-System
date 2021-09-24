package com.danylevych.hotel.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.danylevych.hotel.dao.mysql.MySqlDaoFactory;

public abstract class DaoFactory {

    public static final int MY_SQL = 1;

    private static final Map<Integer, DaoFactory> FACTORIES = new HashMap<>();

    public static DaoFactory getInstance() {
	return FACTORIES.computeIfAbsent(MY_SQL, DaoFactory::newInstance);
    }

    public static DaoFactory getInstance(int type) {
	return FACTORIES.computeIfAbsent(type, DaoFactory::newInstance);
    }

    private static DaoFactory newInstance(int type) {
	if (type == MY_SQL) {
	    return new MySqlDaoFactory();
	}
	return null;
    }

    public abstract Connection getTransactionConnection() throws SQLException;

    public abstract Connection getConnection() throws SQLException;

    public abstract OrderDetailsDao getOrderDetailsDao();

    public abstract BookingDao getBookingDao();

    public abstract OrderDao getOrderDao();

    public abstract UserDao getUserDao();

    public abstract RoomDao getRoomDao();

}
