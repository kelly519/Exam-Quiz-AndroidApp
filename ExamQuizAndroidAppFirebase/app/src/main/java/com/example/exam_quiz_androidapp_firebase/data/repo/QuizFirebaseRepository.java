package com.example.exam_quiz_androidapp_firebase.data.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.exam_quiz_androidapp_firebase.data.model.QuizModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class QuizFirebaseRepository {
    private CollectionReference collectionQuiz;
    private OnFireStoreDataAdded fireStoreDataAdded;

    public QuizFirebaseRepository(CollectionReference collectionQuiz, OnFireStoreDataAdded fireStoreDataAdded){
        this.collectionQuiz = collectionQuiz;
        this.fireStoreDataAdded= fireStoreDataAdded;
    }
    public void getDataFromFireStore(){
        collectionQuiz.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    fireStoreDataAdded.quizDataAdded(task.getResult().toObjects(QuizModel.class));
                }
                else {
                    fireStoreDataAdded.OnError(task.getException());
                }
            }
        });
    }
    public interface OnFireStoreDataAdded{
        void quizDataAdded(List<QuizModel> quizModelList);
        void OnError(Exception exception);
    }

}
