package com.danylevych.hotel.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.entity.User;
import com.danylevych.hotel.util.Validator;

public class RegisterCommand implements Command {

    @Override
    public String execute(HttpServletRequest request,
            HttpServletResponse response) {

	String email = request.getParameter("email");
	if (!Validator.validateEmail(email)) {
	    throw new IllegalArgumentException("Invalid email");
	}

	String password = request.getParameter("password");
	DaoFactory daoFactory = DaoFactory.getInstance(DaoFactory.MY_SQL);
	User user = daoFactory.getUserDao().find(email, password);
	if (user != null) {
	    throw new IllegalArgumentException("Account already exists");
	}

	daoFactory.getUserDao().create(new User(request));

	return request.getContextPath();
    }

}
