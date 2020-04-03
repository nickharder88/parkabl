package com.nickharder88.parkabl.ui.main.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.nickharder88.parkabl.R;

public class LicenseVerificationFragment extends DialogFragment {

    private static final String ARG_PLATE = "plate_number";
    public static final String EXTRA_PLATE = "com.nickharder88.parkabl.plate";

    private TextInputLayout mPlate;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String plate = (String) getArguments().getSerializable(ARG_PLATE);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.license_number_verification, null);

        mPlate = v.findViewById(R.id.plate_number);
        mPlate.getEditText().setText(plate);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.plate_verification_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String plate = mPlate.getEditText().getText().toString();
                        sendResult(Activity.RESULT_OK, plate);
                    }
                })
                .create();
    }

    public static LicenseVerificationFragment newInstance(String plateNumber) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLATE, plateNumber);

        LicenseVerificationFragment fragment = new LicenseVerificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode, String plate) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_PLATE, plate);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
