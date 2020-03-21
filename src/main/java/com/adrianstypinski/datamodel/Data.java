package com.adrianstypinski.datamodel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class Data {

    private final List<Location> locations;

    public Data() {
        locations = LocationDAO.downloadData();
    }

    public void updateData() {
        List<Location> locations = LocationDAO.downloadData();
        if (locations == null) {
            log.error("UNABLE TO DOWNLOAD NEW DATA!");
        } else {
            log.info("DATA DOWNLOADED");
            this.locations.clear();
            this.locations.addAll(locations);
        }
    }

    public void addLocation(Location location) {

    }

    public void deleteLocation(Location location) {

    }

    public List<Location> getLocations() {
        return Collections.unmodifiableList(locations);
    }
}
