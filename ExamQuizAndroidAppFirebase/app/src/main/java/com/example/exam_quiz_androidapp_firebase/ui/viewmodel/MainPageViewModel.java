package com.example.exam_quiz_androidapp_firebase.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.exam_quiz_androidapp_firebase.data.repo.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainPageViewModel extends ViewModel {
    public AuthRepository authRepository;
    private MutableLiveData<Boolean> loginResult = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData = new MutableLiveData<>();

    @Inject
    public MainPageViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
        authRepository.getFirebaseUserMutableLiveData().observeForever(firebaseUserMutableLiveData::postValue);
    }

    public void login(String user_email, String user_password) {
        authRepository.login(user_email, user_password);
        authRepository.getErrorMessageLiveData().observeForever(errorMessage::postValue);
    }

    public LiveData<Boolean> getLoginResult() {
        return loginResult;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }
}
