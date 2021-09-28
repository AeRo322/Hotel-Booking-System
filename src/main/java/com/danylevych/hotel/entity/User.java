package com.danylevych.hotel.entity;

import static com.danylevych.hotel.entity.User.Column.CREATE_TIME;
import static com.danylevych.hotel.entity.User.Column.EMAIL;
import static com.danylevych.hotel.entity.User.Column.FIRST_NAME;
import static com.danylevych.hotel.entity.User.Column.ID;
import static com.danylevych.hotel.entity.User.Column.LAST_NAME;
import static com.danylevych.hotel.entity.User.Column.PASSWORD;
import static com.danylevych.hotel.entity.User.Column.ROLE_ID;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public class User implements Serializable {

    private static final long serialVersionUID = 7225500122023129085L;

    private long id;
    private UserRole role;

    private String firstName;
    private String lastName;

    private String password;
    private String email;

    private Date createTime;
    private Cart cart;

    public enum Column {

	ROLE_ID,
	FIRST_NAME,
	LAST_NAME,
	EMAIL,
	PASSWORD,

	ID,
	CREATE_TIME;

	public final String v = name().toLowerCase();

    }

    public User() {
    }

    public User(ResultSet resultSet) throws SQLException {
	role = UserRole.fromInt(resultSet.getInt(ROLE_ID.v));
	createTime = resultSet.getDate(CREATE_TIME.v);
	firstName = resultSet.getString(FIRST_NAME.v);
	lastName = resultSet.getString(LAST_NAME.v);
	email = resultSet.getString(EMAIL.v);
	id = resultSet.getInt(ID.v);
    }

    public User(HttpServletRequest request) {
	firstName = request.getParameter(FIRST_NAME.v);
	lastName = request.getParameter(LAST_NAME.v);
	password = request.getParameter(PASSWORD.v);
	email = request.getParameter(EMAIL.v);
	role = UserRole.CLIENT;
    }

    public String getFullName() {
	return String.format("%s %s", firstName, lastName);
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public UserRole getRole() {
	return role;
    }

    public void setRole(UserRole role) {
	this.role = role;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public Date getCreateTime() {
	return createTime;
    }

    public void setCreateTime(Date createTime) {
	this.createTime = createTime;
    }

    public Cart getCart() {
	return cart;
    }

    public void setCart(Cart cart) {
	this.cart = cart;
    }

}
