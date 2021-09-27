package com.danylevych.hotel.command;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danylevych.hotel.dao.BookingDao;
import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.entity.Booking;
import com.danylevych.hotel.entity.BookingStatus;
import com.danylevych.hotel.entity.User;

public class BookCommand implements Command {

    @Override
    public String execute(HttpServletRequest request,
            HttpServletResponse response) {

	User user = (User) request.getSession().getAttribute("user");
	if (user == null) {
	    return "auth/login.jsp";
	}

	String answer = request.getParameter("v");
	try {
	    BookingDao bookingDao = DaoFactory.getInstance().getBookingDao();
	    Long bookingId = Long.parseLong(request.getParameter("id"));
	    Booking booking = bookingDao.find(bookingId);

	    switch (answer) {
	    case "pay":
		booking.setStatus(BookingStatus.PAID);
		bookingDao.update(booking);
		break;

	    case "cancel":
		booking.setStatus(BookingStatus.COMPLETED);
		bookingDao.update(booking);
		break;

	    default:
		bookingDao.create(new Booking(request));
	    }
	} catch (ParseException e) {
	    throw new IllegalStateException(e);
	}

	return request.getHeader("referer");
    }

}
