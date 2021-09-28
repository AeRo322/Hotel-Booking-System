package com.danylevych.hotel.thread;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.danylevych.hotel.dao.BookingDao;
import com.danylevych.hotel.dao.CartDao;
import com.danylevych.hotel.dao.DaoFactory;

public final class PaymentTracker extends Thread {

    private static CartDao cartDao = DaoFactory.getInstance().getCartDao();

    private static Logger logger = LogManager.getLogger();

    private static BookingDao bookingDao =
            DaoFactory.getInstance().getBookingDao();

    @Override
    public void run() {
	while (true) {
	    try {
		cartDao.deleteExpiredCarts();
		bookingDao.closeExpiredBookings();
		bookingDao.closeCompletedBookings();
		bookingDao.updateBookings();
		Thread.sleep(Duration.ofSeconds(5).toMillis());
	    } catch (InterruptedException e) {
		logger.catching(e);
		currentThread().interrupt();
	    }
	}
    }

}
