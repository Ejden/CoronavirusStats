package com.adrianstypinski.service;

import com.adrianstypinski.datamodel.Data;
import com.adrianstypinski.datamodel.Point;
import com.adrianstypinski.util.ResponseParser;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataServiceImpl implements DataService {
    private final Data data;
    private final ResponseParser responseParser;

    @Autowired
    public DataServiceImpl(Data data, ResponseParser responseParser) {
        this.data = data;
        this.responseParser = responseParser;
    }

    @Override
    public void updateData() {
        data.updateData();
    }

    @Override
    public Data getData() {
        return data;
    }

    @JsonAnyGetter
    @Override
    public String getPoints(boolean onlyWithActiveCases) {
        List<Point> allPoints = data.getPoints();

        try {
            if (onlyWithActiveCases) {
                return responseParser.getPoints(
                        allPoints.stream()
                                .filter(point -> point.getCases() > 0)
                                .collect(Collectors.toList())
                );
            } else {
                return responseParser.getPoints(allPoints);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @JsonAnyGetter
    @Override
    public String getLocations() {
        try {
            return responseParser.getLocations(data.getLocations());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @JsonAnyGetter
    @Override
    public String getCasesHistory(int id) {
        try {
            return responseParser.getCasesHistory(data.getCasesHistory(id));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
