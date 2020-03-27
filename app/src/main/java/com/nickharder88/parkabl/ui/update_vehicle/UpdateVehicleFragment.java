package com.nickharder88.parkabl.ui.update_vehicle;

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

public class UpdateVehicleFragment extends Fragment {


    private final String TAG = "UpdateVehicleFragment";
    private static final String ARG_VEHICLE_MAKE = "vehicle_make";
    private static final String ARG_VEHICLE_MODEL = "vehicle_model";
    private static final String ARG_VEHICLE_LICENSE = "vehicle_license";
    private static final String ARG_VEHICLE_LOCATION = "vehicle_location";

    private Button mUpdateButton;
    private Button mDeleteButton;
    private TextInputLayout mMakeText;
    private TextInputLayout mModelText;
    private TextInputLayout mLicenseText;
    private TextInputLayout mLocationText;

    private String mMake;
    private String mModel;
    private String mLicense;
    private String mLocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMake = getArguments().getSerializable(ARG_VEHICLE_MAKE).toString();
        mModel = getArguments().getSerializable(ARG_VEHICLE_MODEL).toString();
        mLicense = getArguments().getSerializable(ARG_VEHICLE_LICENSE).toString();
        mLocation = getArguments().getSerializable(ARG_VEHICLE_LOCATION).toString();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update_vehicle, container, false);
        mUpdateButton = v.findViewById(R.id.update_button);
        mDeleteButton = v.findViewById(R.id.delete_button);
        mMakeText = v.findViewById(R.id.vehicle_make);
        mModelText = v.findViewById(R.id.vehicle_model);
        mLicenseText = v.findViewById(R.id.vehicle_license);
        mLocationText = v.findViewById(R.id.vehicle_location);

        mMakeText.getEditText().setText(mMake);
        mModelText.getEditText().setText(mModel);
        mLicenseText.getEditText().setText(mLicense);
        mLocationText.getEditText().setText(mLocation);


        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vehicleMake = mMakeText.getEditText().getText().toString();
                String vehicleModel = mModelText.getEditText().getText().toString();
                String vehicleLicense = mLicenseText.getEditText().getText().toString();
                String vehicleLocation = mLocationText.getEditText().getText().toString();

                Vehicle toUpdate = new Vehicle(vehicleMake, vehicleModel, vehicleLicense, vehicleLocation);
                VehicleRepository repo = new VehicleRepository(getContext());
                repo.updateVehicle(toUpdate);

                final Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vehicleLicense = mLicenseText.getEditText().getText().toString();

                VehicleRepository repo = new VehicleRepository(getContext());
                repo.deleteVehicle(vehicleLicense);

                final Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        Log.i(TAG, "Fragment in onCreateView");
        return v;
    }

    public static UpdateVehicleFragment newInstance(String make, String model, String license, String location) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_VEHICLE_MAKE, make);
        args.putSerializable(ARG_VEHICLE_MODEL, model);
        args.putSerializable(ARG_VEHICLE_LICENSE, license);
        args.putSerializable(ARG_VEHICLE_LOCATION, location);


        UpdateVehicleFragment fragment = new UpdateVehicleFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
