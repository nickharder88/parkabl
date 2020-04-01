package com.nickharder88.parkabl.ui.main.home;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.nickharder88.parkabl.R;
import com.nickharder88.parkabl.data.model.Vehicle;

public class VehicleHolder extends RecyclerView.ViewHolder {

    private TextView mMake;
    private TextView mModel;
    private TextView mLicense;
    private TextView mLocation;

    private Vehicle mVehicle;

    public VehicleHolder(View itemView) {
        super(itemView);
        mMake = itemView.findViewById(R.id.vehicle_make);
        mModel = itemView.findViewById(R.id.vehicle_model);
        mLicense = itemView.findViewById(R.id.vehicle_license);
        mLocation = itemView.findViewById(R.id.vehicle_location);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_updateVehicleFragment);
            }
        });
    }

    public void bind(Vehicle vehicle) {
        mVehicle = vehicle;
        mMake.setText(mVehicle.getMake());
        mModel.setText(mVehicle.getModel());
        mLicense.setText(mVehicle.getLicense());
        mLocation.setText(mVehicle.getLocation());
    }
}
