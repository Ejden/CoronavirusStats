package com.adrianstypinski.service;

import com.adrianstypinski.datamodel.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Data getData() {
        return data;
    }
}
