package com.danylevych.hotel.entity;

import java.sql.Date;

public class User implements Entity {

    private static final long serialVersionUID = 7225500122023129085L;

    private int id;
    private UserRole role;

    private String firstName;
    private String lastName;

    private String password;
    private String email;

    private Date createTime;

    public enum Column {

	ID,
	EMAIL,
	ROLE_ID,
	LAST_NAME,
	FIRST_NAME,
	CREATE_TIME;

	public final String v = name().toLowerCase();

    }

    public String getFullName() {
	return String.format("%s %s", firstName, lastName);
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
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

    @Override
    public Object[] extract() {
	// TODO Auto-generated method stub
	return null;
    }


}
