package com.nickharder88.parkabl.data;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nickharder88.parkabl.data.model.Vehicle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleRepository {

    private final String TAG = "VehicleDatabase";

    private FirebaseFirestore db;
    private Context mContext;
    private List<Vehicle> mVehicles;
    private boolean mGotVehicles;

    public VehicleRepository(Context context) {
        db = FirebaseFirestore.getInstance();
        mContext = context;
        mGotVehicles = false;
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
                        makeToast("Vehicle successfully added!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        makeToast("Vehicle not added - try again.");
                    }
                });
    }

    public List<Vehicle> getVehicles() {
        db.collection("vehicles").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            mVehicles = task.getResult().toObjects(Vehicle.class);
                            mGotVehicles = true;
                            Log.d(TAG, mVehicles.toString());
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            makeToast("Error getting list of vehicles.");
                        }
                    }
                });

        return mVehicles;
    }

    public List<Vehicle> getVehiclesAfterCompletion() {
        while(!mGotVehicles) {};
        return mVehicles;
    }

    public void updateVehicle(Vehicle vehicle) {

    }

    public void deleteVehicle(Vehicle vehicle) {

    }

    private void makeToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }


}
