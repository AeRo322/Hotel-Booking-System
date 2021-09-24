package com.danylevych.hotel.dao.mysql;

import static com.danylevych.hotel.util.DbUtils.close;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.danylevych.hotel.dao.BookingDao;
import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.OrderDao;
import com.danylevych.hotel.dao.OrderDetailsDao;
import com.danylevych.hotel.dao.RoomDao;
import com.danylevych.hotel.dao.UserDao;

public class MySqlDaoFactory extends DaoFactory {

    private OrderDetailsDao orderDetailsDao = new MySqlOrderDetailsDao(this);
    private BookingDao bookingDao = new MySqlBookingDao(this);
    private OrderDao requestDao = new MySqlOrderDao(this);
    private UserDao userDao = new MySqlUserDao(this);
    private RoomDao roomDao = new MySqlRoomDao(this);

    private static final DataSource dataSource;

    static {
	try {
	    Context initContext = new InitialContext();
	    Context envContext = (Context) initContext.lookup("java:/comp/env");
	    dataSource = (DataSource) envContext.lookup("jdbc/hotelDB");
	} catch (NamingException e) {
	    throw new IllegalStateException(e);
	}
    }

    @Override
    public Connection getTransactionConnection() throws SQLException {
	Connection connection = dataSource.getConnection();
	try {
	    connection.setAutoCommit(false);
	} catch (SQLException e) {
	    close(connection);
	    throw e;
	}
	return connection;
    }

    @Override
    public Connection getConnection() throws SQLException {
	return dataSource.getConnection();
    }

    @Override
    public OrderDetailsDao getOrderDetailsDao() {
	return orderDetailsDao;
    }

    @Override
    public BookingDao getBookingDao() {
	return bookingDao;
    }

    @Override
    public OrderDao getOrderDao() {
	return requestDao;
    }

    @Override
    public UserDao getUserDao() {
	return userDao;
    }

    @Override
    public RoomDao getRoomDao() {
	return roomDao;
    }

}
