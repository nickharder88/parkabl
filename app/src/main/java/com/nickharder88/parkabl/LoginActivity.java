package com.nickharder88.parkabl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "TAG";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // get references to widgets
        final Button loginButton = findViewById(R.id.loginButton);
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.username);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, username.getText().toString());
                Log.i(TAG, password.getText().toString());
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
    }


}
