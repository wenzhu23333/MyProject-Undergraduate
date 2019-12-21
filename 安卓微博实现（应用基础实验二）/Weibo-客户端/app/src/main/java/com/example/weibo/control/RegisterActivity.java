package com.example.weibo.control;

import android.content.Intent;
import android.os.Looper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weibo.others.BasicalActivity;
import com.example.weibo.others.LoginService;
import com.example.weibo.basicaldata.Public;
import com.example.weibo.R;

public class RegisterActivity extends BasicalActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button signLog = (Button)findViewById(R.id.register_in);
        final EditText sName = (EditText)findViewById(R.id.register_user_name);
        final EditText sPassword = (EditText)findViewById(R.id.register_user_pass);
        final EditText sagainPassword = (EditText)findViewById(R.id.register_again_user_pass);
        signLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //获取用户输入的用户名和密码
                        String username = sName.getText().toString();
                        String password = sPassword.getText().toString();
                        String againpassword=sagainPassword.getText().toString();
                        final String response = LoginService.fetchRegisterItems(username,password);
                        //把用户名和密码上传给服务器
                        if(password.equals(againpassword)==false)
                        {
                            Looper.prepare();
                            Toast.makeText(RegisterActivity.this,"密码和确认密码不相等！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                        else if(response != null && response.equals("")==false){
                            Looper.prepare();
                            Toast.makeText(RegisterActivity.this,"注册并登录成功",Toast.LENGTH_SHORT).show();
                            //记录当前注册的用户信息并进入微博界面
                            Public.currentUser = Public.handleUserResponse(response);
                            Intent i = new Intent(RegisterActivity.this,WeiboListActivity.class);
                            startActivity(i);
                            Looper.loop();
                        }else{
                            Looper.prepare();
                            Toast.makeText(RegisterActivity.this,"该用户名已存在！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();


            }
        });



    }
}
