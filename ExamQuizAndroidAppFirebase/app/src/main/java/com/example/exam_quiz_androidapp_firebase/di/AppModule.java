package com.example.exam_quiz_androidapp_firebase.di;
import android.app.Application;

import com.example.exam_quiz_androidapp_firebase.data.repo.AuthRepository;

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
}
