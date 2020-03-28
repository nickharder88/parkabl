package com.nickharder88.parkabl.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.nickharder88.parkabl.R;

public class HomeActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentManager fm = getSupportFragmentManager();
        Fragment location = fm.findFragmentById(R.id.fragment_location);
        Fragment vehicleList = fm.findFragmentById(R.id.fragment_vehicle_list);
        Fragment scan = fm.findFragmentById(R.id.fragment_scan);

        if (location == null) {
            fm.beginTransaction().add(R.id.fragment_location, new LocationFragment()).commit();
        }
        if (vehicleList == null) {
            fm.beginTransaction().add(R.id.fragment_vehicle_list, new VehicleListFragment()).commit();
        }
        if (scan == null) {
            fm.beginTransaction().add(R.id.fragment_scan, new ScanFragment()).commit();
        }
    }
}
