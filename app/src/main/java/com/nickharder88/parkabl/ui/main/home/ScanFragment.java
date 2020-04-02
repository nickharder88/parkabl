package com.nickharder88.parkabl.ui.main.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nickharder88.parkabl.R;

import static android.app.Activity.RESULT_OK;

public class ScanFragment extends Fragment {

    private final String TAG = "ScanFragment";

    private static final int REQUEST_PHOTO = 1;

    private Bitmap licenseToScan;

    private FloatingActionButton mScanButton;

    private FirebaseFirestore db;

    private static final int REQUEST_VERIFICATION = 0;

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

        db = FirebaseFirestore.getInstance();

        Log.i(TAG, "Fragment in onCreateView");
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            licenseToScan = (Bitmap) extras.get("data");

//            FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(licenseToScan);
//
//            FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

//            Task<FirebaseVisionText> result =
//                    detector.processImage(image)
//                            .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
//                                @Override
//                                public void onSuccess(FirebaseVisionText firebaseVisionText) {
//                                    String allText = firebaseVisionText.getText();

            String allText = "xxxxxxx";

            FragmentManager manager = getFragmentManager();
            LicenseVerificationFragment dialog = LicenseVerificationFragment.newInstance(allText);
            dialog.setTargetFragment(ScanFragment.this, REQUEST_VERIFICATION);
            dialog.show(manager, TAG);

        } else if (requestCode == REQUEST_VERIFICATION && resultCode == RESULT_OK) {

            String allText = (String) data.getSerializableExtra(LicenseVerificationFragment.EXTRA_PLATE);


            db.collection("vehicles")
                    .whereEqualTo("license", allText)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    FragmentManager manager = getFragmentManager();
                                    if (document.getData().size() == 0){
                                        // failure
                                        LicenseCheckedFragment dialog = LicenseCheckedFragment.newInstance(false);
                                        dialog.show(manager, TAG);
                                    } else {
                                        // Success
                                        LicenseCheckedFragment dialog = LicenseCheckedFragment.newInstance(true);
                                        dialog.show(manager, TAG);
                                    }
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
//                                }
//                            })
//                            .addOnFailureListener(
//                                    new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            // Task failed with an exception
//                                            // ...
//                                        }
//                                    });

        }
    }
}
