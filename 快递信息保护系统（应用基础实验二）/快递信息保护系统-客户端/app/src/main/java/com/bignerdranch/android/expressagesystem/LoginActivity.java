package com.bignerdranch.android.expressagesystem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static String privateKey = "";
    private SharedPreferences.Editor editor;

    private EditText accountEdit;


    private EditText passwordEdit;

    private Button login;

    private TextView register;

    private static final int OK = 200;
    private CheckBox rememberPass;
    private SharedPreferences sp;

    private final static int LOGIN_JUDGE = 1;
    private int RequestCode = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getWindow().setBackgroundDrawableResource(R.drawable.login);//第二种方式设置背景图片
        sp = getSharedPreferences("config", MODE_PRIVATE);
        accountEdit = (EditText) findViewById(R.id.user);
        passwordEdit = (EditText) findViewById(R.id.pass);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);

        login = (Button) findViewById(R.id.login);
        register =(TextView) findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        boolean isRemember = sp.getBoolean("remember_password", false);
        if (isRemember) {
            //将账号密码都设置到文本框中
            String account = sp.getString("account", "");
            String password = sp.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode== Activity.RESULT_OK){
            accountEdit.setText(data.getStringExtra("id"));
            passwordEdit.setText(data.getStringExtra("password"));
        }
    }

    Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.what){
                    case LOGIN_JUDGE:{
                        Bundle bundle = new Bundle();
                        bundle = msg.getData();
                        String result = bundle.getString("result");
                        //Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                        try {
                            if (!result.equals("fail")) {
                                    privateKey = result.split(",")[1];
                                    Intent intent = new Intent(LoginActivity.this,Courier.class);
                                    startActivity(intent);
                                    finish();

                            }else Toast.makeText(LoginActivity.this,"登录失败！",Toast.LENGTH_SHORT).show();
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    }
                    break;
                }
            }
        };

    public void onClick(View view) {
            switch (view.getId()){
                case R.id.login:{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //使用下面类里的函数，连接servlet，返回一个result，使用handler处理这个result
                            String result = HttpService.LoginByPost(accountEdit.getText().toString(),passwordEdit.getText().toString());
                            Bundle bundle = new Bundle();
                            bundle.putString("result",result);
                            Message message = new Message();
                            message.setData(bundle);
                            message.what = LOGIN_JUDGE;
                            handler.sendMessage(message);
                        }
                    }).start();
                }
                break;
                case R.id.register:{
                    Intent intent = new Intent(this,RegisterActivity.class);
                    startActivityForResult(intent,RequestCode);
                }
                break;
            }
        }
    }

