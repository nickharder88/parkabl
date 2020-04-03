package com.nickharder88.parkabl.ui.main.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.nickharder88.parkabl.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class ScanFragment extends Fragment {

    private final String TAG = ScanFragment.class.getSimpleName();
    private final int REQUEST_CODE_CAMERA = 10323;

    private static final int REQUEST_PHOTO = 1;
    private static final int REQUEST_VERIFICATION = 0;

    private Uri currentPhotoUri;
    private FloatingActionButton mScanButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scan, container, false);
        mScanButton = v.findViewById(R.id.scan_button);
        mScanButton.setEnabled(false);
        initializeScan();
        return v;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dangerouslyInitializeScan();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final FragmentManager manager = getParentFragmentManager();
        if (requestCode == REQUEST_PHOTO && resultCode == RESULT_OK) {
            FirebaseVisionImage image = null;
            try {
                image = FirebaseVisionImage.fromFilePath(getContext(), currentPhotoUri);
            } catch (IOException e) {
                Log.i(TAG, "Error Loading Image with Firebase");
            }

            FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
            detector.processImage(image)
                            .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                                @Override
                                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                    String allText = firebaseVisionText.getText();
                                    Log.i(TAG, allText);
                                    LicenseVerificationFragment dialog = LicenseVerificationFragment.newInstance(allText);
                                    dialog.setTargetFragment(ScanFragment.this, REQUEST_VERIFICATION);
                                    dialog.show(manager, TAG);
                                }
                            })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.i(TAG, "Image Firebase Failed");
                                        }
                                    });
        } else if (requestCode == REQUEST_VERIFICATION && resultCode == RESULT_OK) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
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
                                    if (document.getData().size() == 0) {
                                        // Failure
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


        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    private void dangerouslyInitializeScan() {
        final Intent intentCaptureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCaptureImage.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (intentCaptureImage.resolveActivity(getContext().getPackageManager()) != null) {
            mScanButton.setEnabled(true);

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.i(TAG, "Error creating the File");
            }

            if (photoFile != null) {
                currentPhotoUri = FileProvider.getUriForFile(getContext(), "com.nickharder88.parkabl.fileprovider", photoFile);
                intentCaptureImage.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri);

                mScanButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        startActivityForResult(intentCaptureImage, REQUEST_PHOTO);
                    }
                });
            }
        }
    }


    private void initializeScan() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            dangerouslyInitializeScan();
        } else {
            requestPermissions(new String[]{ Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
        }
    }
}
