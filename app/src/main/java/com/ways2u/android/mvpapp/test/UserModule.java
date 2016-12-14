package com.ways2u.android.mvpapp.test;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {  
    @Provides
    @Singleton
    User providesUser() {  
        return new User();  
    }  
}  