package com.example.weibo.control;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weibo.others.LoginService;
import com.example.weibo.R;
import com.example.weibo.basicaldata.Comment;
import com.example.weibo.basicaldata.Public;

import java.util.ArrayList;
import java.util.List;

public class WeiboDetailActivity extends AppCompatActivity {

    boolean flag;
    private LinearLayout commentListLayout;
    SwipeRefreshLayout swipe_refresh_layout_detail;

    public static List<Comment> com = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_detail);

        commentListLayout = (LinearLayout)findViewById(R.id.comment_list);
        TextView poster = (TextView)findViewById(R.id.poster_name);
        TextView content = (TextView)findViewById(R.id.weibo_content);
        TextView commentNum = (TextView)findViewById(R.id.comment_number);

        Intent i = getIntent();
        String posterExtra = i.getStringExtra("POSTER");
        String contentExtra = i.getStringExtra("CONTENT");
        String commentNumExtra = Public.clickedWeibo.getCommentNum();
        poster.setText(posterExtra);
        content.setText(contentExtra);
        commentNum.setText(commentNumExtra);

        Button writeComment = (Button)findViewById(R.id.write_comment);
        writeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入评论编辑界面
                Intent i = new Intent(WeiboDetailActivity.this,CommentActivity.class);
                startActivity(i);
            }
        });

        Button addFollow = (Button)findViewById(R.id.add_follow);
        addFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加关注
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String response = LoginService.addFollowedItems(Public.currentUser.getUserName(),Public.clickedWeibo.getPoster());
                        Looper.prepare();
                        Toast.makeText(WeiboDetailActivity.this,response,Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }).start();
            }
        });

        if(Public.isFirstWeiboDetail) {
            flag=false;
            getCommentInfo();
            while(!flag){}
            showCommentInfo();
            Public.isFirstWeiboDetail=false;
        }else{
            showCommentInfo();
        }


    }


    private void getCommentInfo(){
        Public.singleThreadExecutorWeibo.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    new FetchItemsTask().execute();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

    private void showCommentInfo(){
        commentListLayout.removeAllViews();
        for(int i = 0; i<Public.currentCommentList.size(); i++){
            int j = Public.currentCommentList.size()-i-1;
            View view = LayoutInflater.from(this).inflate(R.layout.comment_list_item, commentListLayout, false);
            final TextView commentatorText = (TextView)view.findViewById(R.id.commentator_name);
            final TextView commentText = (TextView)view.findViewById(R.id.comment_content);
            commentatorText.setText(Public.currentCommentList.get(j).getCommentator());
            commentText.setText(Public.currentCommentList.get(j).getCommentText());
            commentListLayout.addView(view);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        final ScrollView comment_list_scroll = (ScrollView)findViewById(R.id.comment_list_scroll);
        Button back_to_top_comment = (Button)findViewById(R.id.back_to_top_comment);
        back_to_top_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_list_scroll.smoothScrollTo(0, 0);
            }
        });

        swipe_refresh_layout_detail = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_detail);//找到刷新对象
        swipe_refresh_layout_detail.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//设置刷新监听器
            @Override
            public void onRefresh() {
                flag=false;
                getCommentInfo();
                new Handler().postDelayed(new Runnable() {//模拟耗时操作
                    @Override
                    public void run() {

                        while(!flag){}
                        showCommentInfo();

                        TextView commentNum = (TextView)findViewById(R.id.comment_number);
                        commentNum.setText(Public.clickedWeibo.getCommentNum());

                        swipe_refresh_layout_detail.setRefreshing(false);//取消刷新
                    }
                },2000);
            }
        });



    }

    private class FetchItemsTask extends AsyncTask<Void,Void,List<Comment>> {
        @Override
        protected List<Comment> doInBackground(Void... voids) {
            final String response = LoginService.fetchCommentItems(Public.clickedWeibo.getWeiboId());
            Public.currentCommentList = Public.handleCommentResponse(response);
            flag=true;
            return Public.currentCommentList;
        }
    }


}
