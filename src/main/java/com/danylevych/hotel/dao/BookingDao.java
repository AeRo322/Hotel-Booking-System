package com.danylevych.hotel.dao;

import java.sql.ResultSet;

import com.danylevych.hotel.entity.Booking;

public abstract class BookingDao extends JdbcDao<Booking> {
    
    public static final String TABLE_NAME = "booking";

    protected BookingDao(DaoFactory daoFactory) {
	super(daoFactory, TABLE_NAME);
    }

    public abstract void create(Booking booking) ;

    @Override
    protected Booking mapEntity(ResultSet resultSet) {
        return new Booking(resultSet);
    }

    public abstract Booking find(Long bookingId) ;

    public abstract void update(Booking booking) ;

    public abstract void closeExpiredBookings() ;

}
