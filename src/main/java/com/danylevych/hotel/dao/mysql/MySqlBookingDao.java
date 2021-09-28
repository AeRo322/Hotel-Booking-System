package com.danylevych.hotel.dao.mysql;

import java.util.List;

import com.danylevych.hotel.dao.BookingDao;
import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.entity.Booking;
import com.danylevych.hotel.entity.BookingStatus;
import com.danylevych.hotel.entity.Order;
import com.danylevych.hotel.entity.OrderDetails;
import com.danylevych.hotel.entity.Room;
import com.danylevych.hotel.entity.User;
import com.danylevych.hotel.entity.UserRole;

public class MySqlBookingDao extends BookingDao {

    public MySqlBookingDao(DaoFactory daoFactory) {
	super(daoFactory);
    }

    @Override
    public void update(Booking booking, Room room) {
	transaction(c -> {
	    daoFactory.getRoomDao().update(c, room);
	    update(c, booking);
	});
    }

    @Override
    public void create(Booking booking) {
	transaction(c -> {
	    OrderDetails details = booking.getDetails();
	    daoFactory.getOrderDetailsDao().update(c, details);
	    create(c, booking);
	});
    }

    @Override
    public void create(Booking booking, Order order) {
	transaction(c -> {
	    daoFactory.getOrderDao().update(c, order);
	    create(c, booking);
	});
    }

    @Override
    public List<Booking> list(int limit, int offset, String orderBy,
            boolean isAscending, User user) {
	if (user.getRole() == UserRole.MANAGER) {
	    String sql = "SELECT *"
	                 + " FROM booking"
	                 + " JOIN order_details"
	                 + " ON order_details.id = details_id"
	                 + " ORDER BY %s %s"
	                 + " LIMIT ? OFFSET ?";

	    sql = String.format(sql, orderBy, isAscending ? "ASC" : "DESC");

	    return list(sql, limit, offset);
	} else {
	    String sql = "SELECT *"
	                 + " FROM booking"
	                 + " JOIN order_details"
	                 + " ON order_details.id = details_id"
	                 + " WHERE user_id = ?"
	                 + " ORDER BY %s %s"
	                 + " LIMIT ? OFFSET ?";

	    sql = String.format(sql, orderBy, isAscending ? "ASC" : "DESC");

	    return list(sql, user.getId(), limit, offset);
	}
    }

    @Override
    public int count(Object... values) {
	User user = daoFactory.getUserDao().find((long) values[0]);
	if (user.getRole() == UserRole.MANAGER) {
	    final String sql = "SELECT COUNT(*)"
	                       + " FROM booking";

	    return count(sql);
	}
	return super.count(values);
    }

    @Override
    public void closeExpiredBookings() {
	String sql = "UPDATE booking"
	             + " SET status_id = %d"
	             + " WHERE status_id = %d"
	             + " AND create_time < NOW() - INTERVAL 2 DAY";

	sql = String.format(sql, BookingStatus.EXPIRED.ordinal(),
	        BookingStatus.UNPAID.ordinal());

	update(sql);
    }

    @Override
    public void closeCompletedBookings() {
	String sql = "UPDATE booking"
	             + " JOIN order_details"
	             + " ON order_details.id = details_id"
	             + " SET status_id = %d"
	             + " WHERE (status_id = %d"
	             + " OR status_id = %d)"
	             + " AND check_out <= CURDATE()";

	sql = String.format(sql, 
		BookingStatus.COMPLETED.ordinal(),
	        BookingStatus.IN_USE.ordinal(), 
	        BookingStatus.PAID.ordinal());

	update(sql);
    }

    @Override
    public void updateBookings() {
	String sql = "UPDATE booking"
	             + " JOIN order_details"
	             + " ON order_details.id = details_id"
	             + " SET status_id = %d"
	             + " WHERE status_id = %d"
	             + " AND check_in <= CURDATE()";

	sql = String.format(sql, BookingStatus.IN_USE.ordinal(),
	        BookingStatus.PAID.ordinal());

	update(sql);
    }
}
