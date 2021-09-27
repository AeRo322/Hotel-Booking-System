package com.danylevych.hotel.dao.mysql;

import java.util.List;

import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.OrderDao;
import com.danylevych.hotel.dao.OrderDetailsDao;
import com.danylevych.hotel.entity.Order;
import com.danylevych.hotel.entity.OrderDetails;

public class MySqlOrderDao extends OrderDao {

    protected MySqlOrderDao(DaoFactory daoFactory) {
	super(daoFactory);
    }

    @Override
    public void create(Order order)  {
	String sql = "INSERT INTO `order`"
	             + " (details_id, room_class_id, status_id)"
	             + " VALUES(?, ?, ?)";

	transaction(c -> {
	    OrderDetails orderDetails = order.getDetails();
	    OrderDetailsDao orderDetailsDao = daoFactory.getOrderDetailsDao();
	    final long detailsId = orderDetailsDao.create(c, orderDetails);

	    final int roomClassId = order.getRoomClass().ordinal();
	    final int statusId = order.getStatus().ordinal();

	    create(c, sql, detailsId, roomClassId, statusId);
	});

    }

    @Override
    public void update(Order order)  {
	String sql = "UPDATE `order`"
	             + " SET"
	             + " status_id = ?"
	             + " WHERE id = ?";

	int statusId = order.getStatus().ordinal();
	long id = order.getId();

	update(sql, statusId, id);
    }

    @Override
    public Order find(long id)  {
	String sql = "SELECT *"
	             + " FROM `order`, order_details"
	             + " WHERE `order`.id = ?";

	return find(sql, id);
    }

    @Override
    public List<Order> list(int limit, int offset, String orderBy,
            boolean isAscending, Object... values)  {
	String sql = "SELECT *"
	             + " FROM `order`, order_details"
	             + " WHERE `order`.id = order_details.id"
	             + " ORDER BY %s %s"
	             + " LIMIT ? OFFSET ?";

	sql = String.format(sql, orderBy, isAscending ? "ASC" : "DESC");

	return list(sql, limit, offset);
    }

}
