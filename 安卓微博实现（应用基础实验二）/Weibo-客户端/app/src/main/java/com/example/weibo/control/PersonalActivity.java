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
import com.example.weibo.basicaldata.Public;
import com.example.weibo.R;
import com.example.weibo.basicaldata.Focus;

import java.util.List;

public class PersonalActivity extends AppCompatActivity {

    boolean flag;
    SwipeRefreshLayout swipe_refresh_layout_personal;
    private LinearLayout followedListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        
//将之前用户编辑好的个人信息进行显示，如果为首次登录则显示为空
        TextView userName = (TextView)findViewById(R.id.personal_name);
        userName.setText(Public.currentUser.getUserName());
        TextView userBirth = (TextView)findViewById(R.id.personal_birthday);
        userBirth.setText(Public.currentUser.getBirth());
        TextView userHobby = (TextView)findViewById(R.id.personal_hobby);
        userHobby.setText(Public.currentUser.getHobby());
        TextView userHome = (TextView)findViewById(R.id.personal_region);
        userHome.setText(Public.currentUser.getreligion());
        TextView userFans = (TextView)findViewById(R.id.personal_fans_num);
        userFans.setText(Public.currentUser.getFansNum());
        followedListLayout = (LinearLayout)findViewById(R.id.followed_list);

        Button listWeibo = (Button)findViewById(R.id.all_weibo_personal);
        Button focusWeibo = (Button)findViewById(R.id.followed_weibo_personal);
        Button editWeibo = (Button)findViewById(R.id.write_weibo_personal);
        Button editInfo = (Button)findViewById(R.id.edit_info);
        editWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入微博编辑界面
                Intent intent_edit= new Intent(PersonalActivity.this,WeiboActivity.class);
                startActivity(intent_edit);
            }
        });
        listWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入微博列表界面
                Intent intent_list= new Intent(PersonalActivity.this,WeiboListActivity.class);
                startActivity(intent_list);
            }
        });
        focusWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入已关注界面
                Intent intent_focus = new Intent(PersonalActivity.this,WeiboFocusActivity.class);
                startActivity(intent_focus);
            }
        });
        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //个人信息修改
                Intent intent_personal= new Intent(PersonalActivity.this,PersonalinfoActivity.class);
                startActivity(intent_personal);
            }
        });

        if(Public.isFirstPersonal) {
            flag=false;
            getFollowedInfo();
            while (!flag) {}
            showFocusInfo();
            Public.isFirstPersonal=false;
        }else{
            showFocusInfo();
        }

    }

    private void getFollowedInfo(){
        Public.singleThreadExecutorPersonal.execute(new Runnable() {
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

    private class FetchItemsTask extends AsyncTask<Void,Void,List<Focus>> {
        @Override
        protected List<Focus> doInBackground(Void... voids) {
            final String response = LoginService.fetchFollowedUserItems(Public.currentUser.getUserName());
            Public.currentFollowedNameList = Public.handleFollowedListResponse(response);
            flag=true;
            return Public.currentFollowedNameList;
        }
    }

    private void showFocusInfo(){
        followedListLayout.removeAllViews();
        for(int i = 0; i<Public.currentFollowedNameList.size(); i++){
            final int j = Public.currentFollowedNameList.size()-i-1;
            View view = LayoutInflater.from(this).inflate(R.layout.focus_list_item, followedListLayout, false);
            final TextView followedName = (TextView)view.findViewById(R.id.followed_name_item);
            String ceshi = Public.currentFollowedNameList.get(j).getFollowed();
            followedName.setText(Public.currentFollowedNameList.get(j).getFollowed());
            followedListLayout.addView(view);
            final Button cancelFollowed = (Button)findViewById(R.id.cancel_followed);
            cancelFollowed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //取消关注该用户
                            final String response = LoginService.cancelFollowedItems(Public.currentUser.getUserName(),Public.currentFollowedNameList.get(j).getFollowed());
                            Looper.prepare();
                            Toast.makeText(PersonalActivity.this,response,Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }).start();
                }
            });
        }
    }



    @Override
    protected void onResume() {
        super.onResume();

        final ScrollView followed_list_scroll = (ScrollView)findViewById(R.id.followed_list_scroll);
        //设置回到首个微博按钮
        Button back_to_top = (Button)findViewById(R.id.back_to_top_personal);
        back_to_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followed_list_scroll.smoothScrollTo(0, 0);
            }
        });

        swipe_refresh_layout_personal = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_personal);//找到刷新对象
        swipe_refresh_layout_personal.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//设置刷新监听器
            @Override
            public void onRefresh() {
                flag=false;
                getFollowedInfo();
                new Handler().postDelayed(new Runnable() {//模拟网络加载耗时操作（然而这是真的网络加载，是不是可以把获取信息写在这一步上面？
                    @Override
                    public void run() {
                        
                        TextView userHobby = (TextView)findViewById(R.id.personal_hobby);
                        userHobby.setText(Public.currentUser.getHobby());
                        TextView userHome = (TextView)findViewById(R.id.personal_region);
                        userHome.setText(Public.currentUser.getreligion());
                        TextView userFans = (TextView)findViewById(R.id.personal_fans_num);
                        userFans.setText(Public.currentUser.getFansNum());
                        TextView userName = (TextView)findViewById(R.id.personal_name);
                        userName.setText(Public.currentUser.getUserName());
                        TextView userBirth = (TextView)findViewById(R.id.personal_birthday);
                        userBirth.setText(Public.currentUser.getBirth());
                        while(!flag){}
                        showFocusInfo();

                        swipe_refresh_layout_personal.setRefreshing(false);//取消刷新
                    }
                },2000);
            }
        });

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(PersonalActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //注意本行的FLAG设置
        startActivity(intent);
        finish();
    }

}
