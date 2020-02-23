package com.nickharder88.parkabl;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class DoubleFragmentActivity extends AppCompatActivity {

    private final String TAG = "DoubleFragmentActivity";

    protected abstract Fragment createFragmentOne();
    protected abstract Fragment createFragmentTwo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_fragments);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentOne = fm.findFragmentById(R.id.fragment_one_container);
        Fragment fragmentTwo = fm.findFragmentById(R.id.fragment_two_container);

        if (fragmentOne == null) {
            fragmentOne = createFragmentOne();
            fm.beginTransaction().add(R.id.fragment_one_container, fragmentOne).commit();
        }

        if (fragmentTwo == null) {
            fragmentTwo = createFragmentTwo();
            fm.beginTransaction().add(R.id.fragment_two_container, fragmentTwo).commit();
        }

        Log.i(TAG, "Activity in onCreate");

    }
}
