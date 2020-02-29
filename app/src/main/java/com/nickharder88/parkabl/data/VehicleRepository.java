package com.nickharder88.parkabl.data;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nickharder88.parkabl.data.model.Vehicle;

import java.util.HashMap;
import java.util.Map;

public class VehicleRepository {

    private final String TAG = "VehicleDatabase";

    private FirebaseFirestore db;
    private Context mContext;

    public VehicleRepository(Context context) {
        db = FirebaseFirestore.getInstance();
        mContext = context;
    }

    public void addVehicle(Vehicle vehicle) {
        Map<String, Object> data = new HashMap<>();
        data.put("make", vehicle.getMake());
        data.put("model", vehicle.getModel());
        data.put("license", vehicle.getLicense());

        db.collection("vehicles")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        Toast.makeText(mContext, "Vehicle successfully added!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(mContext, "Vehicle not added - try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getVehicle(Vehicle vehicle) {

    }

    public void updateVehicle(Vehicle vehicle) {

    }

    public void deleteVehicle(Vehicle vehicle) {

    }


}
