package com.danylevych.hotel.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;

@WebFilter("/*")
public class LocaleFilter implements Filter {

    private static final String LOCALE = "locale";

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

	HttpServletRequest httpRequest = (HttpServletRequest) request;

	if (httpRequest.getSession().getAttribute(LOCALE) != null) {
	    chain.doFilter(request, response);
	    return;
	}

	ServletContext context = httpRequest.getServletContext();
	Enumeration<Locale> userLocales = httpRequest.getLocales();
	Properties appLocales = (Properties) context.getAttribute("locales");

	while (userLocales.hasMoreElements()) {
	    String language = userLocales.nextElement().getLanguage();

	    if (appLocales.containsKey(language)) {
		setLocale(httpRequest, response, chain, context, language);
		return;
	    }
	}

	String defaultLocale = context.getInitParameter(LOCALE);
	setLocale(httpRequest, response, chain, context, defaultLocale);
    }

    private void setLocale(HttpServletRequest request, ServletResponse response,
            FilterChain chain, ServletContext context, String language)
            throws IOException, ServletException {
	context.setAttribute(LOCALE, language);
	Config.set(request.getSession(), Config.FMT_LOCALE, language);
	chain.doFilter(request, response);
    }

}
