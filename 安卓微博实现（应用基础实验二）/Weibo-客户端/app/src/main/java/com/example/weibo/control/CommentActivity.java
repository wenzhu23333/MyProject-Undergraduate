package com.example.weibo.control;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weibo.others.BasicalActivity;
import com.example.weibo.others.LoginService;
import com.example.weibo.basicaldata.Public;
import com.example.weibo.R;

public class CommentActivity extends BasicalActivity {
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_write);
        flag=false;
        final EditText commentText = (EditText)findViewById(R.id.comment_content_text);
        final Button sendComment = (Button)findViewById(R.id.send_comment);
        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发表评论
                final String newCContent = commentText.getText().toString();
                if(TextUtils.isEmpty(commentText.getText())){
                    Toast.makeText(CommentActivity.this,"评论内容不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    //发送新评论给后台并且更新界面
                    Public.singleThreadExecutorWeibo.execute(new Runnable() {
                        @Override
                        public void run() {
                            //new FetchItemsTask().execute();
                            String response = LoginService.writeCommentItems(Public.clickedWeibo.getWeiboId(),Public.currentUser.getUserName(),newCContent);
                            //更新微博的评论数
                            Public.clickedWeibo.setCommentNum(response);

                        }
                    });
                    Toast.makeText(CommentActivity.this,"评论成功！",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }



}
