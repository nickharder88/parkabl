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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import com.google.android.gms.location.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.nickharder88.parkabl.R;

public class HomeNextFragment extends Fragment implements OnMapReadyCallback {

    private static int REQUEST_CODE_LOCATION = 1;
    private static int UPDATE_INTERVAL = 2000;
    private static int UPDATE_INTERVAL_FASTEST = UPDATE_INTERVAL / 2;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private GoogleMap gMap;

    private static final String TAG = HomeNextFragment.class.getSimpleName();

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home_next, container, false);

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
                    gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                }
            }
        };

        getLastLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            this.dangerouslyGetLastLocation();
        } else {
            requestPermissions(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
        }
    }

    private void dangerouslyGetLastLocation() {
        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    if (location != null) {
                        gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
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
}
