package com.ways2u.android.mvpapp;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ways2u.android.mvpapp.databinding.ActivityMainBinding;
import com.ways2u.android.mvpapp.test.ActivityComponent;
import com.ways2u.android.mvpapp.test.DaggerActivityComponent;
import com.ways2u.android.mvpapp.test.Student;
import com.ways2u.android.mvpapp.test.User;
import com.ways2u.android.mvpapp.test.UserModule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding vb;

    @Inject
    User user1;
    @Inject
    User user2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerActivityComponent.builder().userModule(new UserModule()).build().injects(this);

        vb = DataBindingUtil.setContentView(this, R.layout.activity_main);

        vb.setUser1(user1);
        vb.setUser2(user2);
        vb.setVariable(BR.presenter, new MainActivityPresenter());
        //vb.setVariable(BR.user1,user1);

        List<String> list = new ArrayList<>();
        list.add("你好");
        vb.setList1(list);

        Map<String,String> map = new HashMap<>();
        map.put("name","onelong");
        map.put("age","18");
        vb.setMap(map);

        vb.setArrays(new String[]{"QQ"});

        final Student student = new Student();
        student.field.set("cat");
        student.age.set(2);
        vb.setStudent(student);

        final ObservableArrayList<String> list2 = new ObservableArrayList<>();
        list2.add("dog");
        list2.add("mouse");
        vb.setList2(list2);

        final ObservableArrayMap<String, String> map2 = new ObservableArrayMap<>();
        map2.put("name","Tom");
        map2.put("age","4");
        vb.setMap2(map2);

        vb.fourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.field.set("dog");
                student.age.set(4);
                list2.set(0,"cat");
                list2.set(1,"dog");
                map2.put("name","Sam");
                map2.put("age","5");

                vb.setImgUrl("hhhh");
            }
        });



        vb.setTime2(new Date());


    }

    @BindingConversion
    public static String convertDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    @BindingAdapter({"bind:image"})
    public static void imageLoader(ImageView imageView, String url) {
        if(url!=null)
            imageView.setImageResource(R.mipmap.ic_launcher);
        Log.e("-----txt------",""+url);
        //ImageLoaderUtils.getInstance().displayImage(url, imageView);
    }

    public class MainActivityPresenter {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //vb.userName.setText(s);
        }

        public void onClick(View view) {
            if (view instanceof ImageView)
                Toast.makeText(MainActivity.this, "点到了ImageView", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, "点到了", Toast.LENGTH_SHORT).show();
        }

        public void onClickListenerBinding(User employee) {
            Toast.makeText(MainActivity.this, employee.getName(),
                    Toast.LENGTH_SHORT).show();
        }
    }


}
