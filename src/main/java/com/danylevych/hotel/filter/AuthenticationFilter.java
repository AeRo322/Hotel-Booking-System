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

@WebFilter("/auth/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

	HttpServletResponse httpResponse = (HttpServletResponse) response;
	HttpServletRequest httpRequest = (HttpServletRequest) request;

	User user = (User) httpRequest.getSession().getAttribute("user");
	if (user == null) {
	    chain.doFilter(request, response);
	    return;
	}

	String userRole = user.getRole().toString();
	String homepage = httpRequest.getContextPath();

	httpResponse.sendRedirect(homepage + "/user/" + userRole + ".jsp");
    }
}
