package com.example.exam_quiz_androidapp_firebase.data.repo;

import android.app.Application;
import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthRepository {
    private FirebaseAuth auth;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<Boolean> signUpStatusLiveData;
    private MutableLiveData<String> errorMessageLiveData;

    public AuthRepository(Context context) {
        auth = FirebaseAuth.getInstance();
        firebaseUserMutableLiveData = new MutableLiveData<>();
        signUpStatusLiveData = new MutableLiveData<>();
        errorMessageLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getSignUpStatusLiveData() {
        return signUpStatusLiveData;
    }

    public MutableLiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }

    public void register(String firstName, String lastName, String email, String password, String confirmPassword) {
        if (firstName.isEmpty() || lastName.isEmpty()) {
            errorMessageLiveData.postValue("First and last name cannot be empty!");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessageLiveData.postValue("Please write your e-mail address correctly.");
            return;
        }
        if (!password.equals(confirmPassword)) {
            errorMessageLiveData.postValue("The passwords don't match. Please check again.");
            return;
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                signUpStatusLiveData.postValue(true);
            } else {
                errorMessageLiveData.postValue("Registration failed: " + task.getException().getMessage());
            }
        });
    }

    public void login(String email, String password) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessageLiveData.postValue("Please write your e-mail address correctly.");
            return;
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
            } else {
                errorMessageLiveData.postValue("Login failed: " + task.getException().getMessage());
            }
        });
    }
}
