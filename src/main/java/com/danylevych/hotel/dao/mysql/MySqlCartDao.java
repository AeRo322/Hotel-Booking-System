package com.danylevych.hotel.dao.mysql;

import static com.danylevych.hotel.util.SQL.formatSql;

import java.util.Collections;
import java.util.List;

import com.danylevych.hotel.dao.CartDao;
import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.entity.Cart;
import com.danylevych.hotel.entity.User;

public class MySqlCartDao extends CartDao {

    private int size;

    protected MySqlCartDao(DaoFactory daoFactory) {
	super(daoFactory);
    }

    @Override
    public void deleteExpiredCarts() {
	final String sql = "DELETE FROM cart"
	                   + " WHERE last_update > NOW() + INTERVAL 30 MINUTE";

	update(sql);
    }

    @Override
    public List<Cart> list(int limit, int offset, String orderBy,
            boolean isAscending, Object... values) {
	String sql = "SELECT *"
	             + " FROM order_details"
	             + " WHERE order_details_id"
	             + " ORDER BY %s %s"
	             + " LIMIT ? OFFSET ?";

	List<Integer> rooms = getRoomNumbers((Long) values[0]);

	if (rooms.isEmpty()) {
	    return Collections.emptyList();
	}

	size = rooms.size();
	sql = String.format(sql, formatSql(rooms.toArray()), orderBy,
	        isAscending ? "ASC" : "DESC");

	return list(sql, limit, offset);
    }

    @Override
    public int count()  {
	return size;
    }

    @Override
    public Long find(User user)  {
	String sql = "SELECT order_details_id"
	             + " FROM cart"
	             + " WHERE user_id = ?";

	return find(sql, user.getId()).getOrderDetails().getId();
    }

    @Override
    public void create(Cart cart)  {
	String sql = "INSERT INTO cart"
	             + " (user_id, order_details_id)"
	             + " VALUES(?, ?)";

	create(sql, cart.getUserId(), cart.getOrderDetails().getId());
    }

}
