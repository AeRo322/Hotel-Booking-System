package com.danylevych.hotel.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.danylevych.hotel.entity.User;

public final class Loggers {

    private static final Logger LOGGER = LogManager.getLogger();

    private Loggers() {

    }

    public static void log(Throwable throwable) {
	LOGGER.catching(throwable);
    }

    public static void log(String message) {
	LOGGER.info(message);
    }

    public static void log(HttpServletRequest request, String info) {
	String userEmail = "unknown user";
	User user = (User) request.getSession().getAttribute("user");
	if (user != null) {
	    userEmail = user.getEmail();
	}

	LOGGER.info("{}: {}", userEmail, info);
    }
}
