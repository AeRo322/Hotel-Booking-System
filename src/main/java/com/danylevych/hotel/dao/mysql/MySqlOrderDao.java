package com.danylevych.hotel.dao.mysql;

import static com.danylevych.hotel.util.DbUtils.close;
import static com.danylevych.hotel.util.DbUtils.rollback;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.danylevych.hotel.dao.DaoException;
import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.OrderDao;
import com.danylevych.hotel.entity.Order;
import com.danylevych.hotel.entity.OrderDetails;

public class MySqlOrderDao extends OrderDao {

    protected MySqlOrderDao(DaoFactory daoFactory) {
	super(daoFactory);
    }

    @Override
    public void create(Order order) throws DaoException {
	String sql = "INSERT INTO `order`"
	             + " (details_id, room_class_id, status_id)"
	             + " VALUES(?, ?, ?)";

	Connection c = null;

	try {
	    c = daoFactory.getConnection();
	    c.setAutoCommit(false);

	    OrderDetails orderDetails = order.getDetails();
	    final long detailsId = orderDetailsDao.create(c, orderDetails);

	    final int roomClassId = order.getRoomClass().ordinal();
	    final int statusId = order.getStatus().ordinal();

	    create(c, sql, detailsId, roomClassId, statusId);
	    c.commit();
	} catch (SQLException | DaoException e) {
	    rollback(c);
	    throw new DaoException(e);
	} finally {
	    close(c);
	}

    }

    public void create1(Order order) throws DaoException {
	executeAsTransaction(c -> {
	    OrderDetails orderDetails = order.getDetails();
	    final long orderDetailsId = orderDetailsDao.create(c, orderDetails);

	    orderDetails.setId(orderDetailsId);
	    order.setDetails(orderDetails);

	    create(c, order);
	});

    }

    @Override
    public void update(Order order) throws DaoException {
	String sql = "UPDATE `order`"
	             + " SET"
	             + " status_id = ?"
	             + " WHERE id = ?";

	int statusId = order.getStatus().ordinal();
	long id = order.getId();

	update(sql, statusId, id);
    }

    @Override
    public Order find(long id) throws DaoException {
	String sql = "SELECT *"
	             + " FROM `order`, order_details"
	             + " WHERE `order`.id = ?";

	return find(sql, id);
    }

    @Override
    public List<Order> list(int limit, int offset, String orderBy,
            boolean isAscending) throws DaoException {
	String sql = "SELECT *"
	             + " FROM `order`, order_details"
	             + " WHERE `order`.id = order_details.id"
	             + " ORDER BY %s %s"
	             + " LIMIT ? OFFSET ?";

	sql = String.format(sql, orderBy, isAscending ? "ASC" : "DESC");

	return list(sql, limit, offset);
    }

}
