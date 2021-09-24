package com.danylevych.hotel.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danylevych.hotel.entity.User;

@WebFilter("/user/*")
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

	HttpServletResponse httpResponse = (HttpServletResponse) response;
	HttpServletRequest httpRequest = (HttpServletRequest) request;

	User user = (User) httpRequest.getSession().getAttribute("user");

	if (user == null) {
	    String loginURL = httpRequest.getContextPath() + "/auth/login.jsp";
	    httpResponse.sendRedirect(loginURL);
	    return;
	}

	String userRole = user.getRole().toString();

	if (!httpRequest.getRequestURI().contains(userRole)) {
	    httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
	    return;
	}

	chain.doFilter(request, response);
    }
}
