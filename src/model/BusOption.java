package model;

public class BusOption {
    private final String name;
    private final String departTime;
    private final String arriveTime;
    private final double pricePerSeat;

    public BusOption(String name, String departTime, String arriveTime, double pricePerSeat) {
        this.name = name;
        this.departTime = departTime;
        this.arriveTime = arriveTime;
        this.pricePerSeat = pricePerSeat;
    }

    public String getName() {
        return name;
    }

    public String getDepartTime() {
        return departTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public double getPricePerSeat() {
        return pricePerSeat;
    }

    public String getTimingText() {
        return departTime + " - " + arriveTime;
    }
}
