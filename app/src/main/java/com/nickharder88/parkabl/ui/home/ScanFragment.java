package com.nickharder88.parkabl.ui.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nickharder88.parkabl.R;

import static android.app.Activity.RESULT_OK;

public class ScanFragment extends Fragment {

    private final String TAG = "ScanFragment";

    private static final int REQUEST_PHOTO = 1;

    private Bitmap licenseToScan;

    private FloatingActionButton mScanButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scan, container, false);
        mScanButton = v.findViewById(R.id.scan_button);

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager packageManager = getActivity().getPackageManager();

        boolean canTakePhoto = captureImage.resolveActivity(packageManager) != null;
        mScanButton.setEnabled(canTakePhoto);

        mScanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        Log.i(TAG, "Fragment in onCreateView");
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            licenseToScan = (Bitmap) extras.get("data");
        }
    }
}
