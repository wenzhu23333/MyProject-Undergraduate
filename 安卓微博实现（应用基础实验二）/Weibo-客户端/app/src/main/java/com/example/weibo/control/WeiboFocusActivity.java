package com.example.weibo.control;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
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
import com.example.weibo.basicaldata.Public;
import com.example.weibo.basicaldata.Weibo;

import java.util.List;

public class WeiboFocusActivity extends AppCompatActivity {

    boolean flag;
    private LinearLayout focusWeiboListLayout;
    SwipeRefreshLayout refresh_focus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_focus);

        focusWeiboListLayout = (LinearLayout)findViewById(R.id.followed_weibo_list);


        Button listWeibo = (Button)findViewById(R.id.all_weibo_followed);
        Button writeWeibo = (Button)findViewById(R.id.write_weibo_followed);
        Button Personalinfomation = (Button)findViewById(R.id.my_personal_followed);
        Personalinfomation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入个人信息界面
                Intent i = new Intent(WeiboFocusActivity.this,PersonalActivity.class);
                startActivity(i);
            }
        });
        listWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入微博列表界面
                Intent i = new Intent(WeiboFocusActivity.this,WeiboListActivity.class);
                startActivity(i);
            }
        });
        
        writeWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入微博编辑界面
                Intent i = new Intent(WeiboFocusActivity.this,WeiboActivity.class);
                startActivity(i);
            }
        });

        

        if(Public.isFirstFollowedList) {
            flag=false;
            getFocusWeiboInfo();
            while(!flag){}
            showFollowedWeiboInfo();
            Public.isFirstFollowedList=false;
        }else{
            showFollowedWeiboInfo();
        }

    }



    private class FetchItemsTask extends AsyncTask<Void,Void,List<Weibo>> {
        @Override
        protected List<Weibo> doInBackground(Void... voids) {
            final String response = LoginService.fetchFollowedWeiboItems(Public.currentUser.getUserName());
            Public.currentFollowedWeiboList = Public.handleWeiboResponse(response);
            flag=true;
            return Public.currentFollowedWeiboList;
        }
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent();
        intent.setClass(WeiboFocusActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //注意本行的FLAG设置
        startActivity(intent);
        finish();
        //System.exit(0);
    }
    private void getFocusWeiboInfo(){
        Public.singleThreadExecutorWeibo.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    new FetchItemsTask().execute();

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void showFollowedWeiboInfo(){
        focusWeiboListLayout.removeAllViews();
        for(int i = 0; i<Public.currentFollowedWeiboList.size(); i++){
            final int j = Public.currentFollowedWeiboList.size()-i-1;
            View view = LayoutInflater.from(this).inflate(R.layout.weibo_list_item, focusWeiboListLayout, false);
            final TextView posterText = (TextView)view.findViewById(R.id.poster_name_item);
            final TextView weiboText = (TextView)view.findViewById(R.id.weibo_content_item);
            final TextView comNumText = (TextView)view.findViewById(R.id.comment_number_item);
            posterText.setText(Public.currentFollowedWeiboList.get(j).getPoster());
            weiboText.setText(Public.currentFollowedWeiboList.get(j).getWeiboText());
            comNumText.setText(String.valueOf(Public.currentFollowedWeiboList.get(j).getCommentNum()));
            focusWeiboListLayout.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //进入当前微博详情
                    //将被点击的微博条目的信息传给微博详情Activity
                    Toast.makeText(WeiboFocusActivity.this,"点开微博详情",Toast.LENGTH_SHORT).show();
                    Public.singleThreadExecutorWeibo.execute(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent();
                            Public.clickedWeibo = Public.currentFollowedWeiboList.get(j);
                            i.putExtra("POSTER",Public.currentFollowedWeiboList.get(j).getPoster());
                            i.putExtra("CONTENT",Public.currentFollowedWeiboList.get(j).getWeiboText());
                            i.setClass(WeiboFocusActivity.this,WeiboDetailActivity.class);
                            startActivity(i);
                        }
                    });

                }
            });
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        final ScrollView followed_weibo_list_scroll = (ScrollView)findViewById(R.id.followed_weibo_list_scroll);
        Button back_to_top = (Button)findViewById(R.id.back_to_top_followed);
        back_to_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followed_weibo_list_scroll.smoothScrollTo(0, 0);
            }
        });

        refresh_focus = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_followed);//找到刷新对象
        refresh_focus.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//设置刷新监听器
            @Override
            public void onRefresh() {
                flag=false;
                getFocusWeiboInfo();
                new Handler().postDelayed(new Runnable() {//模拟网络加载耗时操作（然而这是真的网络加载，是不是可以把获取信息写在这一步上面？
                    @Override
                    public void run() {

                        while(!flag){}
                        showFollowedWeiboInfo();

                        refresh_focus.setRefreshing(false);//取消刷新
                    }
                },2000);
            }
        });

    }




}
