package com.chenyi.safe.pojo.vo;

import lombok.Data;

/**
 * @Author：ChenYi
 * @Package：com.chenyi.safe.pojo
 * @Project：network-security-java-backend
 * @name：GeoIPInfo
 * @Date：2024/6/14 下午9:19
 * @Filename：GeoIPInfo
 */
@Data
public class GeoIPInfo {
    private String ip;
    private String city;
    private double longitude;
    private double latitude;

    // Constructor
    public GeoIPInfo(String ip, String city, double longitude, double latitude) {
        this.ip = ip;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    // Getters and Setters
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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