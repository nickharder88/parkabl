package com.nickharder88.parkabl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.widget.Button;
import android.widget.TextView;

import com.nickharder88.parkabl.data.LoginDataSource;
import com.nickharder88.parkabl.data.LoginRepository;
import com.nickharder88.parkabl.data.model.LoggedInUser;
import com.nickharder88.parkabl.ui.login.LoginActivity;

public class WelcomeFragment extends Fragment {


    private final String TAG = "WelcomeFragment";

    private Button mLoginButton;

    private TextView mLoggedInTextView;

    private LoginRepository loginRepository;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "Fragment in onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.loginRepository = LoginRepository.getInstance(new LoginDataSource());
        Log.i(TAG, "Fragment in onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_welcome, container, false);
        mLoginButton = v.findViewById(R.id.login_button);
        mLoggedInTextView = v.findViewById(R.id.logged_in_text_view);

        if (loginRepository.isLoggedIn()) {
            LoggedInUser user = loginRepository.getUser();

            // disable if logged in
            mLoginButton.setEnabled(false);
            mLoginButton.setVisibility(View.GONE);
            mLoggedInTextView.setVisibility(View.VISIBLE);
            mLoggedInTextView.setText(user.getDisplayName());
            Log.i("INFO", user.getDisplayName() == null ? "No Name" : user.getDisplayName());
        } else {
            mLoginButton.setEnabled(true);
            mLoginButton.setVisibility(View.VISIBLE);
            mLoggedInTextView.setVisibility(View.GONE);
            mLoggedInTextView.setText("");
        }

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loginRepository.logout();
                final Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
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
