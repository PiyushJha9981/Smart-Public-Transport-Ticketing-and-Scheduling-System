package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BusBooking {

    private final String username;
    private final String fromCity;
    private final String toCity;
    private final LocalDate travelDate;
    private final String busName;
    private final String timing;
    private final List<String> seatNumbers;
    private final double totalPrice;
    private final LocalDateTime bookedAt;

    public BusBooking(
            String username,
            String fromCity,
            String toCity,
            LocalDate travelDate,
            String busName,
            String timing,
            List<String> seatNumbers,
            double totalPrice,
            LocalDateTime bookedAt
    ) {
        this.username = username;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.travelDate = travelDate;
        this.busName = busName;
        this.timing = timing;
        this.seatNumbers = seatNumbers;
        this.totalPrice = totalPrice;
        this.bookedAt = bookedAt;
    }

    public String getUsername() {
        return username;
    }

    public String getFromCity() {
        return fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public String getBusName() {
        return busName;
    }

    public String getTiming() {
        return timing;
    }

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }
}
