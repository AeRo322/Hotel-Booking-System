package com.danylevych.hotel.dao;

import java.sql.Connection;
import java.sql.ResultSet;

import com.danylevych.hotel.entity.OrderDetails;

public abstract class OrderDetailsDao extends JdbcDao<OrderDetails> {

    public static final String TABLE_NAME = "order_details";

    protected OrderDetailsDao(DaoFactory daoFactory) {
	super(daoFactory, TABLE_NAME);
    }

    @Override
    protected OrderDetails mapEntity(ResultSet resultSet) {
	return new OrderDetails(resultSet);
    }

    public abstract long create(Connection connection,
            OrderDetails orderDetails);

    public abstract long create(OrderDetails orderDetails);

    public abstract void addRoomToOrder(Connection c,
            OrderDetails orderDetails);

    public abstract void addRoomToOrder(OrderDetails orderDetails);

    public abstract void remove(Long orderdId);

    public abstract void update(Connection c, OrderDetails orderDetails);

}
