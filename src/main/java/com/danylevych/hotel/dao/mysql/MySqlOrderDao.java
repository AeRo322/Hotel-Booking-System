package com.danylevych.hotel.dao.mysql;

import java.util.List;

import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.OrderDao;
import com.danylevych.hotel.dao.OrderDetailsDao;
import com.danylevych.hotel.entity.Order;
import com.danylevych.hotel.entity.OrderDetails;
import com.danylevych.hotel.entity.User;
import com.danylevych.hotel.entity.UserRole;

public class MySqlOrderDao extends OrderDao {

    protected MySqlOrderDao(DaoFactory daoFactory) {
	super(daoFactory);
    }

    @Override
    public void update(Order order) {
	transaction(c -> {
	    OrderDetails orderDetails = order.getDetails();
	    if (orderDetails.getRooms() != null) {
		daoFactory.getOrderDetailsDao().addRoomToOrder(c, orderDetails);
	    }
	    update(c, order);
	});
    }

    @Override
    public void create(Order order) {
	transaction(c -> {
	    OrderDetails orderDetails = order.getDetails();
	    OrderDetailsDao orderDetailsDao = daoFactory.getOrderDetailsDao();
	    orderDetails.setId(orderDetailsDao.create(c, true, orderDetails));
	    order.setDetails(orderDetails);
	    create(c, order);
	});
    }

    @Override
    public List<Order> list(int limit, int offset, String orderBy,
            boolean isAscending, User user) {
	if (user.getRole() == UserRole.MANAGER) {
	    String sql = "SELECT *"
	                 + " FROM `order`"
	                 + " JOIN order_details"
	                 + " ON order_details.id = details_id"
	                 + " WHERE status_id"
	                 + " IN (SELECT id FROM order_status WHERE name = 'NEW')"
	                 + " ORDER BY %s %s"
	                 + " LIMIT ? OFFSET ?";

	    sql = String.format(sql, orderBy, isAscending ? "ASC" : "DESC");

	    return list(sql, limit, offset);
	} else {
	    String sql = "SELECT *"
	                 + " FROM `order`"
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
	    String sql = "SELECT COUNT(*)"
	                 + " FROM `order`"
	                 + " WHERE status_id"
	                 + " IN (SELECT id FROM order_status WHERE name = 'NEW')";

	    return count(sql);
	}
	return super.count(values);
    }
}
