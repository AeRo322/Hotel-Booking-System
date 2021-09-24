package com.danylevych.hotel.dao.mysql;

import java.util.List;

import com.danylevych.hotel.dao.BookingDao;
import com.danylevych.hotel.dao.DaoException;
import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.entity.Booking;

public class MySqlBookingDao extends BookingDao {

    public MySqlBookingDao(DaoFactory daoFactory) {
	super(daoFactory);
    }

    @Override
    public void create(Booking booking) {

    }

    @Override
    public List<Booking> list(int limit, int offset, String orderBy,
            boolean isAscending) throws DaoException {
	String sql = "SELECT *"
	             + " FROM %s"
	             + " ORDER BY %s %s"
	             + " LIMIT ? OFFSET ?";

	sql = String.format(sql, orderBy, isAscending ? "ASC" : "DESC");

	return list(sql, limit, offset);
    }

}
