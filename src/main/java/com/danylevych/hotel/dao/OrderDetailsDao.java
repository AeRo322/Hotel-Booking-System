package com.danylevych.hotel.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import com.danylevych.hotel.entity.OrderDetails;
import com.danylevych.hotel.util.SQL;

public abstract class OrderDetailsDao extends JdbcDao<OrderDetails> {

    public static final String TABLE_NAME = "order_details";

    protected OrderDetailsDao(DaoFactory daoFactory) {
	super(daoFactory, TABLE_NAME, OrderDetails.Column.values(), 4);
    }

    public abstract void addRoomToOrder(Connection c,
            OrderDetails orderDetails);

    public abstract void addRoomToOrder(OrderDetails orderDetails);

    public abstract List<Integer> getRoomNumbers(Long orderDetailsId);

    @Override
    public List<OrderDetails> list(int limit, int offset, String orderBy,
            boolean isAscending, Object... values) {
	throw new UnsupportedOperationException();
    }

    @Override
    protected OrderDetails mapEntity(ResultSet resultSet) {
	return new OrderDetails(resultSet);
    }

    @Override
    protected String generateSqlFind(int n) {
	return SQL.generateSqlFind(OrderDetails.Column.ID, n, TABLE_NAME);
    }

    @Override
    protected Object[] getValues(OrderDetails t) {
	return new Object[] {
	    t.getUserId(),
	    t.getGuests(),
	    t.getCheckIn(),
	    t.getCheckOut()
	};
    }

}
