package com.adrianstypinski.service;

import com.adrianstypinski.datamodel.Data;
import com.adrianstypinski.datamodel.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataServiceImpl implements DataService {
    private final Data data;

    @Autowired
    public DataServiceImpl(Data data) {
        this.data = data;
    }

    @Override
    public void updateData() {
        data.updateData();
    }

    @Override
    public void addLocation(Location location) {
        data.addLocation(location);
    }

    @Override
    public void deleteLocation(Location location) {
        data.deleteLocation(location);
    }

    @Override
    public Data getData() {
        return data;
    }
}
