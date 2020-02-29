package com.nickharder88.parkabl.ui.home;

import androidx.fragment.app.Fragment;

import com.nickharder88.parkabl.DoubleVerticalFragmentActivity;

public class HomeActivity extends DoubleVerticalFragmentActivity {

    @Override
    protected Fragment createFragmentOne() { return new VehicleListFragment(); }

    @Override
    protected Fragment createFragmentTwo() {
        return new ScanFragment();
    }
}
