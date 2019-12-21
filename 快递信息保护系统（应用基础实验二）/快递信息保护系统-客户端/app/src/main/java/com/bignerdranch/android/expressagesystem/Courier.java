package com.bignerdranch.android.expressagesystem;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Courier extends AppCompatActivity {

    private Button sacnButton;
    private Button fileButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);
        sacnButton = findViewById(R.id.button);
        fileButton = findViewById(R.id.button2);
        textView = findViewById(R.id.textView);
        setListener();
    }

    private void setListener(){
        sacnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(Courier.this);
                // 开始扫描
                intentIntegrator.initiateScan();
            }
        });
        fileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent()
                        .setType("image/*")
                        .setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "选择图片"), 10);
            }
        });
    }

    private void applyForpermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 获取解析结果
        if (requestCode==10&& Activity.RESULT_OK == resultCode && null != data){
            Uri uri = data.getData();
            applyForpermission();
            //安卓6.0之后一定要动态询问权限。
            String encrypted = QRCodeUtil.qrCodeDecode(getPath.getPath(this,uri)).trim();
            try {
                String decrypted = RSAUtils.DecryptToString(encrypted,LoginActivity.privateKey);
                if (decrypted==null||!decrypted.contains("姓名")){
                    textView.setText("扫描错误：未知二维码或该客户未被分配给您！");
                }
                else {
                    String info = "姓名:"+ decrypted.split("姓名:")[1]+"姓名:"+decrypted.split("姓名:")[2];
                    textView.setText(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
            }
            else {
                String encrypted = result.getContents();
                String decrypted = null;
                Log.d("123",encrypted);
                try {
                   decrypted = RSAUtils.DecryptToString(encrypted,LoginActivity.privateKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (decrypted==null||!decrypted.contains("姓名")){
                    textView.setText("扫描错误：未知二维码或该客户未被分配给您！");
                }
                else {
                    String info = "姓名:"+ decrypted.split("姓名:")[1]+"姓名:"+decrypted.split("姓名:")[2];
                    textView.setText(info);
                }

                Log.d("123",decrypted);
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
