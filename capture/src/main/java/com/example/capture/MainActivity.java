package com.example.capture;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ImageView ivResult;

    private VideoView videoView;

    private ActivityResultLauncher<String> permissionLauncher;

    private ActivityResultLauncher<Uri>  takePictureLauncher;

    private ActivityResultLauncher<Void> takePicturePreviewLauncher;

    private ActivityResultLauncher<String> selectPhotoLauncher;

    private ActivityResultLauncher<Uri> takeVideoLauncher;

    private Uri imageUri;

    private Uri videoUri;

    private int captureType = 0;

    private boolean isPlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivResult = findViewById(R.id.ivResult);
        videoView = findViewById(R.id.videoView);
        Button btnCaptureSmall = findViewById(R.id.btnCaptureSmall);
        Button btnCaptureRaw = findViewById(R.id.btnCaptureRaw);
        Button btnSelectPhoto = findViewById(R.id.btnSelectPhoto);
        Button btnCaptureVideo = findViewById(R.id.btnCaptureVideo);

        videoView.setOnClickListener(v->{
            VideoView vv = (VideoView) v;
            if(isPlay){
                vv.pause();
            }else{
                vv.start();
            }
        });

        // 获取授权
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(!result){
                    Toast.makeText(MainActivity.this, "用户拒绝授权，请手动赋予照相权限", Toast.LENGTH_LONG).show();
                }else{
                    switch (captureType){
                        case 1:
                            takePicturePreviewLauncher.launch(null);
                            break;
                        case 2:
                            takePictureLauncher.launch(imageUri);
                            break;
                        case 6:
                            takeVideoLauncher.launch(videoUri);
                            break;
                    }
                }
            }
        });

        //拍照返回缩略图
        takePicturePreviewLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                ivResult.setImageBitmap(result);
            }
        });
        btnCaptureSmall.setOnClickListener(v->{
            imageUri = getSaveUri(1);
            captureType = 1;
            //判断是否授权：照相
            if(!isGranted(Manifest.permission.CAMERA)){
                permissionLauncher.launch(Manifest.permission.CAMERA);
            }else{
                takePicturePreviewLauncher.launch(null);
            }
        });

        // 拍照
        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){
                    ivResult.setImageURI(imageUri);
                }else{
                    Toast.makeText(MainActivity.this, "出错了55555555", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnCaptureRaw.setOnClickListener(v->{
            imageUri = getSaveUri(1);
            captureType = 2;
            //判断是否授权：照相
            if(!isGranted(Manifest.permission.CAMERA)){
                permissionLauncher.launch(Manifest.permission.CAMERA);
            }else{
                takePictureLauncher.launch(imageUri);
            }
        });

        // 选择图片
        selectPhotoLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                ivResult.setImageURI(result);
            }
        });
        btnSelectPhoto.setOnClickListener(v->{
            selectPhotoLauncher.launch("image/*");
        });

        // 录视频
        takeVideoLauncher = registerForActivityResult(new ActivityResultContracts.TakeVideo(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                videoView.setVideoURI(videoUri);
                videoView.setVisibility(View.VISIBLE);
            }
        });
        btnCaptureVideo.setOnClickListener(v->{
            videoUri = getSaveUri(2);
            captureType = 6;
            //判断是否授权：照相
            if(!isGranted(Manifest.permission.CAMERA)){
                permissionLauncher.launch(Manifest.permission.CAMERA);
            }else{
                takeVideoLauncher.launch(videoUri);
            }
        });







    }


    private Uri getSaveUri(int type)  {
        File storageFile;
        if(Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()){
            storageFile = getExternalCacheDir();
        }else{
            storageFile = getCacheDir();
        }
        File file = null;
        try {
            if(type == 1){
                //photo
                file = File.createTempFile("tmp_iamge_file", ".png", storageFile);
                file.createNewFile(); //创建新的临时文件
//            photoFile.deleteOnExit();  //推出JVM时删除
            }else if(type == 2){
                // vedio
                file = File.createTempFile("tmp_iamge_file", ".mp4", storageFile);
                file.createNewFile(); //创建新的临时文件
            }else{
                // 待新增
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Uri res = FileProvider.getUriForFile(this, getApplicationContext().getPackageName()+".fileprovider", file);
        return res;
    }

    private boolean isGranted(String permission){
        return ActivityCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
    }


}