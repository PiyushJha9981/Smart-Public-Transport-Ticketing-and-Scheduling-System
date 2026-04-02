package service;

import model.BusBooking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class BookingHistoryService {

    private static final List<BusBooking> BOOKINGS = Collections.synchronizedList(new ArrayList<>());

    private BookingHistoryService() {}

    public static void add(BusBooking booking) {
        BOOKINGS.add(booking);
    }

    public static List<BusBooking> forUser(String username) {
        List<BusBooking> result = new ArrayList<>();
        if (username == null) return result;

        synchronized (BOOKINGS) {
            for (BusBooking b : BOOKINGS) {
                if (username.equals(b.getUsername())) {
                    result.add(b);
                }
            }
        }
        return result;
    }
}
