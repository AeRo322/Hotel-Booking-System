package com.danylevych.hotel.entity;

import static com.danylevych.hotel.entity.Cart.Column.LAST_UPDATE;
import static com.danylevych.hotel.entity.Cart.Column.USER_ID;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Cart implements Serializable {

    private static final long serialVersionUID = -5259640173749600445L;

    private long userId;
    private OrderDetails orderDetails;
    private Date lastUpdate;

    public enum Column {

	USER_ID,
	ORDER_DETAILS_ID,

	LAST_UPDATE;

	public final String v = name().toLowerCase();

    }

    public Cart(ResultSet resultSet) {
	try {
	    userId = resultSet.getLong(USER_ID.v);
	    orderDetails = new OrderDetails(resultSet);
	    lastUpdate = resultSet.getTimestamp(LAST_UPDATE.v);
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    }

    public Cart(User user, OrderDetails orderDetails) {
	userId = user.getId();
	this.orderDetails = orderDetails;
    }

    public long getUserId() {
	return userId;
    }

    public void setUserId(long userId) {
	this.userId = userId;
    }

    public Date getLastUpdate() {
	return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
	this.lastUpdate = lastUpdate;
    }

    public OrderDetails getOrderDetails() {
	return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
	this.orderDetails = orderDetails;
    }

}
