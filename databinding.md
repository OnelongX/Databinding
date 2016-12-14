###DataBinding主要解决了两个问题： 
* 需要多次使用findViewById，损害了应用性能且令人厌烦 
* 更新UI数据需切换至UI线程，将数据分解映射到各个view比较麻烦

###DataBinding基本使用包括以下内容：
* 单纯的摆脱findviewbyid
* 绑定基本数据类型及String
* 绑定Model数据
* 绑定事件
* 通过静态方法转换数据类型
* 通过运算符操作数据
* 自定义Binding的类名
* 绑定相同Model的操作
* model变量改变自动更新数据
* 绑定List/Map等集合数据
* Observable自动更新
* Databinding与include标签的结合
* DataBinding与RecyclerView的结合

###命名规则
* 布局通过DataBindingUtils.setContentView()加载到代码中，而且会生成对应一个Binding对象，对象名是布局文件文称加上Binding后缀
* 通过Binding对象.id名称，就相当于拿到了指定的布局中的id的控件了，使用起来和findviewbyid获取的控件是一样的
* 在布局中是通过@{}来绑定数据的，{}中是布局中该控件属性对应的数据类型数据，同时还可以支持运算符运算和静态方法调用和转换

```java
binding设置数据有2中方式:
1.binding.setUser(user) 
2.binding.setVariable(BR.user,user)–采用BR指
```
```java
1.android:onClick="@{event.click1}"
2.android:onClick="@{event::click2}"
3.android:onClick="@{()->event.cilck3(title4)}"
 [注]：()->event.cilck3(title4)是lambda表达式写法，
 也可以写成：(view)->event.cilck3(title4),前面(view)表示onClick方法的传递的参数，
 如果event.click3()方法中不需要用到view参数，可以将view省略。
 当然event.click1也可以写成(view)->event.click1(view)，将onClick(View view)的view参数传递给event.click1(view)方法。
大概就这意思，以下是伪代码
onclick(View view){
     event.click1(view)
}
```

```xml
<?xml version="1.0" encoding="utf-8"?><!--布局以layout作为根布局-->
<layout>

    <data>
        <!--绑定基本数据类型及String-->
        <!--name:   和java代码中的对象是类似的，名字自定义-->
        <!--type:   和java代码中的类型是一致的-->
        <variable
            name="content"
            type="String" />

        <variable
            name="enabled"
            type="boolean" />
            <!--绑定Model数据2中形式，一中是导入该类型的，一种指定类型的全称，和java一样-->
        <!--  方式一 -->
        <variable
            name="user"
            type="www.zhang.com.databinding.User" />
        <!--  方式二 -->
        <!--<import type="www.zhang.com.databinding.User" />-->
        <!--<variable-->
            <!--name="user"-->
            <!--type="User" />-->
    </data>
    <!--我们需要展示的布局-->
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <!--绑定基本数据类型及String的使用是通过   @{数据类型的对象}  通过对应数据类型的控制显示-->
        <Button
            android:id="@+id/main_tv2"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:clickable="@{enabled}"
            android:text="@{content}" />
       <Button
            android:id="@+id/main_btn3"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{user.text}" /><!--这里user.text相当于user.getText()-->
    </LinearLayout>
</layout>
```

```xml
<data>
     <variable
            name="user"
            type="www.zhang.com.databinding.User" />

        <!--调用静态方法，需要先导入需要的包    当然java中的lang包可以不用导包-->
        <import type="www.zhang.com.databinding.Utils" />

    </data>
    android:text="@{Utils.getName(user)}" /><!--就和java中写代码一样，只要符合数据类型就好-->
    
    public class Utils {
    public static String getName(Object o) {
        return o.getClass().getName();
    }
}

		android:paddingLeft="@{user2.state ? @dimen/largepadding : (int)@dimen/smallpadding}"
        android:text="@{user2.content ?? @string/app_name}" />
        <!-- android:text="@{user2.content ?? @string/app_name}"
         等价于
         android:text="@{user2.content!=null? user2.content : @string/app_name}"-->
         android:text="@{`Hello World`+ @string/app_name}" /><!--``字符包裹的表示字符串，可用作拼接字符串 -->

 <data class="www.zhang.com.databinding.activity.Item">
    ...
</data>
import www.zhang.com.databinding.activity.Item;
Item binding = DataBindingUtil.setContentView(FiveActivity.this, R.layout.activity_five);



<data class=".Item">
    ...
</data>
import www.zhang.com.databinding.Item;
Item binding = DataBindingUtil.setContentView(FiveActivity.this, R.layout.activity_five);


<data class="Item">
    ...
</data>
import www.zhang.com.databinding.databinding.Item;
Item binding =DataBindingUtil.setContentView(FiveActivity.this, R.layout.activity_five);

<!--因为type="User"都为User类，会导致不知道到那个包，所以可以通过alias属性重命名type的类型，但实际上alias被指定的那个类型(如：www.zhang.com.databinding.model.User)-->
        <import type="www.zhang.com.databinding.model.User" alias="Model"/>
        
 
Model类继承BaseObservable,BaseObservable实现Android.databinding.Observable接口，Observable接口可以允许附加一个监听器到model对象以便监听对象上的所有属性的变化。
Observable接口有一个机制来添加和删除监听器，但通知与否由开发人员管理。为了使开发更容易，BaseObservable实现了监听器注册机制。DataBinding实现类依然负责通知当属性改变时。这是通过指定一个Bindable注解给getter以及setter内通知来完成的。
notifyPropertyChanged(BR.参数名)通知更新这一个参数，需要与@Bindable注解配合使用。notifyChange()通知更新所有参数，可以不用和@Bindable注解配合使用

Observable是一个接口，它的子类有BaseObservable,ObservableField,ObservableBoolean, ObservableByte, ObservableChar, ObservableShort, ObservableInt, ObservableLong, ObservableFloat, ObservableDouble, and ObservableParcelable，ObservableArrayList,ObservableArrayMap

泛型的支持会在编译时期报红线，是可以直接运行的,但是需要通过转义字符才行，如：\<数据类型> 或者\<数据类型\>
android:text="@{list[1]}"
android:text="@{map[`name`]}" 
android:text="@{arrays[0]}"

<include
            android:id="@+id/toolbar"
            layout="@layout/toolbar"
            android:layout_height="56dp"
            android:layout_width="match_parent"
            bind:content="@{con}" />
 <!--通过命名空间将写有toolbar的xml文件中定义的content对象作为属性绑定con对象，这2个对象是同一个类-->

```

