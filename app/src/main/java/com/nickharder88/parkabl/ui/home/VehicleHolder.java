package com.nickharder88.parkabl.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nickharder88.parkabl.R;
import com.nickharder88.parkabl.data.model.Vehicle;

public class VehicleHolder extends RecyclerView.ViewHolder {

    private TextView mMake;
    private TextView mModel;
    private TextView mLicense;

    private Vehicle mVehicle;

    public VehicleHolder(View itemView) {
        super(itemView);
        mMake = itemView.findViewById(R.id.vehicle_make);
        mModel = itemView.findViewById(R.id.vehicle_model);
        mLicense = itemView.findViewById(R.id.vehicle_license);
    }

    public void bind(Vehicle vehicle) {
        mVehicle = vehicle;
        mMake.setText(mVehicle.getMake());
        mModel.setText(mVehicle.getModel());
        mLicense.setText(mVehicle.getLicense());
    }
}
