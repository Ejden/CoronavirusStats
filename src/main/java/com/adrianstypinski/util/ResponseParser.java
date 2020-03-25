package com.adrianstypinski.util;

import com.adrianstypinski.datamodel.Location;
import com.adrianstypinski.datamodel.Point;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public interface ResponseParser {
    String getPoints(List<Point> s) throws JsonProcessingException;

    String getLocations(List<Location> s) throws JsonProcessingException;

    String getCasesHistory(Map<Calendar, Integer> s) throws JsonProcessingException;
}
