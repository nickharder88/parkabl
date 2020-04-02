package com.nickharder88.parkabl.ui.main.home_next;

import android.location.Location;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.github.davidmoten.geo.GeoHash;
import com.github.davidmoten.geo.LatLong;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nickharder88.parkabl.data.dto.AddressDTO;
import com.nickharder88.parkabl.data.dto.PropertyDTO;
import com.nickharder88.parkabl.data.model.Address;
import com.nickharder88.parkabl.data.model.Property;

import java.util.*;

public class HomeNextViewModel extends ViewModel {

    private MutableLiveData<Map<String, Address>> addresses = new MutableLiveData<>();
    private MutableLiveData<Map<String, Property>> properties = new MutableLiveData<>();

    public LiveData<Map<String, Property>> getProperties() {
        return properties;
    }
    public LiveData<Map<String, Address>> getAddresses() { return addresses; }


    public void newLocation(Location location) {
        // TODO Check if sufficiently changed location

        double latPerMile = 0.0144927536231884;
        double lngPerMile = 0.0181818181818182;
        double miles = 0.5;

        final String lower = GeoHash.encodeHash(new LatLong(location.getLatitude() - latPerMile * miles, location.getLongitude() - lngPerMile * miles));
        final String upper = GeoHash.encodeHash(new LatLong(location.getLatitude() + latPerMile * miles, location.getLongitude() + lngPerMile * miles));

        // TODO: Slow Implementation
        final FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        final HashMap<String, Property> mHashProperty = new HashMap<String, Property>();

        // Get All Properties
        mFirestore.collection(Property.collection).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    final QuerySnapshot snapProperties = task.getResult();
                    if (snapProperties != null) {
                        // Fill Store of Properties referenced by Address
                        for (DocumentSnapshot snap : snapProperties.getDocuments()) {
                            String address = (String) snap.get("address");
                            if (address != null) {
                                mHashProperty.put(address, new Property(snap.getId(), snap.toObject(PropertyDTO.class)));
                            }
                        }

                        // Get All Addresses Close to Location
                        mFirestore
                                .collection(Address.collection)
                                .whereGreaterThanOrEqualTo("geohash", lower)
                                .whereLessThanOrEqualTo("geohash", upper)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            QuerySnapshot snapAddresses = task.getResult();
                                            if (snapAddresses != null) {

                                                Map<String, Property> mProperties = new HashMap<>();
                                                Map<String, Address> mAddresses = new HashMap<>();

                                                for (DocumentSnapshot snap: snapAddresses.getDocuments()) {
                                                    Property property = mHashProperty.get(snap.getId());
                                                    if (property != null) {
                                                        mProperties.put(property.id, property);
                                                        mAddresses.put(snap.getId(), new Address(snap.getId(), snap.toObject(AddressDTO.class)));
                                                    }
                                                }

                                                // Set Live Data
                                                properties.setValue(mProperties);
                                                addresses.setValue(mAddresses);
                                            }
                                        }
                                    }
                                });
                    }
                } else {
                    Log.w(HomeNextViewModel.class.getSimpleName(), "Could not get properties");
                }
            }
        });
    }
}
