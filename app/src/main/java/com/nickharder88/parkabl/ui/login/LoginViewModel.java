package com.nickharder88.parkabl.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.util.Patterns;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.nickharder88.parkabl.data.LoginRepository;
import com.nickharder88.parkabl.data.model.LoggedInUser;
import com.nickharder88.parkabl.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        Task<LoggedInUser> result = loginRepository.login(username, password);

        result.addOnSuccessListener(new OnSuccessListener<LoggedInUser>() {
            @Override
            public void onSuccess(LoggedInUser loggedInUser) {
                // fire off intent for starting LoginActivity
                loginResult.setValue(new LoginResult(new LoggedInUserView(loggedInUser.getDisplayName())));
            }
        });

        result.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loginResult.setValue(new LoginResult(R.string.login_failed));
            }
        });
    }

    public void loginDataChanged(String username, String password) {
        if (isEmailValid(username)) {
            if (!password.isEmpty()) {
                loginFormState.setValue(new LoginFormState(true));
            } else {
                loginFormState.setValue(new LoginFormState(null, null));
            }
        } else {
            loginFormState.setValue(new LoginFormState(R.string.invalid_email, null));
        }
    }

    // A placeholder username validation check
    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
