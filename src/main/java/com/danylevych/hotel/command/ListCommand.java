package com.danylevych.hotel.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danylevych.hotel.dao.JdbcDao;
import com.danylevych.hotel.dao.DaoException;
import com.danylevych.hotel.dao.DaoFactory;
import com.danylevych.hotel.dao.OrderDao;
import com.danylevych.hotel.dao.RoomDao;
import com.danylevych.hotel.util.Functions;
import com.danylevych.hotel.util.Validator;

public class ListCommand implements Command {

    private int limit = 10;
    private int pageRange = 10;

    private String orderBy;
    private boolean isAscending = true;

    @Override public String execute(HttpServletRequest request,
            HttpServletResponse response) {

	DaoFactory daoFactory = DaoFactory.getInstance(DaoFactory.MY_SQL);
	String entity = request.getParameter("entity");
	JdbcDao<?> dao;
	String url;

	switch (entity) {
	case OrderDao.TABLE_NAME:
	    dao = daoFactory.getOrderDao();
	    url = "/WEB-INF/user/orderList.jsp";
	    break;

	case RoomDao.TABLE_NAME:
	    dao = daoFactory.getRoomDao();
	    url = "/WEB-INF/menu/roomList.jsp";
	    break;

	default:
	    throw new IllegalArgumentException("Entity = "
	                                       + entity);
	}

	int page;

	String requestedSortField = request.getParameter("orderBy");

	if (Validator.validateSortField(entity, requestedSortField)) {
	    if (requestedSortField.equals(orderBy)) {
		isAscending = !isAscending;
	    } else {
		orderBy = requestedSortField;
	    }
	    page = 1;
	} else {
	    page = Functions.applyOrDefault(request.getParameter("page"),
	            Integer::parseInt, 1);
	}

	final int offset = (page - 1) * limit;
	final int nRows;
	List<?> list;

	try {
	    list = dao.list(limit, offset, orderBy, isAscending);
	    nRows = dao.count();
	} catch (DaoException e) {
	    throw new IllegalArgumentException(e);
	}

	final int x = nRows / limit;
	final int nPages = x + ((nRows % limit != 0) ? 1 : 0);
	final int nPageLinks = Math.min(pageRange, nPages);

	page = x - (nRows - offset) / limit + 1;

	int firstPage = Math.min(Math.max(0, page - (pageRange / 2)),
	        nPages - nPageLinks);

	int[] pages = new int[nPageLinks];
	for (int j = 0; j < pages.length; j++) {
	    pages[j] = ++firstPage;
	}

	request.setAttribute("list", list);
	request.setAttribute("page", page);
	request.setAttribute("pages", pages);
	request.setAttribute("isFirstPage", offset == 0);
	request.setAttribute("isLastPage", offset + limit >= nRows);

	return url;
    }

}
