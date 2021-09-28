package com.danylevych.hotel.command;

import static java.lang.Long.parseLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danylevych.hotel.dao.BookingDao;
import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.entity.Booking;
import com.danylevych.hotel.entity.BookingStatus;
import com.danylevych.hotel.entity.Cart;
import com.danylevych.hotel.entity.User;
import com.danylevych.hotel.util.Session;

public class BookCommand implements Command {

    @Override
    public String execute(HttpServletRequest request,
            HttpServletResponse response) {

	User user = Session.getUser(request);
	if (user == null) {
	    return "auth/login.jsp";
	}

	DaoFactory daoFactory = DaoFactory.getInstance();
	BookingDao bookingDao = daoFactory.getBookingDao();
	Cart cart = user.getCart();
	long bookingId;
	if (cart != null) {
	    bookingId = cart.getOrderDetails().getId();
	} else {
	    bookingId = parseLong(request.getParameter("booking_id"));
	}
	Booking booking = bookingDao.find(bookingId);
	final String answer = request.getParameter("v");

	switch (answer) {
	case "create":
	    bookingDao.create(new Booking(request, bookingId));
	    user.setCart(null);
	    Session.saveUser(request, user);
	    break;

	case "pay":
	    booking.setStatus(BookingStatus.PAID);
	    bookingDao.update(booking);
	    break;

	case "cancel":
	    booking.setStatus(BookingStatus.COMPLETED);
	    bookingDao.update(booking);
	    break;

	default:
	    throw new IllegalArgumentException();
	}

	return request.getHeader("referer");
    }

}
