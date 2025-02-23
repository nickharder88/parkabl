package com.nickharder88.parkabl.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nickharder88.parkabl.data.dto.VehicleDTO;
import com.nickharder88.parkabl.data.model.Vehicle;

import java.util.HashMap;
import java.util.Map;

public class VehicleRepository {

    private final String TAG = "VehicleRepository";

    private FirebaseFirestore db;
    private Context mContext;

    public VehicleRepository(Context context) {
        db = FirebaseFirestore.getInstance();
        mContext = context;
    }

    public void add(VehicleDTO vehicle) {
        Map<String, Object> data = new HashMap<>();
        data.put("make", vehicle.make);
        data.put("model", vehicle.model);
        data.put("license", vehicle.licensePlateNum);

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

    public void update(final VehicleDTO vehicle) {
        db.collection("vehicles").whereEqualTo("licensePlateNum", vehicle.licensePlateNum)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Map<String, Object> data = new HashMap<>();
                            data.put("make", vehicle.make);
                            data.put("model", vehicle.model);
                            data.put("license", vehicle.licensePlateNum);

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().set(data)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error writing document", e);
                                                makeToast("Vehicle not updated - try again.");
                                            }
                                        });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            makeToast("Vehicle not updated - try again.");
                        }
                    }
                });

    }

    public void delete(final String licensePlateNum) {
        Log.d(TAG, "Deleting vehicle...");
        db.collection("vehicles").whereEqualTo("licensePlateNum", licensePlateNum)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                document.getReference().delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error deleting document", e);
                                                makeToast("Vehicle not deleted - try again.");
                                            }
                                        });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            makeToast("Vehicle not deleted - try again.");
                        }
                    }
                });

    }

    private void makeToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }


}
