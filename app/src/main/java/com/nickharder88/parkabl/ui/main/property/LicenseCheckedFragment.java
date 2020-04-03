package com.nickharder88.parkabl.ui.main.property;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.nickharder88.parkabl.R;

import java.util.Objects;

public class LicenseCheckedFragment extends DialogFragment {

    private final static  String ARG_PARKED = "parked";

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        boolean parked_legally = getArguments().getBoolean(ARG_PARKED);

        int layoutToInflate = R.layout.bad_license_check;
        if (parked_legally) {
            layoutToInflate = R.layout.good_license_check;
        }

        View v = LayoutInflater.from(getActivity()).inflate(layoutToInflate, null);

        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(v)
                .setTitle(R.string.license_check_title)
                .setPositiveButton(android.R.string.ok, null).create();
    }

    public static LicenseCheckedFragment newInstance(boolean parkedLegally) {
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARKED, parkedLegally);

        LicenseCheckedFragment fragment = new LicenseCheckedFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
