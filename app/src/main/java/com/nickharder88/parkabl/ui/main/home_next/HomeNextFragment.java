package com.nickharder88.parkabl.ui.main.home_next;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import com.github.davidmoten.geo.GeoHash;
import com.github.davidmoten.geo.LatLong;
import com.google.android.gms.location.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.nickharder88.parkabl.R;
import com.nickharder88.parkabl.data.model.Address;
import com.nickharder88.parkabl.data.model.Property;

import java.util.HashMap;
import java.util.Map;

public class HomeNextFragment extends Fragment implements OnMapReadyCallback, OnMarkerClickListener {

    private static final String TAG = HomeNextFragment.class.getSimpleName();
    private static final int REQUEST_CODE_LOCATION = 1;
    private static final int UPDATE_INTERVAL = 2000;
    private static final int UPDATE_INTERVAL_FASTEST = UPDATE_INTERVAL / 2;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;

    // Google Map
    private GoogleMap gMap;
    // PropertyId, Marker
    private HashMap<String, Marker> markers = new HashMap<>();

    /* Property Card */
    private FrameLayout frameLayoutPropertyWrapper;
    private TextView textViewPropertyId;
    private TextView textViewAddress;
    private MaterialButton buttonViewProperty;
    private Button buttonCloseViewProperty;

    private final HomeNextViewModel viewModel = new HomeNextViewModel();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        requestLocationUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();
        removeLocationUpdates();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home_next, container, false);

        frameLayoutPropertyWrapper = view.findViewById(R.id.property_card_wrapper);
        textViewPropertyId = view.findViewById(R.id.text_property_id);
        textViewAddress = view.findViewById(R.id.text_address);
        buttonViewProperty = view.findViewById(R.id.button_view_property);
        buttonCloseViewProperty = view.findViewById(R.id.button_close_view_property);

        // Add Button Listeners
        buttonCloseViewProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPropertyCard(null, null);
            }
        });

        // Hide Property Card
        setPropertyCard(null, null);


        // Create Location Request
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(UPDATE_INTERVAL_FASTEST);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);
        return view;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        gMap = googleMap;

        try {
            boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.styled_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error", e);
        }

        // Initialize Map
        gMap.setMinZoomPreference(15);
        gMap.setMaxZoomPreference(20);
        gMap.setMyLocationEnabled(true);

        // Create Callback
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    onNewLocation(location);
                }
            }
        };

        getLastLocation();

        // Listen For Properties Nearby
        viewModel.getProperties().observe(this, new Observer<Map<String, Property>>() {
            @Override
            public void onChanged(Map<String, Property> properties) {
                for (final Property property : properties.values()) {
                    // Ignore Markers that We've Already Included
                    if (markers.containsKey(property.id)) {
                        continue;
                    }

                    // Get The Address of the Property
                    property.getAddress().addOnCompleteListener(new OnCompleteListener<Address>() {
                        @Override
                        public void onComplete(@NonNull Task<Address> task) {
                            if (task.isSuccessful()) {
                                Address address = task.getResult();
                                if (address != null && address.data.geohash != null) {
                                    LatLong latLong = GeoHash.decodeHash(address.data.geohash);
                                    Marker marker = gMap.addMarker(new MarkerOptions().position(new LatLng(latLong.getLat(), latLong.getLon())));
                                    marker.setTag(property.id);
                                    markers.put(property.id, marker);

                                    // TODO Remove Markers Not in HashMap
                                    // marker.remove();
                                    // markers.remove(id);
                                }
                            }
                        }
                    });
                }
            }
        });

        // Listen for Marker Clicks
        gMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String propertyId = (String) marker.getTag();
        Map<String, Property> properties = viewModel.getProperties().getValue();
        Map<String, Address> addresses = viewModel.getAddresses().getValue();
        if (properties == null || addresses == null) {
            throw new Error("Expected Properties and Addresses to be defined");
        }

        Property property = properties.get(propertyId);
        if (property == null) {
            throw new Error("Expected Property to be defined");
        }

        Address address = addresses.get(property.data.address);
        setPropertyCard(property, address);
        return false;
    }

    private void setPropertyCard(final Property property, Address address) {
        if (frameLayoutPropertyWrapper == null) {
            return;
        }

        if (property == null) {
            buttonViewProperty.setOnClickListener(null);
            frameLayoutPropertyWrapper.setVisibility(View.INVISIBLE);
        } else {
            textViewPropertyId.setText(property.id);
            textViewAddress.setText(address.toString());
            frameLayoutPropertyWrapper.setVisibility(View.VISIBLE);

            buttonViewProperty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("propertyId", property.id);
                    Navigation.findNavController(v).navigate(R.id.action_homeNextFragment_to_propertyFragment, bundle);
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.dangerouslyGetLastLocation();
            }
        }
    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            this.dangerouslyGetLastLocation();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
        }
    }

    private void dangerouslyGetLastLocation() {
        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    if (location != null) {
                        onNewLocation(location);
                    }
                }
            }
        });
    }

    private void requestLocationUpdates() {
        if (mFusedLocationProviderClient != null && mLocationRequest != null && mLocationCallback != null) {
            mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
    }

    private void removeLocationUpdates() {
        if (mFusedLocationProviderClient != null && mLocationCallback != null) {
            mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
        }
    }

    private void onNewLocation(Location location) {
        if (gMap != null) {
            gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
            viewModel.newLocation(location);
        }
    }
}
