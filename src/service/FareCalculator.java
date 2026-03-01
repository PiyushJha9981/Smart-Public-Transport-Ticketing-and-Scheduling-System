package service;

public class FareCalculator {

    public static double calculateFare(String transport, double distance) {
        switch (transport) {
            case "Bus": return distance * 2;
            case "Metro": return distance * 3;
            case "Taxi": return distance * 10;
            case "Train": return distance * 5;
            default: return 0;
        }
    }
}