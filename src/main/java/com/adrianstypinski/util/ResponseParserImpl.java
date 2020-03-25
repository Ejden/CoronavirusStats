package com.adrianstypinski.util;

import com.adrianstypinski.datamodel.Location;
import com.adrianstypinski.datamodel.Point;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResponseParserImpl implements ResponseParser {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getPoints(List<Point> s) throws JsonProcessingException {
        return objectMapper.writeValueAsString(s);
    }

    @Override
    public String getLocations(List<Location> s) throws JsonProcessingException {
        return objectMapper.writeValueAsString(s);
    }

    @Override
    public String getCasesHistory(Map<Calendar, Integer> s) throws JsonProcessingException {
        Map<String, Integer> output = new LinkedHashMap<>();

        for (Map.Entry<Calendar, Integer> entry : s.entrySet()) {
            String date = entry.getKey().get(Calendar.DAY_OF_MONTH) + "/"
                    + (entry.getKey().get(Calendar.MONTH)+1) + "/"
                    + entry.getKey().get(Calendar.YEAR);

            output.put(date, entry.getValue());
        }

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(output);
    }
}
