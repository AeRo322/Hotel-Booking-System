package com.danylevych.hotel.listener;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.danylevych.hotel.thread.PaymentTracker;
import com.danylevych.hotel.util.Loggers;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
	ServletContext context = sce.getServletContext();

	String logPath = context.getRealPath("/WEB-INF/events.log");
	System.setProperty("logPath", logPath);
	Loggers.log("Logs path: "
	            + logPath);

	String localesPath = context.getInitParameter("localesPath");
	localesPath = context.getRealPath(localesPath);
	if (localesPath == null) {
	    String s = "LocalesPath parameter should be present in context";
	    throw new IllegalStateException(s);
	}

	try (FileReader fileReader = new FileReader(localesPath)) {
	    Properties locales = new Properties();
	    locales.load(fileReader);
	    context.setAttribute("locales", locales);
	    Loggers.log("Locales: "
	                + locales);
	} catch (IOException e) {
	    throw new IllegalStateException(e);
	}

	String homepage = context.getContextPath();
	context.setAttribute("homepage", homepage);
	Loggers.log("Homepage: "
	            + homepage);

	new PaymentTracker().start();
    }

}
