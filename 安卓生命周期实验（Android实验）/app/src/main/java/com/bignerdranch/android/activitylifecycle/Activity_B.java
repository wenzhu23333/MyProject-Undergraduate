package com.bignerdranch.android.activitylifecycle;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class Activity_B extends AppCompatActivity implements View.OnClickListener,SimpleDialog.MyDialogFragment_Listener {

    private Button StartA;
    private Button StartC;
    private Button FinishB;
    private Button DialogB;
    private EditText LifeCycleMethod;
    private EditText ActivityStatus;
    private ArrayList<String> arrayList;
    private EditText ActivityNum;
    private int from;
    private static final String ArrayListResult = "ArrayListResult";
    private static final  int Activity_A_Request = 2;
    private static final  int Activity_C_Request = 3;
    public void getDataFrom_DialogFragment(Boolean isOver)
    {
        if (isOver)
        {
            arrayList.add("Activity B.onResume( )\n");
            showArrayList(arrayList);
            Conf.CurrentStatus[1] = 2;
            Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Activity_A_Request)
        {
            if (data == null) return;
            arrayList = MainActivity.getArrayListResult(data);
            arrayList.add("Activity A.onPause( )\n");
            arrayList.add("Activity B.onStart( )\n");
            arrayList.add("Activity B.onResume( )\n");
            arrayList.add("Activity A.onStop( )\n");
            arrayList.add("Activity A.onDestroy( )\n");
            showArrayList(arrayList);
            Conf.CurrentStatus[1] = 2;
            //Conf.CurrentStatus[0] = 0;
            Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
            showActivityNum_2();
        }
        if (requestCode == Activity_C_Request)
        {
            if (data == null) return;
            arrayList = MainActivity.getArrayListResult(data);
            arrayList.add("Activity C.onPause( )\n");
            arrayList.add("Activity B.onStart( )\n");
            arrayList.add("Activity B.onResume( )\n");
            arrayList.add("Activity C.onStop( )\n");
            arrayList.add("Activity C.onDestroy( )\n");
            showArrayList(arrayList);
            Conf.CurrentStatus[1] = 2;
           // Conf.CurrentStatus[2] = 0;
            Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
            showActivityNum_3();
        }
    }
    public void showActivityNum_1()
    {
        ActivityCollector.Activity_B_Num++;
        ActivityCollector.ShowActivityNum(ActivityNum);
    }
    public void showActivityNum_2()
    {
        ActivityCollector.Activity_A_Num--;
        ActivityCollector.ShowActivityNum(ActivityNum);
    }
    public void showActivityNum_3()
    {
        ActivityCollector.Activity_C_Num--;
        ActivityCollector.ShowActivityNum(ActivityNum);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__b);
        initView();
        from = getIntent().getIntExtra(Conf.From,0);
        ActivityCollector.addActivity(this);
        showActivityNum_1();
        arrayList = getIntent().getStringArrayListExtra(Conf.LifeCycleMessage);
        if (from==1)
        {
            arrayList.add("Activity A.onPause( )\n");
            arrayList.add("Activity B.onCreate( )\n");
            arrayList.add("Activity B.onStart( )\n");
            arrayList.add("Activity B.onResume( )\n");
            arrayList.add("Activity A.onStop( )\n");
            Conf.CurrentStatus[0] = 3;
            Conf.CurrentStatus[1] = 2;
            Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
        }
        if (from==3)
        {
            arrayList.add("Activity C.onPause( )\n");
            arrayList.add("Activity B.onCreate( )\n");
            arrayList.add("Activity B.onStart( )\n");
            arrayList.add("Activity B.onResume( )\n");
            arrayList.add("Activity C.onStop( )\n");
            Conf.CurrentStatus[2] = 3;
            Conf.CurrentStatus[1] = 2;
            Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
        }
        showArrayList(arrayList);
        Log.d(MainActivity.TAG, "B onCreate() called");

    }
    public void initView()
    {
        StartA = findViewById(R.id.StartA);
        StartC = findViewById(R.id.StartC);
        FinishB = findViewById(R.id.FinishB);
        DialogB = findViewById(R.id.Dialog);
        ActivityNum = findViewById(R.id.textView);
        LifeCycleMethod = findViewById(R.id.textView1);
        LifeCycleMethod.setMovementMethod(ScrollingMovementMethod.getInstance());
        LifeCycleMethod.setSelection(LifeCycleMethod.getText().length());
        ActivityStatus = findViewById(R.id.textView2);
        ActivityStatus.setMovementMethod(ScrollingMovementMethod.getInstance());
        StartA.setOnClickListener(this);
        StartC.setOnClickListener(this);
        FinishB.setOnClickListener(this);
        DialogB.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivity.TAG, "B onStart() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MainActivity.TAG, "B onStop() called");
    }

    @Override
    protected void onDestroy() {
        ActivityCollector.removeActivity(this);
        super.onDestroy();
        Log.d(MainActivity.TAG, "B onDestroy() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MainActivity.TAG, "B onResume() called");

    }

    @Override
    protected void onPause() {
        if(ActivityCollector.isRepetition(this))
        {
            Conf.CurrentStatus[1] = 3;

        }
        else
        {
            Conf.CurrentStatus[1] = 0;
            //Log.d(TAG, "哈哈哈哈");
        }
        super.onPause();
        Log.d(MainActivity.TAG, "B onPause() called");
    }

    @Override
    public void onClick(View v) {
        int ID = v.getId();
        if (ID == R.id.Dialog)
        {
            arrayList.add("Activity B.onPause( )\n");
            showArrayList(arrayList);
            Conf.CurrentStatus[1] = 1;
            Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
            showDialog();
        }
        else if (ID == R.id.StartA)
        {
           Intent intent = Conf.newIntent(Activity_B.this,MainActivity.class,arrayList,2);
           startActivityForResult(intent,Activity_A_Request);
        }
        else if (ID == R.id.StartC)
        {
            Intent intent = Conf.newIntent(Activity_B.this,Activity_C.class,arrayList,2);
            startActivityForResult(intent,Activity_C_Request);
        }
        else if (ID == R.id.FinishB)
        {
            setArrayListResult(arrayList);
            finish();
        }

    }
    @Override
    public void onBackPressed() {
        setArrayListResult(arrayList);
        super.onBackPressed();

    }
    public void showDialog()
    {
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.show(getFragmentManager(),"simpleDialog");
    }
    public  void  showArrayList(ArrayList<String> arrayList)
    {
        LifeCycleMethod.setText("");
        for (String s:arrayList)
            LifeCycleMethod.append(s);
        LifeCycleMethod.setSelection(LifeCycleMethod.getText().length());
    }
    private void setArrayListResult(ArrayList<String> arrayListResult) {
        Intent data = new Intent();
        data.putExtra(ArrayListResult, arrayListResult);
        setResult(RESULT_OK, data);
    }
    public static ArrayList<String> getArrayListResult(Intent intent)
    {
        return intent.getStringArrayListExtra(ArrayListResult);
    }

}
