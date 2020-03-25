package com.adrianstypinski.datamodel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class Data {
    private final List<Location> locations;
    private final List<Point> points;

    @Autowired
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

    public Map<Calendar, Integer> getCasesHistory(int id) {
        return Collections.unmodifiableMap(locations.get(id).getCases());
    }
}
