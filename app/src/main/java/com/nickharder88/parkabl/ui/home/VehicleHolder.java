package com.nickharder88.parkabl.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nickharder88.parkabl.R;
import com.nickharder88.parkabl.data.model.Vehicle;
import com.nickharder88.parkabl.ui.update_vehicle.UpdateVehicleActivity;

public class VehicleHolder extends RecyclerView.ViewHolder {

    private TextView mMake;
    private TextView mModel;
    private TextView mLicense;

    private Vehicle mVehicle;

    public VehicleHolder(View itemView, final Activity activity) {
        super(itemView);
        mMake = itemView.findViewById(R.id.vehicle_make);
        mModel = itemView.findViewById(R.id.vehicle_model);
        mLicense = itemView.findViewById(R.id.vehicle_license);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UpdateVehicleActivity.newIntent(activity, mMake.getText().toString(), mModel.getText().toString(), mLicense.getText().toString());
                activity.startActivity(intent);
            }
        });
    }

    public void bind(Vehicle vehicle) {
        mVehicle = vehicle;
        mMake.setText(mVehicle.getMake());
        mModel.setText(mVehicle.getModel());
        mLicense.setText(mVehicle.getLicense());
    }
}
