package com.nickharder88.parkabl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.widget.Button;

public class WelcomeFragment extends Fragment {

    private Button mLoginButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        return v;
    }
}
