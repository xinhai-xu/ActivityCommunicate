package com.example.activitycommunicate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Activirty2 extends AppCompatActivity {
    private static final String TAG = "Activirty2";

    // 法一、法五：
    private MyUser myUser;

//    // 法二：
//    public static MyUser myUser;
    private EditText age;
    private EditText phone;


    private View.OnClickListener listen = v -> {
//        Intent intent = new Intent(this, MainActivity.class);
//        if(myUser != null){
//            myUser.setAge(Integer.parseInt(age.getText().toString()));
//            myUser.setPhone(phone.getText().toString());
//        }
//        // 法一、创建Bundle
////        Bundle bundle = new Bundle();
////        bundle.putParcelable("user", myUser);
////        intent.putExtras(bundle);
//        // 法二、Intent的putExtra，本质上也是放到Bundle中，该方法会自动判断Intent是否已绑定Bundle，有则放入，没有则创建Bundle后放入
//        intent.putExtra("user", myUser);
//        startActivity(intent);

//        // 法二：通过类的静态变量
//        myUser = new MyUser(MainActivity.myUser.getAccount(), MainActivity.myUser.getPassword(),
//                Integer.parseInt(age.getText().toString()), phone.getText().toString());
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//
//        // 法三：通过Application
//        MyApplication application = (MyApplication) getApplicationContext();
//        MyUser myUser1 = application.getMyUser();
//        myUser1.setAge(Integer.parseInt(age.getText().toString()));
//        myUser1.setPhone(phone.getText().toString());
//        Intent intent = new Intent(this, Activirty2.class);
//        startActivity(intent);

//        //法五：
//        Intent intent = new Intent();
//        if(myUser != null){
//            myUser.setAge(Integer.parseInt(age.getText().toString()));
//            myUser.setPhone(phone.getText().toString());
//        }
//        intent.putExtra("user", myUser);
//        setResult(RESULT_OK, intent);
//        finish();

        // 法六：EventBus
        if(myUser != null){
            myUser.setAge(Integer.parseInt(age.getText().toString()));
            myUser.setPhone(phone.getText().toString());
            EventBus.getDefault().postSticky(new MessageEvent(myUser, "Activity2"));
        }
        finish();

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        getMessage();
        age = findViewById(R.id.age);
        phone = findViewById(R.id.phone);
        ImageButton btn_back = findViewById(R.id.back);
        btn_back.setOnClickListener(listen);
        //注册订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销订阅者
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(MessageEvent messageEvent){
        if(messageEvent.getMessageSource().equals("Activity1")){
            Log.e(TAG, "onCreate: "+ messageEvent.getUser().toString());
            myUser = messageEvent.getUser();
            Toast.makeText(Activirty2.this, messageEvent.getUser().toString(), Toast.LENGTH_LONG).show();
            // 哈希表持有粘性事件的强引用，不再使用的粘性事件应该移除
            EventBus.getDefault().removeStickyEvent(this);
        }
    }

    private void getMessage() {
//        // getIntent()方法取出Intent
//        Intent intent = getIntent();
//        if(intent != null){
//            myUser = intent.getParcelableExtra("user", MyUser.class);
//            Log.e(TAG, "onCreate: "+ myUser.toString());
//            Toast.makeText(this, myUser.toString(), Toast.LENGTH_LONG).show();
//        }

//        if(MainActivity.myUser!=null){
//            Log.e(TAG, "onCreate: "+ MainActivity.myUser.toString());
//            Toast.makeText(this, MainActivity.myUser.toString(), Toast.LENGTH_LONG).show();
//        }

//        // 法三：通过MyApplication
//        MyApplication application = (MyApplication) getApplicationContext();
//        if(application.getMyUser()!=null){
//            Log.e(TAG, "onCreate: "+ application.getMyUser().toString());
//            Toast.makeText(this, application.getMyUser().toString(), Toast.LENGTH_LONG).show();
//        }

//        // 法五、
//        Intent intent = getIntent();
//        if(intent != null){
//            myUser = intent.getParcelableExtra("user", MyUser.class);
//            Log.e(TAG, "onCreate: "+ myUser.toString());
//            Toast.makeText(this, myUser.toString(), Toast.LENGTH_LONG).show();
//        }
    }
}
