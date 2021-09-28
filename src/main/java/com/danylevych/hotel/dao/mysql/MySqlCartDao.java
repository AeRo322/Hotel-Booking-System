package com.danylevych.hotel.dao.mysql;

import static com.danylevych.hotel.util.SQL.formatSql;

import java.util.Collections;
import java.util.List;

import com.danylevych.hotel.dao.CartDao;
import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.OrderDetailsDao;
import com.danylevych.hotel.entity.Cart;
import com.danylevych.hotel.entity.OrderDetails;
import com.danylevych.hotel.entity.User;

public class MySqlCartDao extends CartDao {

    private int size;

    protected MySqlCartDao(DaoFactory daoFactory) {
	super(daoFactory);
    }

    @Override
    public void create(Cart cart) {
	transaction(c -> {
	    OrderDetails orderDetails = cart.getOrderDetails();
	    OrderDetailsDao orderDetailsDao = daoFactory.getOrderDetailsDao();
	    orderDetails.setId(orderDetailsDao.create(c, true, orderDetails));
	    orderDetailsDao.addRoomToOrder(c, orderDetails);
	    cart.setOrderDetails(orderDetails);
	    create(c, cart);
	});
    }

    @Override
    public void deleteExpiredCarts() {
	final String sql = "DELETE order_details"
	                   + " FROM order_details"
	                   + " JOIN cart "
	                   + " ON order_details_id = id"
	                   + " WHERE last_update < NOW() - INTERVAL 10 SECOND";

	update(sql);
    }

    @Override
    public List<Cart> list(int limit, int offset, String orderBy,
            boolean isAscending, User user) {
	String sql = "SELECT *"
	             + " FROM room"
	             + " JOIN order_details"
	             + " ON order_details.id = %d"
	             + " JOIN cart"
	             + " ON cart.order_details_id = %d"
	             + " WHERE number IN (%s)"
	             + " ORDER BY %s %s"
	             + " LIMIT ? OFFSET ?";

	Cart cart = user.getCart();
	if (cart == null) {
	    return Collections.emptyList();
	}

	final long orderDetailsId = cart.getOrderDetails().getId();
	OrderDetailsDao orderDetailsDao = daoFactory.getOrderDetailsDao();
	List<Integer> rooms = orderDetailsDao.getRoomNumbers(orderDetailsId);

	size = rooms.size();
	sql = String.format(sql, orderDetailsId, orderDetailsId,
	        formatSql(rooms.toArray()), orderBy,
	        isAscending ? "ASC" : "DESC");

	return list(sql, limit, offset);
    }

    @Override
    public int count(Object... values) {
	return size;
    }

}
