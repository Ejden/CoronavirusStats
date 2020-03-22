package com.adrianstypinski.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Point {
    private String province_state;
    private String country_region;
    private double latitude;
    private double longitude;
    private int cases;
}
