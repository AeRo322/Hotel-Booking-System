package com.danylevych.hotel.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Cart implements Serializable {

    private static final long serialVersionUID = -5259640173749600445L;

    private long userId;
    private OrderDetails orderDetails;
    private Date lastUpdate;

    public Cart(ResultSet resultSet) {
	try {
	    userId = resultSet.getLong("user_id");
	    orderDetails = new OrderDetails(resultSet);
	    lastUpdate = resultSet.getTimestamp("last_update");
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

    public OrderDetails getOrderDetails() {
	return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
	this.orderDetails = orderDetails;
    }

    public Date getLastUpdate() {
	return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
	this.lastUpdate = lastUpdate;
    }

}
