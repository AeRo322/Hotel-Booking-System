package com.danylevych.hotel.command;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.entity.Booking;

public class BookCommand implements Command {

    @Override
    public String execute(HttpServletRequest request,
            HttpServletResponse response) {

	try {
	    DaoFactory.getInstance()
	              .getBookingDao()
	              .create(new Booking(request));
	} catch (ParseException e) {
	    throw new IllegalStateException(e);
	}

	return "/WEB-INF/user/sendRequest.jsp";
    }

}
