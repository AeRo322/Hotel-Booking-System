package com.danylevych.hotel.entity;

import static com.danylevych.hotel.entity.Order.Column.CREATE_TIME;
import static com.danylevych.hotel.entity.Order.Column.ID;
import static com.danylevych.hotel.entity.Order.Column.ROOM_CLASS_ID;
import static com.danylevych.hotel.entity.Order.Column.STATUS_ID;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class Order implements Entity {

    private static final long serialVersionUID = 6192785431279133934L;

    private long id;
    private OrderStatus status;
    private OrderDetails details;

    private RoomClass roomClass;
    private Date createTime;

    public enum Column {

	ID,
	STATUS_ID,
	DETAILS_ID,
	ROOM_CLASS_ID,
	CREATE_TIME;

	public final String v = name().toLowerCase();

    }

    public Order() {

    }

    public Order(HttpServletRequest request) throws ParseException {
	int classId = Integer.parseInt(request.getParameter("room_class_id"));
	roomClass = RoomClass.fromInt(classId);
	details = new OrderDetails(request);
	status = OrderStatus.NEW;
    }

    public Order(ResultSet resultSet) throws SQLException {
	roomClass = RoomClass.fromInt(resultSet.getInt(ROOM_CLASS_ID.v));
	status = OrderStatus.fromInt(resultSet.getInt(STATUS_ID.v));
	createTime = resultSet.getTimestamp(CREATE_TIME.v);
	id = resultSet.getLong(ID.v);
	
	details = new OrderDetails(resultSet);
    }

    @Override
    public Object[] extract() {
	return new Object[] {
	    status.ordinal(),
	    details.getId(),
	    roomClass.ordinal(),
	};
    }

    public OrderDetails getDetails() {
	return details;
    }

    public void setDetails(OrderDetails details) {
	this.details = details;
    }

    public OrderStatus getStatus() {
	return status;
    }

    public void setStatus(OrderStatus status) {
	this.status = status;
    }

    public RoomClass getRoomClass() {
	return roomClass;
    }

    public void setRoomClass(RoomClass roomClass) {
	this.roomClass = roomClass;
    }

    public Date getCreateTime() {
	return createTime;
    }

    public void setCreateTime(Date createTime) {
	this.createTime = createTime;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

}