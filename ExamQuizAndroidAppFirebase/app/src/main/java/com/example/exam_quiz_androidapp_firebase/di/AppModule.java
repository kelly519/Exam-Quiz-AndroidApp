package com.example.exam_quiz_androidapp_firebase.di;
import android.app.Application;

import com.example.exam_quiz_androidapp_firebase.data.repo.AuthRepository;
import com.example.exam_quiz_androidapp_firebase.data.repo.QuizFirebaseRepository;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Singleton
    @Provides
    public static AuthRepository provideAuthRepository(Application application) {
        return new AuthRepository(application);
    }
    @Singleton
    @Provides
    public static FirebaseFirestore provideFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }

    @Singleton
    @Provides
    public static CollectionReference provideCollectionReference(FirebaseFirestore firebaseFirestore) {
        return firebaseFirestore.collection("QuizList"); // "quizzes" koleksiyon adı, gereksinimlerinize göre değiştirebilirsiniz.
    }

    @Singleton
    @Provides
    public static QuizFirebaseRepository provideQuizFirebaseRepository(CollectionReference collectionReference) {
        return new QuizFirebaseRepository(collectionReference.getFirestore());
    }

}
