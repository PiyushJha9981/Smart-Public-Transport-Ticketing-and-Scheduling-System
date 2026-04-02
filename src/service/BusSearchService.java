package service;

import model.BusOption;

import java.util.ArrayList;
import java.util.List;

public final class BusSearchService {

    private BusSearchService() {}

    public static List<BusOption> search(String fromCity, String toCity) {
        // Dummy data (can be expanded easily)
        List<BusOption> options = new ArrayList<>();

        options.add(new BusOption("CityExpress", "07:30", "10:15", 250));
        options.add(new BusOption("MetroLink", "09:00", "12:05", 220));
        options.add(new BusOption("RapidBus", "12:15", "15:45", 280));
        options.add(new BusOption("NightRider", "18:30", "22:10", 300));

        return options;
    }
}
