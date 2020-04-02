package com.nickharder88.parkabl.ui.main.new_vehicle;

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

import androidx.navigation.Navigation;
import com.google.android.material.textfield.TextInputLayout;
import com.nickharder88.parkabl.R;
import com.nickharder88.parkabl.data.VehicleRepository;
import com.nickharder88.parkabl.data.dto.VehicleDTO;
import com.nickharder88.parkabl.data.model.Vehicle;
import com.nickharder88.parkabl.ui.main.home.HomeFragment;

public class CreateVehicleFragment extends Fragment {

    private final String TAG = "CreateVehicleFragment";

    private Button mCreateButton;
    private TextInputLayout mMakeText;
    private TextInputLayout mModelText;
    private TextInputLayout mLicenseText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_vehicle, container, false);
        mCreateButton = v.findViewById(R.id.create_button);
        mMakeText = v.findViewById(R.id.vehicle_make);
        mModelText = v.findViewById(R.id.vehicle_model);
        mLicenseText = v.findViewById(R.id.vehicle_license);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vehicleMake = mMakeText.getEditText().getText().toString();
                String vehicleModel = mModelText.getEditText().getText().toString();
                String vehicleLicense = mLicenseText.getEditText().getText().toString();

                VehicleDTO dto = new VehicleDTO();
                dto.make = vehicleMake;
                dto.model = vehicleModel;
                dto.license = vehicleLicense;


                VehicleRepository repo = new VehicleRepository(getContext());
                repo.add(dto);

                Navigation.findNavController(v).navigate(R.id.action_createVehicleFragment_to_homeFragment);
            }
        });

        return v;
    }
}
