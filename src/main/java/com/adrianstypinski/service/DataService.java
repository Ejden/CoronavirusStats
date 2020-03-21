package com.adrianstypinski.service;

import com.adrianstypinski.datamodel.Data;
import com.adrianstypinski.datamodel.Location;
import com.adrianstypinski.datamodel.LocationDAO;

import java.util.Collections;
import java.util.List;

public interface DataService {
    public void updateData();

    public void addLocation(Location location);

    public void deleteLocation(Location location);

    public Data getData();
}
