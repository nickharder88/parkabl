package com.nickharder88.parkabl.ui.new_vehicle;

import androidx.fragment.app.Fragment;

import com.nickharder88.parkabl.SingleFragmentActivity;

public class CreateVehicleActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CreateVehicleFragment();
    }
}
