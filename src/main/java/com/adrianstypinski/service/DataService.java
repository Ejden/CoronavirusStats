package com.adrianstypinski.service;

import com.adrianstypinski.datamodel.Data;

public interface DataService {
    void updateData();

    Data getData();

    String getPoints(boolean param1);

    String getLocations();

    String getCasesHistory(int id);
}
