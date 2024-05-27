package com.example.exam_quiz_androidapp_firebase.ui.viewmodel.quiz;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.exam_quiz_androidapp_firebase.data.model.QuizModel;
import com.example.exam_quiz_androidapp_firebase.data.repo.QuizFirebaseRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class QuizViewModel extends ViewModel implements QuizFirebaseRepository.OnFireStoreDataAdded {
    private final MutableLiveData<List<QuizModel>> quizModelListData = new MutableLiveData<>();
    private final QuizFirebaseRepository quizFirebaseRepository;

    @Inject
    public QuizViewModel(QuizFirebaseRepository quizFirebaseRepository){
        this.quizFirebaseRepository = quizFirebaseRepository;
        this.quizFirebaseRepository.setOnFireStoreDataAdded(this);
        this.quizFirebaseRepository.getDataFromFireStore();
    }

    public LiveData<List<QuizModel>> getLiveDatafromFireStore() {
        return quizModelListData;
    }

    @Override
    public void quizDataAdded(List<QuizModel> quizModelList) {
        quizModelListData.setValue(quizModelList);
    }

    public void OnError(Exception e) {
        Log.e("QuizViewModelError", "There is a mistake about : " + e.getMessage());
    }
}
