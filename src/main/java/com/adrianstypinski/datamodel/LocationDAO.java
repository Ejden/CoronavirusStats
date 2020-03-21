package com.adrianstypinski.datamodel;

import com.adrianstypinski.exceptions.ResponseCodeException;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class LocationDAO {
    public static final String DATA_LINK = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

    public static List<Location> downloadData() {
        try {
            URL url = new URL(DATA_LINK);
            List<String> rawData = getDataFromSite(url);
            return parseData(rawData);

        } catch (MalformedURLException e) {
            log.error("There was an error while creating URL for data.\n{}", e.getMessage());
            return null;
        } catch (IOException | ResponseCodeException e) {
            log.error("There was an error while downloading data.\n{}", e.getMessage());
            return null;
        }
    }

    private static List<String> getDataFromSite(URL url) throws IOException, ResponseCodeException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(10000);

        int responseCode = connection.getResponseCode();

        if (responseCode != 200) {
            throw new ResponseCodeException("Response code is not equal 200");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        List<String> rawData = new ArrayList<>();

        String input;
        while ((input = reader.readLine()) != null) {
//            log.info(input);
            rawData.add(input);
        }

        return rawData;
    }

    private static List<Location> parseData(List<String> rawData) {
        List<Location> data = new ArrayList<>();
        Pattern pattern = Pattern.compile("(?:,|\\n|^)(\"(?:(?:\"\")*[^\"]*)*\"|[^\",\\n]*|(?:\\n|$))");
        List<String> title = new ArrayList<>(Arrays.asList(rawData.get(0).split(",")));

        for (int i = 1; i < rawData.size(); i++) {
            String record = rawData.get(i);

            //Setting up matcher for finding new cells
            Matcher matcher = pattern.matcher(record);

            //Creating new location
            Location location = new Location();

            //Variable indicates in which cell we are currently
            int pos = 0;

            //Checking if record has null in Province cell
            if (record.startsWith(",")) {
                location.setProvince_state("");
                pos++;
            }

            String cell = null;
            //Finding every cell
            while (matcher.find()) {
                cell = matcher.group();

                //Deleting coma from first letter
                if (cell.startsWith(",")) {
                    cell = cell.substring(1);
                }

                //Setting up location
                switch (pos) {
                    case 0:
                        location.setProvince_state(cell);
                        break;
                    case 1:
                        location.setCountry_region(cell);
                        break;
                    case 2:
                        location.setLatitude(Double.parseDouble(cell));
                        break;
                    case 3:
                        location.setLongitude(Double.parseDouble(cell));
                        break;
                    default:
                        location.addNewDate(title.get(pos), Integer.parseInt(cell));
                }
                pos++;
            }

            //Setting last cell (the newest value) as newest number of cases
            location.setNewestNumberOfCases(Integer.parseInt(cell));

//            log.info("Location created: {}", location);
            data.add(location);
        }
        return data;
    }

    public static void main(String[] args) {
        List<Location> locations = downloadData();

    }
}
