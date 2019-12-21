package com.bignerdranch.android.expressagesystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final static int REGISTER_JUDGE = 2;
    private EditText et_user_register;
    private EditText et_pass_register;
    private EditText et_pass_register2;
    private EditText et_MailBox;
    private EditText et_idcard_register;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = findViewById(R.id.register);
        register.setOnClickListener(this);
        et_MailBox = findViewById(R.id.et_mail_register);
        et_pass_register = findViewById(R.id.et_pass_register);
        et_pass_register2 = findViewById(R.id.et_pass_register2);
        et_user_register = findViewById(R.id.et_user_register);
        et_idcard_register = findViewById(R.id.et_idcard_register);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case REGISTER_JUDGE:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    //Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                    try {
                        if (result.equals("success")) {
                            Intent intent = new Intent();
                            intent.putExtra("id",et_user_register.getText().toString());
                            intent.putExtra("password",et_pass_register.getText().toString());
                            setResult(Activity.RESULT_OK,intent);//向上一级发送数据
                            finish();
                        }
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.register:{
                if (!et_pass_register.getText().toString().equals(et_pass_register2.getText().toString()))
                    Toast.makeText(RegisterActivity.this,"两次密码不一致！",Toast.LENGTH_LONG).show();
                else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = HttpService.RegisterByPost(et_user_register.getText().toString(),
                                    et_pass_register.getText().toString(),et_MailBox.getText().toString(),et_idcard_register.getText().toString());
                            Log.d("123",result+"123123");
                            Bundle bundle = new Bundle();
                            bundle.putString("result",result);
                            Message msg = new Message();
                            msg.what = REGISTER_JUDGE;
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    }).start();
                }
            break;
            }
        }
    }
}
