package com.nickharder88.parkabl.ui.main.property;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nickharder88.parkabl.data.dto.PropertyDTO;
import com.nickharder88.parkabl.data.model.Property;

public class PropertyViewModel extends ViewModel {

    private String TAG = "PropertyViewModel";

    private MutableLiveData<Property> property = new MutableLiveData<>();

    public LiveData<Property> getProperty() {
        return property;
    }

    public PropertyViewModel(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Property.collection).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(TAG, "Could not get Property");
                    return;
                }

                DocumentSnapshot snap = task.getResult();
                property.setValue(new Property(snap.getId(), snap.toObject(PropertyDTO.class)));
            }
        });
    }
}
