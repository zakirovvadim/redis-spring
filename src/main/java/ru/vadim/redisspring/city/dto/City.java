package ru.vadim.redisspring.city.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@ToString
public class City {
    @JsonProperty("zip")
    private String zip; // Был String, изменён на int

    @JsonProperty("lat")
    private double lat; // Был String, изменён на double

    @JsonProperty("lng")
    private double lng; // Был String, изменён на double

    @JsonProperty("city")
    private String city;

    @JsonProperty("stateId")
    private String stateId;

    @JsonProperty("stateName")
    private String stateName;

    @JsonProperty("population")
    private int population;

    @JsonProperty("temperature")
    private int temperature;

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}

/*
  {
    "zip": 44028,
    "lat": 41.31019,
    "lng": -81.939,
    "city": "Columbia Station",
    "stateId": "OH",
    "stateName": "Ohio",
    "population": 8938,
    "density": 104,
    "temperature": 1
  },
 */
