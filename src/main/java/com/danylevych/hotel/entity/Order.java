package com.danylevych.hotel.entity;

import static com.danylevych.hotel.entity.Order.Column.CREATE_TIME;
import static com.danylevych.hotel.entity.Order.Column.DETAILS_ID;
import static com.danylevych.hotel.entity.Order.Column.ID;
import static com.danylevych.hotel.entity.Order.Column.ROOM_CLASS_ID;
import static com.danylevych.hotel.entity.Order.Column.STATUS_ID;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class Order implements Serializable {

    private static final long serialVersionUID = 6192785431279133934L;

    private RoomClass roomClass;
    private OrderDetails details;
    private OrderStatus status;

    private Date createTime;
    private long id;

    public enum Column {

	ROOM_CLASS_ID,
	DETAILS_ID,
	STATUS_ID,

	ID,
	CREATE_TIME;

	public final String v = name().toLowerCase();

    }

    public Order() {

    }

    public Order(HttpServletRequest request) {
	int classId = Integer.parseInt(request.getParameter(ROOM_CLASS_ID.v));
	roomClass = RoomClass.fromInt(classId);
	details = new OrderDetails(request);
	status = OrderStatus.NEW;
    }

    public Order(ResultSet resultSet) {
	try {
	    roomClass = RoomClass.fromInt(resultSet.getInt(ROOM_CLASS_ID.v));
	    status = OrderStatus.fromInt(resultSet.getInt(STATUS_ID.v));
	    createTime = resultSet.getTimestamp(CREATE_TIME.v);
	    id = resultSet.getLong(ID.v);

	    final long orderDetailsId = resultSet.getLong(DETAILS_ID.v);
	    details = new OrderDetails(resultSet, orderDetailsId);
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
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
