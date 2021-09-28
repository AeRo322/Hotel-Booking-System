package com.danylevych.hotel.entity;

import static com.danylevych.hotel.entity.Booking.Column.CREATE_TIME;
import static com.danylevych.hotel.entity.Booking.Column.DETAILS_ID;
import static com.danylevych.hotel.entity.Booking.Column.ID;
import static com.danylevych.hotel.entity.Booking.Column.STATUS_ID;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class Booking implements Serializable {

    private static final long serialVersionUID = -3301985661998346827L;

    private long id;
    private Date createTime;
    private OrderDetails details;
    private BookingStatus status;

    public enum Column {

	DETAILS_ID,
	STATUS_ID,

	ID,
	CREATE_TIME;

	public final String v = name().toLowerCase();

    }

    public Booking() {

    }

    public Booking(ResultSet resultSet) {
	try {
	    status = BookingStatus.fromInt(resultSet.getInt(STATUS_ID.v));
	    createTime = resultSet.getTimestamp(CREATE_TIME.v);
	    id = resultSet.getLong(ID.v);
	    
	    final long orderDetailsId = resultSet.getLong(DETAILS_ID.v);
	    details = new OrderDetails(resultSet, orderDetailsId);
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    }

    public Booking(HttpServletRequest request, long orderDetailsId) {
	details = new OrderDetails(request);
	details.setId(orderDetailsId);
	status = BookingStatus.UNPAID;
    }

    public Booking(Order order) {
	details = order.getDetails();
	status = BookingStatus.UNPAID;
    }

    public Date getCreateTime() {
	return createTime;
    }

    public void setCreateTime(Date createTime) {
	this.createTime = createTime;
    }

    public OrderDetails getDetails() {
	return details;
    }

    public void setDetails(OrderDetails details) {
	this.details = details;
    }

    public BookingStatus getStatus() {
	return status;
    }

    public void setStatus(BookingStatus status) {
	this.status = status;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

}
