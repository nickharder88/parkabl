package com.nickharder88.parkabl.data.model;

public class Vehicle {

    private String make;
    private String model;
    private String license;

    public Vehicle() {

    }

    public Vehicle(String make, String model, String license) {
        this.make = make;
        this.model = model;
        this.license = license;
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
}
