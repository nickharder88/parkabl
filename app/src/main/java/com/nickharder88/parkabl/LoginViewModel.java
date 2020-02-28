package com.nickharder88.parkabl;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends ViewModel {
    private FirebaseAuth mAuth;
    private String username = "";
    private String password = "";

    public LoginViewModel(SavedStateHandle savedStateHandle) {
        username = savedStateHandle["username"];
    }
}
