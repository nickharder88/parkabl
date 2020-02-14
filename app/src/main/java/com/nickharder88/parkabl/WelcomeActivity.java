package com.nickharder88.parkabl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class WelcomeActivity extends AppCompatActivity {

    private final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new WelcomeFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

        Log.i(TAG, "Activity in onCreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Activity in onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Activity in onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "Activity in onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "Activity in onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "Activity in onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Activity in onDestroy");
    }
}
