package com.danylevych.hotel.dao;

import java.sql.ResultSet;

import com.danylevych.hotel.entity.Booking;
import com.danylevych.hotel.util.SQL;

public abstract class BookingDao extends JdbcDao<Booking> {

    public static final String TABLE_NAME = "booking";

    protected BookingDao(DaoFactory daoFactory) {
	super(daoFactory, TABLE_NAME, Booking.Column.values(), 2);
    }

    public abstract void closeExpiredBookings();

    @Override
    protected String generateSqlFind(int n) {
	return SQL.generateSqlFind(Booking.Column.ID, n, TABLE_NAME);
    }

    @Override
    protected Object[] getValues(Booking t) {
	return new Object[] {
	    t.getDetails().getId(),
	    t.getStatus().ordinal()
	};
    }

    @Override
    protected Booking mapEntity(ResultSet resultSet) {
	return new Booking(resultSet);
    }

}
