package com.example.exam_quiz_androidapp_firebase.data.repo;

import android.app.Application;
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
    private Application application;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private FirebaseAuth auth;

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public AuthRepository(Application application){
        this.application = application;
        firebaseUserMutableLiveData = new MutableLiveData<>();
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null){
            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
        }
    }

    public void register(String user_firstName, String user_lastName, String user_email, String user_password, String user_confirmPassword) {
        if (user_firstName.isEmpty() || user_lastName.isEmpty()) {
            Toast.makeText(application, "First and last name cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user_email).matches()) {
            Toast.makeText(application, "Please write your e-mail address correctly.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!user_password.equals(user_confirmPassword)) {
            Toast.makeText(application, "The passwords don't match. Please check again..", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    firebaseUserMutableLiveData.postValue(auth.getCurrentUser());




                } else {
                    Toast.makeText(application, "Kayıt başarısız: ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void login(String user_email, String user_password) {

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user_email).matches()) {
            Toast.makeText(application, "Please write your e-mail address correctly.", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.signInWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                    Toast.makeText(application, "Login successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(application, "Login unsuccessful! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
