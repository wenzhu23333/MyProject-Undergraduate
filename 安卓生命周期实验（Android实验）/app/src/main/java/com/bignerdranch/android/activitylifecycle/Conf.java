package com.bignerdranch.android.activitylifecycle;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import java.net.Inet4Address;
import java.util.ArrayList;

/**
 * Created by 杨文卓 on 2018/10/28.
 */
enum STATUS{NEVER,CREATE,START,RESUME,PAUSE,STOP,DESTROY};
public  class  Conf {
    public static final String LifeCycleMessage = "LifeCycleMessage";
    public static final String From = "from";
    public static Intent newIntent(Context sender, Class<?> receiver, ArrayList<String> message,int from) {
        Intent intent = new Intent(sender, receiver);
        intent.putExtra(LifeCycleMessage, message);
        intent.putExtra(From,from);
        return intent;
    }
    public static int[] CurrentStatus = new int[]{
      0,0,0
    };
    public static void showCurrentStatus(EditText editText,int[] currentStatus)
    {
        editText.setText("");
        if (Conf.CurrentStatus[0]==1) editText.append("Activity A: Paused\n");
        if (Conf.CurrentStatus[0]==2) editText.append("Activity A: Resumed\n");
        if (Conf.CurrentStatus[0]==3) editText.append("Activity A: Stopped\n");


        if (Conf.CurrentStatus[1]==1) editText.append("Activity B: Paused\n");
        if (Conf.CurrentStatus[1]==2) editText.append("Activity B: Resumed\n");
        if (Conf.CurrentStatus[1]==3) editText.append("Activity B: Stopped\n");

        if (Conf.CurrentStatus[2]==1) editText.append("Activity C: Paused\n");
        if (Conf.CurrentStatus[2]==2) editText.append("Activity C: Resumed\n");
        if (Conf.CurrentStatus[2]==3) editText.append("Activity C: Stopped\n");
        editText.setSelection(editText.getText().length());
    }
    public static void ChangeCurrentStatus(int i,EditText editText)
    {
        Conf.CurrentStatus[i] = 2;
        Conf.showCurrentStatus(editText,Conf.CurrentStatus);
    }
}
