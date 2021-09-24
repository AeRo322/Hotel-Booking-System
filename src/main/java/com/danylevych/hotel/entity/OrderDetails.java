package com.danylevych.hotel.entity;

import static com.danylevych.hotel.entity.Order.Column.DETAILS_ID;
import static com.danylevych.hotel.entity.OrderDetails.Column.CHECK_IN;
import static com.danylevych.hotel.entity.OrderDetails.Column.CHECK_OUT;
import static com.danylevych.hotel.entity.OrderDetails.Column.GUESTS;
import static com.danylevych.hotel.entity.OrderDetails.Column.USER_ID;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class OrderDetails implements Entity {

    private static final long serialVersionUID = -5358042958363538412L;

    private long id;
    private int userId;
    private int guests;

    private Date checkIn;
    private Date checkOut;

    private List<Integer> rooms;

    public enum Column {

	ID,
	GUESTS,
	USER_ID,
	CHECK_IN,
	CHECK_OUT;

	public final String v = name().toLowerCase();

    }

    public OrderDetails() {

    }

    public OrderDetails(HttpServletRequest request) throws ParseException {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	checkIn = format.parse(request.getParameter("check_in"));
	checkOut = format.parse(request.getParameter("check_out"));

	guests = Integer.parseInt(request.getParameter("guests"));

	User user = (User) request.getSession().getAttribute("user");
	userId = user.getId();
	
	int roomNumber = Integer.parseInt(request.getParameter("roomNumbers"));
	
	//rooms.add
    }

    public OrderDetails(ResultSet resultSet) throws SQLException {
	guests = resultSet.getInt(GUESTS.v);
	userId = resultSet.getInt(USER_ID.v);
	checkIn = resultSet.getDate(CHECK_IN.v);
	checkOut = resultSet.getDate(CHECK_OUT.v);
	id = resultSet.getLong(DETAILS_ID.v);
    }

    public int getUserId() {
	return userId;
    }

    public void setUserId(int userId) {
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

    public List<Integer> getRooms() {
	return rooms;
    }

    public void setRooms(List<Integer> rooms) {
	this.rooms = rooms;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    @Override
    public Object[] extract() {
	// TODO Auto-generated method stub
	return null;
    }

}
