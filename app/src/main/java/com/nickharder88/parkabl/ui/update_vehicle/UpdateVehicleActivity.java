package com.nickharder88.parkabl.ui.update_vehicle;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.nickharder88.parkabl.SingleFragmentActivity;

public class UpdateVehicleActivity extends SingleFragmentActivity {

    public static final String EXTRA_VEHICLE_MAKE = "com.nickharder88.parkabl.ui.update_vehicle.vehicle_make";
    public static final String EXTRA_VEHICLE_MODEL = "com.nickharder88.parkabl.ui.update_vehicle.vehicle_model";
    public static final String EXTRA_VEHICLE_LICENSE = "com.nickharder88.parkabl.ui.update_vehicle.vehicle_license";
    public static final String EXTRA_VEHICLE_LOCATION = "com.nickharder88.parkabl.ui.update_vehicle.vehicle_location";

    @Override
    protected Fragment createFragment() {
        return UpdateVehicleFragment.newInstance(getIntent().getSerializableExtra(EXTRA_VEHICLE_MAKE).toString(), getIntent().getSerializableExtra(EXTRA_VEHICLE_MODEL).toString(), getIntent().getSerializableExtra(EXTRA_VEHICLE_LICENSE).toString(), getIntent().getSerializableExtra(EXTRA_VEHICLE_LOCATION).toString());
    }

    public static Intent newIntent(Context packageContext, String make, String model, String license, String location) {
        Intent intent = new Intent(packageContext, UpdateVehicleActivity.class);
        intent.putExtra(EXTRA_VEHICLE_MAKE, make);
        intent.putExtra(EXTRA_VEHICLE_MODEL, model);
        intent.putExtra(EXTRA_VEHICLE_LICENSE, license);
        intent.putExtra(EXTRA_VEHICLE_LOCATION, location);
        return intent;
    }
}
