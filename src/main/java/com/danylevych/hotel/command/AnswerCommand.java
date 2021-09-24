package com.danylevych.hotel.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danylevych.hotel.dao.DaoException;
import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.OrderDao;
import com.danylevych.hotel.entity.Order;
import com.danylevych.hotel.entity.OrderStatus;

public class AnswerCommand implements Command {

    @Override
    public String execute(HttpServletRequest request,
            HttpServletResponse response) {

	String entity = request.getParameter("entity");
	if (!entity.equals(OrderDao.TABLE_NAME)) {
	    throw new IllegalArgumentException("Entity = "
	                                       + entity);
	}

	String answer = request.getParameter("v");
	final OrderStatus status = OrderStatus.valueOf((answer
	                                                + "ed").toUpperCase());

	final long id = Long.parseLong(request.getParameter("id"));

	try {
	    DaoFactory daoFactory = DaoFactory.getInstance(DaoFactory.MY_SQL);
	    OrderDao orderDao = daoFactory.getOrderDao();
	    Order order = orderDao.find(id);
	    order.setStatus(status);
	    orderDao.update(order);
	} catch (DaoException e) {
	    throw new IllegalArgumentException(e);
	}

	return request.getHeader("referer");
    }

}
