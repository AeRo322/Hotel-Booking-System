package com.danylevych.hotel.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

public class SetLocaleCommand implements Command {

    @Override
    public String execute(HttpServletRequest request,
            HttpServletResponse response) {

	String requestedLocale = request.getParameter("locale");

	if (requestedLocale == null) {
	    throw new IllegalArgumentException("No locale parameter found");
	}

	HttpSession session = request.getSession();
	session.setAttribute("locale", requestedLocale);

	Config.set(session, Config.FMT_LOCALE, requestedLocale);

	return request.getHeader("referer");
    }

}
