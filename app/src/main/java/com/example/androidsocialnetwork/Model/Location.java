package com.example.androidsocialnetwork.Model;

public class Location {

    private String address;
    private String city;
    private String country;
    private String county;
    private Integer id;
    private Integer latitude;
    private Integer longitude;
    private String postalCode;
    private String stateProvice;
    private String urlGoogleMaps;
    private String urlOpenStreetMap;

    public Location() {}

    public Location(String address, String city, String country, String county, Integer id, Integer latitude, Integer longitude, String postalCode, String stateProvice, String urlGoogleMaps, String urlOpenStreetMap) {
        this.address = address;
        this.city = city;
        this.country = country;
        this.county = county;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.postalCode = postalCode;
        this.stateProvice = stateProvice;
        this.urlGoogleMaps = urlGoogleMaps;
        this.urlOpenStreetMap = urlOpenStreetMap;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStateProvice() {
        return stateProvice;
    }

    public void setStateProvice(String stateProvice) {
        this.stateProvice = stateProvice;
    }

    public String getUrlGoogleMaps() {
        return urlGoogleMaps;
    }

    public void setUrlGoogleMaps(String urlGoogleMaps) {
        this.urlGoogleMaps = urlGoogleMaps;
    }

    public String getUrlOpenStreetMap() {
        return urlOpenStreetMap;
    }

    public void setUrlOpenStreetMap(String urlOpenStreetMap) {
        this.urlOpenStreetMap = urlOpenStreetMap;
    }
}
