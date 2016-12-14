package com.ways2u.android.mvpapp.test;

import com.ways2u.android.mvpapp.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = UserModule.class)
public interface ActivityComponent {  
    void injects(MainActivity activity);
}