package com.nickharder88.parkabl.data.model;

import com.nickharder88.parkabl.data.dto.LandlordDTO;

public class Landlord {
    public static String collection = "landlords";

    public String id;
    public LandlordDTO data;

    public Landlord(String id, LandlordDTO data) {
        this.id = id;
        this.data = data;
    }
}
