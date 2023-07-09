package com.example.activityresultapitest;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.security.PrivateKey;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ActivityResultLauncher<String> permissionLauncher;

    private ActivityResultLauncher<String[]> multiplePermissionsLauncher;
    private ActivityResultLauncher<Void> contactLauncher;
    private ActivityResultLauncher<Uri> takePictureLauncher;

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 申请单个权限
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                Log.d(TAG, "onActivityResult: "+(result? "权限申请成功" : "权限申请失败"));
                if(result){
                    Toast.makeText(MainActivity.this, "权限申请成功", Toast.LENGTH_LONG).show();
                    return;
                }
                boolean b = shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
                if(b){
                    // 被拒绝
                    Toast.makeText(MainActivity.this, "被用户拒绝", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "被用户拒绝，申请时或之前选择不再提示，智能跳转设置页手动赋予权限", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button btnRequestPermission = findViewById(R.id.btn_RequestPermission);
        btnRequestPermission.setOnClickListener(v->permissionLauncher.launch(Manifest.permission.CAMERA));

        // 申请一组权限
        multiplePermissionsLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                result.entrySet().forEach(entry -> {
                    Log.d(TAG, entry.getKey()+": "+(entry.getValue() ? "权限申请成功" : "权限申请失败"));
                });
            }
        });
        Button btnRequestMultiplePermissions = findViewById(R.id.btn_RequestMultiplePermissions);
        btnRequestMultiplePermissions.setOnClickListener(v->
                multiplePermissionsLauncher.launch(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION}));

        // 获取联系人
        contactLauncher = registerForActivityResult(new ActivityResultContracts.PickContact(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                Log.d(TAG, "onActivityResult: "+result);
            }
        });
        Button btnContact = findViewById(R.id.btn_Contact);
        btnContact.setOnClickListener(v->contactLauncher.launch(null));

        // 拍照
//        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), new ActivityResultCallback<Boolean>() {
//            @Override
//            public void onActivityResult(Boolean result) {
//                if(result){
//                    iv.setImageURI();
//                }else{
//                    Toast.makeText(MainActivity.this, "出错了5555", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//        Button btnTakePicture = findViewById(R.id.btn_TakePicture);
//        iv = findViewById(R.id.iv);
//        btnTakePicture.setOnClickListener(v->contactLauncher.launch(new Uri()));


    }
}