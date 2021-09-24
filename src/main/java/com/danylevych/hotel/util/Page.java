package com.danylevych.hotel.util;

import java.util.Arrays;
import java.util.List;

public enum Page {
    HOME(""),
    ROOM_LIST("roomList.jsp"),
    ORDER_LIST("orderList.jsp"),
    BOOKING_LIST("bookingList.jsp"),
    PROFILE("client.jsp", "manager.jsp");

    private List<String> values;

    Page(String... values) {
	this.values = Arrays.asList(values);
    }

    public static boolean isActive(String currentPage, Page... pages) {
	return Arrays.asList(pages)
	             .stream()
	             .anyMatch(page -> page.values.contains(currentPage));
    }

    public static String highlightIfActive(String currentPage, Page page) {
	return isActive(currentPage, page) ? "active" : "";
    }
}