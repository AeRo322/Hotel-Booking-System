package com.danylevych.hotel.util;

import java.sql.Connection;
import java.sql.SQLException;

public final class DbUtils {

    private DbUtils() {

    }

    public static void rollback(Connection connection) {
	if (connection != null) {
	    try {
		connection.rollback();
	    } catch (SQLException e) {
		Loggers.log(e);
	    }
	}
    }

    public static void close(AutoCloseable autoCloseable) {
	if (autoCloseable != null) {
	    try {
		autoCloseable.close();
	    } catch (Exception e) {
		Loggers.log(e);
	    }
	}
    }
}
