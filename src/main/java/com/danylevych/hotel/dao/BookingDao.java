package com.danylevych.hotel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.danylevych.hotel.entity.Booking;

public abstract class BookingDao extends JdbcDao<Booking> {

    protected BookingDao(DaoFactory daoFactory) {
	super(daoFactory, "booking");
    }

    public abstract void create(Booking booking);

    @Override
    protected Booking mapEntity(ResultSet resultSet) {
        try {    
            return new Booking(resultSet);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

}
