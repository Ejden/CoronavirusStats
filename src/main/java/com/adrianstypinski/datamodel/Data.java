package com.adrianstypinski.datamodel;

import com.adrianstypinski.util.ResponseParser;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class Data {
    private final List<Location> locations;
    private final List<Point> points;
    private final ResponseParser responseParser;

    @Autowired
    public Data(ResponseParser responseParser) {
        this.responseParser = responseParser;
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

    @JsonAnyGetter
    public String getPoints() {
        try {
            return responseParser.getPoints(points);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @JsonAnyGetter
    public String getLocations() {
        try {
            return responseParser.getLocations(locations);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @JsonAnyGetter
    public String getCasesHistory(int id) {
        try {
            return responseParser.getCasesHistory(locations.get(id).getCasesHistory());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
