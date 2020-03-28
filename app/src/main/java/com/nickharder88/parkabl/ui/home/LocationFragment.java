package com.nickharder88.parkabl.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.nickharder88.parkabl.R;

public class LocationFragment extends Fragment {

    private static int REQUEST_CODE_LOCATION = 1;
    private static int UPDATE_INTERVAL = 2000;
    private static int UPDATE_INTERVAL_FASTEST = UPDATE_INTERVAL / 2;

    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    private FusedLocationProviderClient fusedLocationClient;
    private TextView textLatitude;
    private TextView textLongitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_location, container, false);
        this.textLatitude = v.findViewById(R.id.text_latitude);
        this.textLongitude = v.findViewById(R.id.text_longitude);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                setLatitude(location);
                setLongitude(location);
            }
        };

        // initial request and check for permissions
        getLastLocation();

        // set up location polling
        createLocationRequest();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.requestLocationUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.removeLocationUpdates();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    private void createLocationRequest() {
        LocationRequest request = new LocationRequest();
        request.setInterval(UPDATE_INTERVAL);
        request.setFastestInterval(UPDATE_INTERVAL_FASTEST);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest = request;
    }

    private void requestLocationUpdates() {
        this.fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private void removeLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            this.dangerouslyGetLastLocation();
        } else {
            requestPermissions(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
        }
    }

    private void dangerouslyGetLastLocation() {
        this.fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    if (location != null) {
                        setLatitude(location);
                        setLongitude(location);
                    }
                } else {
                    Log.w("LOCATION", task.getException());
                }
            }
        });
    }

    private void setLatitude(Location location) {
        textLatitude.setText("Latitude " + String.valueOf(location.getLatitude()));
    }

    private void setLongitude(Location location) {
        textLongitude.setText("Longitude " + String.valueOf(location.getLongitude()));
    }
}
