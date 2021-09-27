package com.danylevych.hotel.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danylevych.hotel.dao.CartDao;
import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.OrderDetailsDao;
import com.danylevych.hotel.entity.Cart;
import com.danylevych.hotel.entity.OrderDetails;
import com.danylevych.hotel.entity.Room;
import com.danylevych.hotel.entity.User;
import com.danylevych.hotel.util.Session;

public class CartCommand implements Command {

    @Override
    public String execute(HttpServletRequest request,
            HttpServletResponse response) {

	User user = Session.getUser(request);
	if (user == null) {
	    return "auth/login.jsp";
	}

	DaoFactory daoFactory = DaoFactory.getInstance();
	OrderDetailsDao orderDetailsDao = daoFactory.getOrderDetailsDao();
	int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));

	Room room = daoFactory.getRoomDao().find(roomNumber);
	OrderDetails orderDetails = new OrderDetails(user, room);

	CartDao cartDao = daoFactory.getCartDao();
	Cart cart = user.getCart();

	if (cart == null) {
	    cart = new Cart(user, orderDetails);
	    cartDao.create(cart);
	    user.setCart(cart);
	    Session.saveUser(request, user);
	} else {
	    orderDetails.setId(cart.getOrderDetails().getId());
	    orderDetailsDao.addRoomToOrder(orderDetails);
	}

	return request.getHeader("referer") + "#";
    }

}
