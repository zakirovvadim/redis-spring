package ru.vadim.redisspring.fib.geo.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GeoLocation {

    private double longitude;
    private double latitude;

    public GeoLocation() {
    }

    public GeoLocation(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
