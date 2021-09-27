package com.danylevych.hotel.entity;

import static com.danylevych.hotel.entity.OrderDetails.Column.CHECK_IN;
import static com.danylevych.hotel.entity.OrderDetails.Column.CHECK_OUT;
import static com.danylevych.hotel.entity.OrderDetails.Column.GUESTS;
import static com.danylevych.hotel.entity.OrderDetails.Column.ID;
import static com.danylevych.hotel.entity.OrderDetails.Column.USER_ID;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.OrderDetailsDao;
import com.danylevych.hotel.dao.RoomDao;
import com.danylevych.hotel.util.Session;

public class OrderDetails implements Serializable {

    private static final long serialVersionUID = -5358042958363538412L;

    private int guests;
    private long userId;
    private Date checkIn;
    private Date checkOut;

    private Long id;
    private List<Room> rooms;

    public enum Column {

	USER_ID,
	GUESTS,
	CHECK_IN,
	CHECK_OUT,

	ID;

	public final String v = name().toLowerCase();

    }

    public OrderDetails() {

    }

    public OrderDetails(HttpServletRequest request) {
	try {
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    checkIn = format.parse(request.getParameter(CHECK_IN.v));
	    checkOut = format.parse(request.getParameter(CHECK_OUT.v));
	    guests = Integer.parseInt(request.getParameter(GUESTS.v));
	    userId = Session.getUser(request).getId();
	} catch (ParseException e) {
	    throw new IllegalStateException(e);
	}
    }

    public OrderDetails(ResultSet resultSet) {
	try {
	    guests = resultSet.getInt(GUESTS.v);
	    userId = resultSet.getInt(USER_ID.v);
	    checkIn = resultSet.getDate(CHECK_IN.v);
	    checkOut = resultSet.getDate(CHECK_OUT.v);
	    id = resultSet.getLong(ID.v);

	    DaoFactory instance = DaoFactory.getInstance();
	    OrderDetailsDao orderDetailsDao = instance.getOrderDetailsDao();
	    List<Integer> roomNumbers = orderDetailsDao.getRoomNumbers(id);
	    RoomDao roomDao = instance.getRoomDao();
	    rooms = roomNumbers.isEmpty() ? null
	                                  : roomDao.find(roomNumbers.toArray());
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    }

    public OrderDetails(User user, Room room) {
	userId = user.getId();
	rooms = Arrays.asList(room);
    }

    public long getUserId() {
	return userId;
    }

    public void setUserId(long userId) {
	this.userId = userId;
    }

    public int getGuests() {
	return guests;
    }

    public void setGuests(int guests) {
	this.guests = guests;
    }

    public Date getCheckIn() {
	return checkIn;
    }

    public void setCheckIn(Date checkIn) {
	this.checkIn = checkIn;
    }

    public Date getCheckOut() {
	return checkOut;
    }

    public void setCheckOut(Date checkOut) {
	this.checkOut = checkOut;
    }

    public List<Room> getRooms() {
	return rooms;
    }

    public void setRooms(List<Room> rooms) {
	this.rooms = rooms;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

}
