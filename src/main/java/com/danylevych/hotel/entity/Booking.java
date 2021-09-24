package com.danylevych.hotel.entity;

import static com.danylevych.hotel.entity.Booking.Column.CREATE_TIME;
import static com.danylevych.hotel.entity.Booking.Column.ID;
import static com.danylevych.hotel.entity.Booking.Column.STATUS_ID;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class Booking implements Entity {

    private static final long serialVersionUID = -3301985661998346827L;

    private long id;
    private Date createTime;
    private OrderDetails details;
    private BookingStatus status;

    public enum Column {

	ID,
	STATUS_ID,
	DETAILS_ID,
	CREATE_TIME;

	public final String v = name().toLowerCase();

    }

    public Booking(ResultSet resultSet) throws SQLException {
	setStatus(BookingStatus.fromInt(resultSet.getInt(STATUS_ID.v)));
	createTime = resultSet.getTimestamp(CREATE_TIME.v);
	setId(resultSet.getLong(ID.v));

	setDetails(new OrderDetails(resultSet));
    }

    public Booking(HttpServletRequest request) throws ParseException {
	details = new OrderDetails(request);
    }

    public Date getCreateTime() {
	return createTime;
    }

    public void setCreateTime(Date createTime) {
	this.createTime = createTime;
    }

    @Override
    public Object[] extract() {
	// TODO Auto-generated method stub
	return null;
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
