package com.danylevych.hotel.dao;

public class DaoException extends Exception {

    private static final long serialVersionUID = -940647043999963986L;

    public DaoException(String message) {
	super(message);
    }

    public DaoException(Throwable e) {
	super(e);
    }

}
