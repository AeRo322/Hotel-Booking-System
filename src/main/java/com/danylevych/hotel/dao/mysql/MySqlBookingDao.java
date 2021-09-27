package com.danylevych.hotel.dao.mysql;

import static com.danylevych.hotel.util.SQL.formatSql;

import java.util.ArrayList;
import java.util.List;

import com.danylevych.hotel.dao.BookingDao;
import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.OrderDetailsDao;
import com.danylevych.hotel.dao.RoomDao;
import com.danylevych.hotel.entity.Booking;
import com.danylevych.hotel.entity.BookingStatus;
import com.danylevych.hotel.entity.OrderDetails;
import com.danylevych.hotel.entity.Room;
import com.danylevych.hotel.entity.RoomStatus;

public class MySqlBookingDao extends BookingDao {

    public MySqlBookingDao(DaoFactory daoFactory) {
	super(daoFactory);
    }

    @Override
    public void create(Booking booking) {
	transaction(c -> {
	    daoFactory.getOrderDetailsDao().update(c, booking.getDetails());
	    create(c, booking);
	});
    }

    @Override
    public List<Booking> list(int limit, int offset, String orderBy,
            boolean isAscending, Object... values) {
	String sql = "SELECT *"
	             + " FROM booking"
	             + " JOIN order_details"
	             + " ON order_details.id = details_id"
	             + " WHERE user_id = ?"
	             + " ORDER BY %s %s"
	             + " LIMIT ? OFFSET ?";

	sql = String.format(sql, orderBy, isAscending ? "ASC" : "DESC");

	Long userId = (Long) values[0];

	return list(sql, userId, limit, offset);
    }

    @Override
    public void closeExpiredBookings() {
	transaction(c -> {
	    String sql = "UPDATE booking"
	                 + " SET"
	                 + " status_id = %d"
	                 + " WHERE id IN (%s)";

	    List<Booking> expiredBookings = getExpiredBookings();
	    if (expiredBookings.isEmpty()) {
		return;
	    }

	    Long[] ids = new Long[expiredBookings.size()];
	    for (int i = 0; i < ids.length; i++) {
		ids[i] = expiredBookings.get(i).getId();
	    }

	    sql = String.format(sql, BookingStatus.EXPIRED.ordinal(),
	            formatSql(ids));

	    update(c, sql, expiredBookings);

	    for (Booking booking : expiredBookings) {
		final long orderDetailsId = booking.getDetails().getId();
		OrderDetailsDao orderDetailsDao =
		        daoFactory.getOrderDetailsDao();
		List<Integer> roomNumbers =
		        orderDetailsDao.getRoomNumbers(orderDetailsId);
		List<Room> rooms = new ArrayList<>();

		for (Integer roomNumber : roomNumbers) {
		    Room room = new Room();
		    room.setNumber(roomNumber);
		    room.setRoomStatus(RoomStatus.AVAILABLE);
		    rooms.add(room);
		}

		OrderDetails details = booking.getDetails();
		details.setRooms(rooms);
		booking.setDetails(details);
		RoomDao roomDao = daoFactory.getRoomDao();
		roomDao.update(c, booking.getDetails().getRooms());
	    }
	});
    }

    private List<Booking> getExpiredBookings() {
	String sql = "SELECT *"
	             + " FROM booking"
	             + " WHERE status_id = %d"
	             + " AND create_time > NOW() + INTERVAL 2 DAY";

	sql = String.format(sql, BookingStatus.UNPAID.ordinal());

	return list(sql);
    }

}
