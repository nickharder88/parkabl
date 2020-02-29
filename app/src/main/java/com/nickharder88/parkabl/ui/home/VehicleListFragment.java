package com.nickharder88.parkabl.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;

import com.nickharder88.parkabl.R;
import com.nickharder88.parkabl.ui.new_vehicle.CreateVehicleActivity;

public class VehicleListFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_vehicle_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.new_vehicle:
                final Intent intent = new Intent(getActivity(), CreateVehicleActivity.class);
                startActivity(intent);
                return true;
            case R.id.show_vehicles:
                // set off intent to show all vehicles
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
