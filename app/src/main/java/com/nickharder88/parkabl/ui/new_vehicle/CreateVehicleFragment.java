package com.nickharder88.parkabl.ui.new_vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.nickharder88.parkabl.R;
import com.nickharder88.parkabl.data.VehicleRepository;
import com.nickharder88.parkabl.data.model.Vehicle;
import com.nickharder88.parkabl.ui.home.HomeActivity;

public class CreateVehicleFragment extends Fragment {

    private final String TAG = "CreateVehicleFragment";

    private Button mCreateButton;
    private TextInputLayout mMakeText;
    private TextInputLayout mModelText;
    private TextInputLayout mLicenseText;
    private TextInputLayout mLocationText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_vehicle, container, false);
        mCreateButton = v.findViewById(R.id.create_button);
        mMakeText = v.findViewById(R.id.vehicle_make);
        mModelText = v.findViewById(R.id.vehicle_model);
        mLicenseText = v.findViewById(R.id.vehicle_license);
        mLocationText = v.findViewById(R.id.vehicle_location);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vehicleMake = mMakeText.getEditText().getText().toString();
                String vehicleModel = mModelText.getEditText().getText().toString();
                String vehicleLicense = mLicenseText.getEditText().getText().toString();
                String vehicleLocation = mLocationText.getEditText().getText().toString();

                Vehicle toAdd = new Vehicle(vehicleMake, vehicleModel, vehicleLicense, vehicleLocation);
                VehicleRepository repo = new VehicleRepository(getContext());
                repo.addVehicle(toAdd);

                final Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        Log.i(TAG, "Fragment in onCreateView");
        return v;
    }
}
