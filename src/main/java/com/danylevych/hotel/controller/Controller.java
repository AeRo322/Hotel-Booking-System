package com.danylevych.hotel.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danylevych.hotel.command.CommandContainer;
import com.danylevych.hotel.util.Loggers;

@WebServlet("/do")
public class Controller extends HttpServlet {

    private static final long serialVersionUID = -8170981454815051926L;
    private static final String ERROR_JSP = "/WEB-INF/error/error.jsp";

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) {

	try {
	    String url = CommandContainer.execute(request, response);
	    request.getRequestDispatcher(url).forward(request, response);
	} catch (IOException | ServletException e) {
	    Loggers.log(e);
	    showErrorPage(request, response);
	}
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) {

	try {
	    String url = CommandContainer.execute(request, response);
	    response.sendRedirect(url);
	} catch (IOException e) {
	    Loggers.log(e);
	    showErrorPage(request, response);
	}
    }

    void showErrorPage(ServletRequest request, ServletResponse response) {
	try {
	    request.getRequestDispatcher(ERROR_JSP).forward(request, response);
	} catch (ServletException | IOException e) {
	    Loggers.log(e);
	}
    }
}
