package com.adrianstypinski.datamodel;

import java.text.SimpleDateFormat;
import java.util.*;

public class Location {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/YYYY");
    private String province_state;
    private String country_region;
    private double latitude;
    private double longitude;
    private Map<String, Integer> cases;
    private int newestNumberOfCases;

    public Location(String province_state, String country_region, double latitude, double longitude) {
        this.province_state = province_state;
        this.country_region = country_region;
        this.latitude = latitude;
        this.longitude = longitude;
        cases = new LinkedHashMap<>();
    }

    public Location() {
        cases = new HashMap<>();
    }

    public void addNewDate(String date, int confirmedCases) {
        cases.put(date, confirmedCases);
    }

    public void setProvince_state(String province_state) {
        this.province_state = province_state;
    }

    public void setCountry_region(String country_region) {
        this.country_region = country_region;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setNewestNumberOfCases(int newestNumberOfCases) {
        this.newestNumberOfCases = newestNumberOfCases;
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

    public int getNewestNumberOfCases() {
        return newestNumberOfCases;
    }

    @Override
    public String toString() {
        return "Location{" +
                "province_state='" + province_state + '\'' +
                ", country_region='" + country_region + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", cases=" + cases +
                ", newestNumberOfCases=" + newestNumberOfCases +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;

        Location location = (Location) o;

        if (!Objects.equals(province_state, location.province_state))
            return false;
        return country_region.equals(location.country_region);
    }

    @Override
    public int hashCode() {
        int result = province_state != null ? province_state.hashCode() : 0;
        result = 31 * result + country_region.hashCode();
        return result;
    }
}
