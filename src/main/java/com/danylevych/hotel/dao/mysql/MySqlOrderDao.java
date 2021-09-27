package com.danylevych.hotel.dao.mysql;

import java.util.List;

import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.OrderDao;
import com.danylevych.hotel.dao.OrderDetailsDao;
import com.danylevych.hotel.entity.Order;
import com.danylevych.hotel.entity.OrderDetails;
import com.danylevych.hotel.util.SQL;

public class MySqlOrderDao extends OrderDao {

    protected MySqlOrderDao(DaoFactory daoFactory) {
	super(daoFactory);
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
            boolean isAscending, Object... values) {
	String sql = "SELECT *"
	             + " FROM `order`"
	             + " JOIN order_details"
	             + " ON order_details.id = details_id"
	             + " WHERE user_id = ?"
	             + " ORDER BY %s %s"
	             + " LIMIT ? OFFSET ?";

	sql = String.format(sql, orderBy, isAscending ? "ASC" : "DESC");

	Long userId = (Long) values[0];

	return list(sql, userId, limit, offset);
    }

    @Override
    protected String generateSqlFind(int n) {
	return SQL.generateSqlFind(TABLE_NAME
	                           + "."
	                           + Order.Column.ID,
	        n, TABLE_NAME, OrderDetailsDao.TABLE_NAME);
    }

}
