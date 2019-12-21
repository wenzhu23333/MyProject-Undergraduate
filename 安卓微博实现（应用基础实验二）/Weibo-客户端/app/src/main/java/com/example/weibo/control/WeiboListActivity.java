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

import com.example.weibo.others.LoginService;
import com.example.weibo.basicaldata.Public;
import com.example.weibo.R;
import com.example.weibo.basicaldata.Weibo;

import java.util.ArrayList;
import java.util.List;

public class WeiboListActivity extends AppCompatActivity {

    boolean flag;
    private LinearLayout weiboListLayout;
    SwipeRefreshLayout swipe_refresh_layout;

    public static List<Weibo> wb = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_list);
        //flag=false;
        weiboListLayout = (LinearLayout)findViewById(R.id.weibo_list);
        Button editWeibo = (Button)findViewById(R.id.write_weibo);
        Button focusWeibo = (Button)findViewById(R.id.followed_weibo);
        Button Personalinfo = (Button)findViewById(R.id.my_personal);
        editWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //微博编写发送
                Intent intent_edit= new Intent(WeiboListActivity.this,WeiboActivity.class);
                startActivity(intent_edit);
            }
        });

        focusWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关注的用户
                Intent intent_focus = new Intent(WeiboListActivity.this,WeiboFocusActivity.class);
                startActivity(intent_focus);
            }
        });


        Personalinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //个人信息
                Intent intent_information = new Intent(WeiboListActivity.this,PersonalActivity.class);
                startActivity(intent_information);
            }
        });


        if(Public.isFirstWeiboList) {
            flag = false;
            getWeiboInfo();
            while (!flag) {}
            showWeiboInfo();
            Public.isFirstWeiboList=false;
        }else{
            showWeiboInfo();
        }


    }

    private void showWeiboInfo(){
        weiboListLayout.removeAllViews();
        for(int i = 0; i<Public.currentWeiboList.size(); i++){
            final int j = Public.currentWeiboList.size()-i-1;
            View view = LayoutInflater.from(this).inflate(R.layout.weibo_list_item, weiboListLayout, false);
            final TextView posterText = (TextView)view.findViewById(R.id.poster_name_item);
            final TextView weiboText = (TextView)view.findViewById(R.id.weibo_content_item);
            final TextView comNumText = (TextView)view.findViewById(R.id.comment_number_item);
            posterText.setText(Public.currentWeiboList.get(j).getPoster());
            weiboText.setText(Public.currentWeiboList.get(j).getWeiboText());
            comNumText.setText(String.valueOf(Public.currentWeiboList.get(j).getCommentNum()));
            weiboListLayout.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //进入当前微博详情
                    //将被点击的微博条目的信息传给微博详情Activity
                    //Toast.makeText(WeiboListActivity.this,"点开微博详情",Toast.LENGTH_SHORT).show();
                    Public.singleThreadExecutorWeibo.execute(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent();
                            Public.clickedWeibo = Public.currentWeiboList.get(j);
                            i.putExtra("POSTER",Public.currentWeiboList.get(j).getPoster());
                            i.putExtra("CONTENT",Public.currentWeiboList.get(j).getWeiboText());
                            i.setClass(WeiboListActivity.this,WeiboDetailActivity.class);
                            startActivity(i);
                        }
                    });

                }
            });
        }
    }

    private void getWeiboInfo(){
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

    private class FetchItemsTask extends AsyncTask<Void,Void,List<Weibo>> {
        @Override
        protected List<Weibo> doInBackground(Void... voids) {
            final String response = LoginService.fetchWeiboItems();
            Public.currentWeiboList = Public.handleWeiboResponse(response);
            flag=true;
            return Public.currentWeiboList;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        final ScrollView weibo_list_scroll = (ScrollView)findViewById(R.id.weibo_list_scroll);
        Button back_to_top = (Button)findViewById(R.id.back_to_top);
        back_to_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weibo_list_scroll.smoothScrollTo(0, 0);
            }
        });

        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);//找到刷新对象
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//设置刷新监听器
            @Override
            public void onRefresh() {
                flag=false;
                getWeiboInfo();
                new Handler().postDelayed(new Runnable() {//模拟网络加载耗时操作（然而这是真的网络加载，是不是可以把获取信息写在这一步上面？
                    @Override
                    public void run() {

                        while(!flag){}
                        showWeiboInfo();

                        swipe_refresh_layout.setRefreshing(false);//取消刷新
                    }
                },2000);
            }
        });

    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent();
        intent.setClass(WeiboListActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //注意本行的FLAG设置
        startActivity(intent);
        finish();
        //System.exit(0);
    }

}
