package com.example.activitycommunicate;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText account;
    private EditText password;

    // 法二：
//    public static MyUser myUser;
    // 法五：Activity Results API
    private ActivityResultLauncher<Intent> activityResultLauncher;


    private View.OnClickListener listen = v -> {
        // 法一：通过Bundle
//        Intent intent = new Intent(this, Activirty2.class);
//        MyUser myUser = new MyUser(account.getText().toString(), password.getText().toString(), 0, null);
//
//
//        // 1、创建Bundle
////        Bundle bundle = new Bundle();
////        bundle.putParcelable("user", myUser);
////        intent.putExtras(bundle);
//
//        // 2、Intent的putExtra，本质上也是放到Bundle中，该方法会自动判断Intent是否已绑定Bundle，有则放入，没有则创建Bundle后放入
//        intent.putExtra("user", myUser);
//        startActivity(intent);

//        // 法二：通过类的静态变量
//        myUser = new MyUser(account.getText().toString(), password.getText().toString(), 0, null);
//        Intent intent = new Intent(this, Activirty2.class);
//        startActivity(intent);

//        // 法三：通过Application
//        MyUser myUser = new MyUser(account.getText().toString(), password.getText().toString(), 0, null);
//        MyApplication application = (MyApplication) getApplicationContext();
//        application.setMyUser(myUser);
//        Intent intent = new Intent(this, Activirty2.class);
//        startActivity(intent);

//        // 法五、Activity Results API
//        Intent intent = new Intent(this, Activirty2.class);
//        MyUser myUser = new MyUser(account.getText().toString(), password.getText().toString(), 0, null);
//        intent.putExtra("user", myUser);
//        activityResultLauncher.launch(intent);

        // 法六：EventBus
        MyUser myUser = new MyUser(account.getText().toString(), password.getText().toString(), 0, null);
        EventBus.getDefault().postSticky(new MessageEvent(myUser, "Activity1"));
        startActivity(new Intent(this, Activirty2.class));

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // getIntent()方法取出Intent
        getMessage();
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        ImageButton btn_forward = findViewById(R.id.forward);
        btn_forward.setOnClickListener(listen);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(MessageEvent messageEvent){
        if(messageEvent.getMessageSource().equals("Activity2")){
            Log.e(TAG, "onCreate: "+ messageEvent.getUser().toString());
            Toast.makeText(MainActivity.this, messageEvent.getUser().toString(), Toast.LENGTH_LONG).show();
            // 哈希表持有粘性事件的强引用，不再使用的粘性事件应该移除
            EventBus.getDefault().removeStickyEvent(this);
        }
    }

    private void getMessage() {
//        // 法一：getIntent()方法取出Intent
//        Intent intent = getIntent();
//        if(intent != null){
//            MyUser myUser = intent.getParcelableExtra("user", MyUser.class);
//            if(myUser!=null){
//                Log.e(TAG, "onCreate: "+ myUser.toString());
//                Toast.makeText(this, myUser.toString(), Toast.LENGTH_LONG).show();
//            }
//
//        }

//        // 法二：通过类的静态变量
//        if(Activirty2.myUser!=null){
//            Log.e(TAG, "onCreate: "+ Activirty2.myUser.toString());
//            Toast.makeText(this, Activirty2.myUser.toString(), Toast.LENGTH_LONG).show();
//        }

//        // 法三：通过MyApplication
//        MyApplication application = (MyApplication) getApplicationContext();
//        if(application.getMyUser()!=null){
//            Log.e(TAG, "onCreate: "+ application.getMyUser().toString());
//            Toast.makeText(this, application.getMyUser().toString(), Toast.LENGTH_LONG).show();
//        }

//        // 法五、
//        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                MyUser user = result.getData().getParcelableExtra("user", MyUser.class);
//                if(user!=null){
//                    Log.e(TAG, "onCreate: "+ user.toString());
//                    Toast.makeText(MainActivity.this, user.toString(), Toast.LENGTH_LONG).show();
//                }
//            }
//        });
    }
}