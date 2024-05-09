package com.example.exam_quiz_androidapp_firebase.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.exam_quiz_androidapp_firebase.data.repo.AuthRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainPageViewModel extends ViewModel {
    public AuthRepository authRepository;
    private MutableLiveData<Boolean> loginResult = new MutableLiveData<>();


    @Inject
    public MainPageViewModel (AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    public void login(String user_email, String user_password){
        authRepository.login(user_email,user_password);
    }
    public LiveData<Boolean> getLoginResult() {
        return loginResult;
    }

}
