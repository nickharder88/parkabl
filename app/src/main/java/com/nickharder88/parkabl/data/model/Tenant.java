package com.nickharder88.parkabl.data.model;

import com.nickharder88.parkabl.data.dto.TenantDTO;

public class Tenant {
    public static String collection = "tenants";

    public String id;
    public TenantDTO data;

    public Tenant(String id, TenantDTO data) {
        this.id = id;
        this.data = data;
    }
}
