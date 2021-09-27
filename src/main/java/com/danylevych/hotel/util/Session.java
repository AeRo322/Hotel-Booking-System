package com.danylevych.hotel.util;

import javax.servlet.http.HttpServletRequest;

import com.danylevych.hotel.entity.User;

public final class Session {

    private Session() {

    }

    public static User getUser(HttpServletRequest request) {
	return (User) request.getSession().getAttribute("user");

    }

    public static void saveUser(HttpServletRequest request, User user) {
	request.getSession().setAttribute("user", user);
    }
}
