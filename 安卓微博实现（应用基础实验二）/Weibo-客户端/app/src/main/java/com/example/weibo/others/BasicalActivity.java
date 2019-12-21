package com.example.weibo.others;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.example.weibo.R;

//此Activity主要是为了在登录或者注册时使用软键盘，让点击空白处时隐藏键盘
public class BasicalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basical);
    }

    //点击空白位置 隐藏软键盘
    public boolean onTouchEvent(MotionEvent event){
        if(null != this.getCurrentFocus()){
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super .onTouchEvent(event);
    }
}
