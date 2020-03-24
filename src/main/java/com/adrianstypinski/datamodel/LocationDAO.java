package com.adrianstypinski.datamodel;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Slf4j
public class LocationDAO {
    public static final String DATA_LINK = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

    public static List<Location> downloadData() {
        try {
            URL url = new URL(DATA_LINK);
            CSVParser rawData = getDataFromSite(url);
            return parseData(rawData);
        } catch (MalformedURLException e) {
            log.error("There was an error while creating URL for data.\n{}", e.getMessage());
            return null;
        } catch (IOException e) {
            log.error("There was an error while downloading data.\n{}", e.getMessage());
            return null;
        }
    }

    private static CSVParser getDataFromSite(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(10000);

        InputStreamReader input = new InputStreamReader(connection.getInputStream());

        return CSVFormat.RFC4180.withFirstRecordAsHeader().parse(input);
    }

    private static List<Location> parseData(CSVParser rawData) throws IOException {
        // == Data ==
        // 1st column = Province
        // 2nd column = Country
        // 3rd column = latitude
        // 4th column = longitude
        // 5th column and grater = cases by dates

        List<String> headers = rawData.getHeaderNames();
        List<CSVRecord> records = rawData.getRecords();
        List<Location> locations = new ArrayList<>();

        records.forEach(record -> {
            String province = record.get(0);
            String country = record.get(1);
            double latitude = Double.parseDouble(record.get(2));
            double longitude = Double.parseDouble(record.get(3));

            Location location = new Location(province, country, latitude, longitude);

            for (int i = 4; i < record.size(); i++) {
                String[] date = headers.get(i).split("/");
                int month = Integer.parseInt(date[0]);
                int day = Integer.parseInt(date[1]);
                int year = Integer.parseInt(date[2]);

                @SuppressWarnings("MagicConstant")
                Calendar cal = new GregorianCalendar(year, month-1, day);
                int cases = 0;
                try {
                    cases = Integer.parseInt(record.get(i));
                } catch (NumberFormatException ignored) {}
                    location.addNewDate(cal, cases);
            }

            locations.add(location);
        });

        return locations;
    }
}
