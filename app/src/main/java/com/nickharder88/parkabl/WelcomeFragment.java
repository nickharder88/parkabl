package com.nickharder88.parkabl;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.widget.Button;

public class WelcomeFragment extends Fragment {


    private final String TAG = "WelcomeFragment";

    private Button mLoginButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "Fragment in onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Fragment in onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_welcome, container, false);
        mLoginButton = v.findViewById(R.id.login_button);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // fire off intent for starting LoginActivity
            }
        });

        Log.i(TAG, "Fragment in onCreateView");
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "Fragment in onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "Fragment in onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "Fragment in onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "Fragment in onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "Fragment in onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "Fragment in onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Fragment in onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "Fragment in onDetach");
    }

}
