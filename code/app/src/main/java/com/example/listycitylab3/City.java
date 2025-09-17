package com.example.listycitylab3;

import java.io.Serializable;

public class City implements Serializable {
    private String cityName;
    private String provinceName;

    // Constructor to initialize the city and province name
    public City(String cityName, String provinceName) {
        this.cityName = cityName;
        this.provinceName = provinceName;
    }

    // Getter method for cityName and provinceName
    public String getCityName() {return cityName; }
    public String getProvinceName() {return provinceName; }

    public void setCity(String cityName) {
        this.cityName = cityName;
    }

    public void setProvince(String provinceName) {
        this.provinceName = provinceName;
    }
}
