package com.nickharder88.parkabl.ui.login;

import android.util.Log;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

// Material
import com.google.android.material.textfield.TextInputLayout;

import com.nickharder88.parkabl.ui.home.HomeActivity;
import com.nickharder88.parkabl.R;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final TextInputLayout emailInputLayout = findViewById(R.id.email);
        final TextInputLayout passwordInputLayout = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);

        // initialize to disabled
        loginButton.setEnabled(false);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getEmailError() != null) {
                    emailInputLayout.setError(getString(loginFormState.getEmailError()));
                } else {
                    emailInputLayout.setError(null);
                }

                if (loginFormState.getPasswordError() != null) {
                    passwordInputLayout.setError(getString(loginFormState.getPasswordError()));
                } else {
                    passwordInputLayout.setError(null);
                }
            }
        });

        final Intent intent = new Intent(this, HomeActivity.class);
        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    // Complete and destroy login activity once successful
                    finish();
                    startActivity(intent);
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(emailInputLayout.getEditText().getText().toString(),
                        passwordInputLayout.getEditText().getText().toString());
            }
        };

        emailInputLayout.getEditText().addTextChangedListener(afterTextChangedListener);
        passwordInputLayout.getEditText().addTextChangedListener(afterTextChangedListener);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.login(emailInputLayout.getEditText().getText().toString(),
                        passwordInputLayout.getEditText().getText().toString());
            }
        });
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
