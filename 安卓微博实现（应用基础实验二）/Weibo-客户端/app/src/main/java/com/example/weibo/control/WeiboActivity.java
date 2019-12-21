package com.example.weibo.control;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weibo.others.BasicalActivity;
import com.example.weibo.others.LoginService;
import com.example.weibo.R;
import com.example.weibo.basicaldata.Public;

public class WeiboActivity extends BasicalActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_write);

        final EditText weiboText = (EditText)findViewById(R.id.weibo_content_text);
        final Button sendWeibo = (Button)findViewById(R.id.send_weibo);
        sendWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发微博
                final String newWContent = weiboText.getText().toString();
                if(TextUtils.isEmpty(weiboText.getText())){
                    Toast.makeText(WeiboActivity.this,"微博内容不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    //添加新微博到后台数据库微博表

                    Public.singleThreadExecutorWeibo.execute(new Runnable() {
                        @Override
                        public void run() {
                            final String response = LoginService.writeWeiboItems(Public.currentUser.getUserName(),newWContent);
                        }
                    });

                    Toast.makeText(WeiboActivity.this,"微博发送成功！",Toast.LENGTH_SHORT).show();
                    finish();

                }

            }
        });
    }
}
