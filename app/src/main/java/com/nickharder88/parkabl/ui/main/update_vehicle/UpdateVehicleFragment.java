package com.nickharder88.parkabl.ui.main.update_vehicle;

import android.os.Bundle;
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

public class UpdateVehicleFragment extends Fragment {

    private final String TAG = "UpdateVehicleFragment";

    private Button mUpdateButton;
    private Button mDeleteButton;
    private TextInputLayout mMakeText;
    private TextInputLayout mModelText;
    private TextInputLayout mLicenseText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        mMakeText.getEditText().setText(R.string.make);
        mModelText.getEditText().setText(R.string.model);
        mLicenseText.getEditText().setText(R.string.license);


        mUpdateButton.setOnClickListener(new View.OnClickListener() {
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
                repo.update(dto);

                Navigation.findNavController(v).navigate(R.id.action_updateVehicleFragment_to_homeFragment);
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vehicleLicense = mLicenseText.getEditText().getText().toString();

                VehicleRepository repo = new VehicleRepository(getContext());
                repo.delete(vehicleLicense);

                Navigation.findNavController(v).navigate(R.id.action_updateVehicleFragment_to_homeFragment);
            }
        });
        return v;
    }
}
