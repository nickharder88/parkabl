package com.nickharder88.parkabl.data.model;

public class Vehicle {

    private String make;
    private String model;
    private String license;
    private String location;

    public Vehicle() {

    }

    public Vehicle(String make, String model, String license, String location) {
        this.make = make;
        this.model = model;
        this.license = license;
        this.location = location;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getLicense() {
        return license;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
