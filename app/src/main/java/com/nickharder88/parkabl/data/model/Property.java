package com.nickharder88.parkabl.data.model;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nickharder88.parkabl.data.dto.AddressDTO;
import com.nickharder88.parkabl.data.dto.LandlordDTO;
import com.nickharder88.parkabl.data.dto.PropertyDTO;

public class Property {
    public static String collection = "properties";

    public String id;
    public PropertyDTO data;

    public Property(String id, PropertyDTO data) {
        this.id = id;
        this.data = data;
    }

    public Task<Address> getAddress() {
        if (data.address == null) {
            return null;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection(Address.collection).document(data.address).get().continueWith(new Continuation<DocumentSnapshot, Address>() {
            @Override
            public Address then(@NonNull Task<DocumentSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    if (task.getException() != null) {
                        throw task.getException();
                    }

                    throw new Exception("Could not get Address");
                }

                DocumentSnapshot snap = task.getResult();
                if (snap == null) {
                    return null;
                }

                return new Address(snap.getId(), snap.toObject(AddressDTO.class));
            }
        });
    }

    public Task<Landlord> getLandlord() {
        if (data.landlord == null) {
            return null;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection(Landlord.collection).document(data.landlord).get().continueWith(new Continuation<DocumentSnapshot, Landlord>() {
            @Override
            public Landlord then(@NonNull Task<DocumentSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    if (task.getException() != null) {
                        throw task.getException();
                    }

                    throw new Exception("Could not get Landlord");
                }

                DocumentSnapshot snap = task.getResult();
                if (snap == null) {
                    return null;
                }

                return new Landlord(snap.getId(), snap.toObject(LandlordDTO.class));
            }
        });
    }
}
