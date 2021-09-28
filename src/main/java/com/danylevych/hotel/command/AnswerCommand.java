package com.danylevych.hotel.command;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.OrderDao;
import com.danylevych.hotel.entity.Booking;
import com.danylevych.hotel.entity.Order;
import com.danylevych.hotel.entity.OrderDetails;
import com.danylevych.hotel.entity.OrderStatus;
import com.danylevych.hotel.entity.Room;

public class AnswerCommand implements Command {

    @Override
    public String execute(HttpServletRequest request,
            HttpServletResponse response) {

	DaoFactory daoFactory = DaoFactory.getInstance();
	OrderDao orderDao = daoFactory.getOrderDao();

	final long orderId = parseLong(request.getParameter("order_id"));
	Order order = orderDao.find(orderId);

	final String answer = request.getParameter("v");

	switch (answer) {
	case "propose":
	    int roomNumber = parseInt(request.getParameter("room_number"));
	    Room room = daoFactory.getRoomDao().find(roomNumber);

	    OrderDetails orderDetails = order.getDetails();
	    orderDetails.setRooms(Arrays.asList(room));
	    order.setDetails(orderDetails);

	    order.setStatus(OrderStatus.PENDING);
	    orderDao.update(order);
	    break;

	case "decline":
	    order.setStatus(OrderStatus.DECLINED);
	    orderDao.update(order);
	    break;

	case "cancel":
	    order.setStatus(OrderStatus.CANCELED);
	    orderDao.update(order);
	    break;

	case "accept":
	    order.setStatus(OrderStatus.ACCEPTED);
	    daoFactory.getBookingDao().create(new Booking(order), order);
	    break;

	default:
	    throw new IllegalArgumentException(answer);
	}

	return request.getHeader("referer");
    }

}
