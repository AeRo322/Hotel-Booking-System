package com.danylevych.hotel.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.entity.Order;
import com.danylevych.hotel.entity.User;
import com.danylevych.hotel.util.Session;

public class OrderCommand implements Command {

    @Override
    public String execute(HttpServletRequest request,
            HttpServletResponse response) {

	User user = Session.getUser(request);
	if (user == null) {
	    return request.getContextPath()
	           + "/auth/login.jsp";
	}

	try {
	    DaoFactory.getInstance().getOrderDao().create(new Order(request));
	} catch (Exception e) {
	    throw new IllegalArgumentException(e);
	}

	return request.getHeader("referer");
    }

}
