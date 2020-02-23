package com.nickharder88.parkabl;

import androidx.fragment.app.Fragment;

public class HomeActivity extends DoubleFragmentActivity {

    @Override
    protected Fragment createFragmentOne() { return new VehicleListFragment(); }

    @Override
    protected Fragment createFragmentTwo() {
        return new ScanFragment();
    }
}
