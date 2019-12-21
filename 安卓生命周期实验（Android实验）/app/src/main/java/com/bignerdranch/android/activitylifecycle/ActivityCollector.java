package com.bignerdranch.android.activitylifecycle;

import android.app.Activity;
import android.widget.EditText;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 杨文卓 on 2018/10/29.
 */

public class ActivityCollector {
    public static List<Activity> activities = new LinkedList<>();
    public static void addActivity(Activity activity)
    {
        activities.add(activity);
    }
    public static void removeActivity(Activity activity)
    {
        activities.remove(activity);
    }
    public static void finishAll(){
        for (Activity activity:activities)
            if (!activity.isFinishing()) activity.finish();
    }
    public static boolean isRepetition(Activity activity)
    {
        for (int i = 0;i<activities.size()-1;i++) {
            if (activities.get(i) instanceof MainActivity)
                if (activity instanceof MainActivity) return true;
            if (activities.get(i) instanceof Activity_B)
                if (activity instanceof Activity_B) return true;
            if (activities.get(i)instanceof Activity_C)
                if (activity instanceof Activity_C) return true;
        }
        return false;
    }
    public static int Activity_A_Num = 0;
    public static int Activity_B_Num = 0;
    public static int Activity_C_Num = 0;
    public static void ShowActivityNum(EditText editText)
    {
        int total = Activity_A_Num+Activity_B_Num+Activity_C_Num;
        editText.setText("Total : "+total+" / A : "+Activity_A_Num+" / B : "+Activity_B_Num+" / C : "+Activity_C_Num);
    }
}
