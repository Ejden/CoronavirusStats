package com.adrianstypinski.datamodel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class Data {

    private final List<Location> locations;
    private final List<Point> points;

    public Data() {
        locations = LocationDAO.downloadData();
        points = new ArrayList<>();
        generatePoints();
    }

    public void updateData() {
        List<Location> downloadData = LocationDAO.downloadData();
        if (downloadData == null) {
            log.error("UNABLE TO DOWNLOAD NEW DATA!");
        } else {
            log.info("DATA DOWNLOADED");
            this.locations.clear();
            this.locations.addAll(downloadData);
            generatePoints();
        }
    }

    private void generatePoints() {
        if (!points.isEmpty()) {
            points.clear();
        }

        if (!locations.isEmpty()) {
            locations.forEach(location -> points.add(location.toPoint()));
        }
    }

    public List<Point> getPoints() {
        return Collections.unmodifiableList(points);
    }

    public List<Location> getLocations() {
        return Collections.unmodifiableList(locations);
    }

    public String[] getTitles() {
        List<String> dates = new ArrayList<>();
        Set<Calendar> calendars = locations.get(0).getCases().keySet();
        calendars.forEach(calendar -> {
            String date = calendar.get(Calendar.DAY_OF_MONTH) + "/"
                    + Calendar.MONTH + "/"
                    + Calendar.YEAR;
            dates.add(date);
        });

        String[] allDates = new String[dates.size()];
        dates.toArray(allDates);
        return allDates;
    }
}
