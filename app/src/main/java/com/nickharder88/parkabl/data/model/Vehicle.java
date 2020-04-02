package com.nickharder88.parkabl.data.model;

import com.nickharder88.parkabl.data.dto.VehicleDTO;

public class Vehicle {

    public String id;
    public VehicleDTO data;

    public Vehicle(String id, VehicleDTO data) {
        this.id = id;
        this.data = data;
    }
}
