package com.danylevych.hotel.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.entity.Order;
import com.danylevych.hotel.entity.User;

public class OrderCommand implements Command {

    @Override
    public String execute(HttpServletRequest request,
            HttpServletResponse response) {

	User user = (User) request.getSession().getAttribute("user");
	if (user == null) {
	    return "auth/login.jsp";
	}

	try {
	    DaoFactory.getInstance().getOrderDao().create(new Order(request));
	} catch (Exception e) {
	    throw new IllegalArgumentException(e);
	}

	return "user/client.jsp";
    }

}
