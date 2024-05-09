package com.example.exam_quiz_androidapp_firebase.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.exam_quiz_androidapp_firebase.data.repo.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SignUpViewModel extends ViewModel {
    public AuthRepository authRepository;
    private MutableLiveData<FirebaseUser> userData = new MutableLiveData<>();


    @Inject
    public SignUpViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void register(String user_firstName, String user_lastName, String user_email, String user_password, String user_confirmPassword) {
        authRepository.register(user_firstName, user_lastName, user_email, user_password, user_confirmPassword);
    }
    public LiveData<FirebaseUser> getUserData() {
        return userData;
    }
}
