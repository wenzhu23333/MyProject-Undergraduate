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

public class LoginActivity extends BasicalActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText loginName = (EditText)findViewById(R.id.login_user_name);
        final EditText loginPassword = (EditText)findViewById(R.id.login_user_pass);
        Button register = (Button)findViewById(R.id.sign_up);
        Button logIn = (Button)findViewById(R.id.log_in);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入注册界面
                Intent intent_register = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent_register);

            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"正在提交请求",Toast.LENGTH_LONG).show();
                Intent i = new Intent(LoginActivity.this,WeiboListActivity.class);
                startActivity(i);
                //用于在当前界面上反馈服务器上是否有此账号
               new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String username = loginName.getText().toString();
                        String password = loginPassword.getText().toString();
                        final String response = LoginService.fetchLoginItems(username,password);
                        //将用户名和密码传给服务器
                        if(response != null && response.equals("")==false){
                          Looper.prepare();
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            //进入微博列表
                            Public.currentUser = Public.handleUserResponse(response);
                            Intent i = new Intent(LoginActivity.this,WeiboListActivity.class);
                            startActivity(i);
                            Looper.loop();
                        }else{
                            Looper.prepare();
                            Toast.makeText(LoginActivity.this,"用户名或密码有误！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();

            }
        });
        if(Public.isFirstInit) {
            Public.isFirstInit=false;
        }else{
            Public.isFirstInit = true;
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        Public.isFirstWeiboList = true;
        Public.isFirstWeiboDetail = true;
        Public.isFirstFollowedList = true;
        Public.isFirstPersonal = true;

        super.onDestroy();
    }
}
