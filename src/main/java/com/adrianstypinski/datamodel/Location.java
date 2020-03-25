package com.adrianstypinski.datamodel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Location {
    // == PUBLIC FIELDS ==
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("mm/dd/YYYY");
    private static int idValue = 0;

    // == PRIVATE FIELDS ==
    private int id;
    private String province_state;
    private String country_region;
    private double latitude;
    private double longitude;
    private TreeMap<Calendar, Integer> cases;

    // == CONSTRUCTORS ==
    public Location(String province_state, String country_region, double latitude, double longitude) {
        this.id = idValue++;
        this.province_state = province_state;
        this.country_region = country_region;
        this.latitude = latitude;
        this.longitude = longitude;
        cases = new TreeMap<>();

    }

    public int getId() {
        return id;
    }

    public String getProvince_state() {
        return province_state;
    }

    public String getCountry_region() {
        return country_region;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public TreeMap<Calendar, Integer> getCases() {
        return cases;
    }

    // == PUBLIC METHODS ==
    public void addNewDate(Calendar date, int confirmedCases) {
        cases.put(date, confirmedCases);
    }

    public int getActualNumberOfCases() {
        return cases.lastEntry().getValue();
    }

    public Map<Calendar, Integer> getCasesHistory() {
        return Collections.unmodifiableMap(cases);
    }

    // == METHOD FOR CREATING NEW POINT FROM LOCATION ==

    public Point toPoint() {
        return new Point(id, province_state, country_region, latitude, longitude, getActualNumberOfCases());
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", province_state='" + province_state + '\'' +
                ", country_region='" + country_region + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", cases=" + cases +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;

        Location location = (Location) o;

        return id == location.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
