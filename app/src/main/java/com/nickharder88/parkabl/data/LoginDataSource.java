package com.nickharder88.parkabl.data;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nickharder88.parkabl.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Task<LoggedInUser> login(String email, String password) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        return mAuth.signInWithEmailAndPassword(email, password).continueWith(new Continuation<AuthResult, LoggedInUser>() {
            @Override
            public LoggedInUser then(@NonNull Task<AuthResult> task) throws Exception {
                FirebaseUser fbaseUser = mAuth.getCurrentUser();
                if (task.isSuccessful() && fbaseUser != null) {
                    return new LoggedInUser(fbaseUser.getUid(), fbaseUser.getEmail());
                } else {
                    throw new IOException();
                }
            }
        });
    }

    public void logout() {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }
}
