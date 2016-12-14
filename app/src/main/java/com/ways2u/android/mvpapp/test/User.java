package com.ways2u.android.mvpapp.test;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.ways2u.android.mvpapp.BR;

import javax.inject.Inject;
import javax.inject.Singleton;

//@Singleton
public class User extends BaseObservable {


    private String name="onelong";
    //@Inject
    public User(){

    }


    @Bindable
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
