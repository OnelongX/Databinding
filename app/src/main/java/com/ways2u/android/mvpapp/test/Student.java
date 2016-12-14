package com.ways2u.android.mvpapp.test;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

/**
 * Created by huanglong on 2016/12/7.
 */


public class Student {
    public final ObservableField<String> field = new ObservableField<>();
    public final ObservableInt age = new ObservableInt();
    public Student(){

    }
}
