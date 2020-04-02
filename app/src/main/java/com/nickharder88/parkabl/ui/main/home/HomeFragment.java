package com.nickharder88.parkabl.ui.main.home;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.nickharder88.parkabl.R;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Add Child Fragments
        FragmentManager fm = getChildFragmentManager();
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

        return view;
    }
}
