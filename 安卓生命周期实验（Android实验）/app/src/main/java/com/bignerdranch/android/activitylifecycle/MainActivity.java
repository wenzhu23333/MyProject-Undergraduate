package com.bignerdranch.android.activitylifecycle;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,SimpleDialog.MyDialogFragment_Listener {

    private Button StartB;
    private Button StartC;
    private Button FinishA;
    private Button DialogA;
    private EditText LifeCycleMethod;
    private EditText ActivityStatus;
    private EditText ActivityNum;
    private int CurrentNum;
    private int from;

    private ArrayList<String> arrayList;

    private static final String ArrayListResult = "ArrayListResult";

    private static final  int Activity_B_Request = 0;
    private static final  int Activity_C_Request = 1;
    public static final String TAG = "MainActivity";


    public void getDataFrom_DialogFragment(Boolean isOver)
    {
        if (isOver)
        {
            arrayList.add("Activity A.onResume( )\n");
            showArrayList(arrayList);
            Conf.CurrentStatus[0] = 2;
            Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Activity_B_Request)
        {
            if (data == null) return;
            arrayList = Activity_B.getArrayListResult(data);
            arrayList.add("Activity B.onPause( )\n");
            arrayList.add("Activity A.onStart( )\n");
            arrayList.add("Activity A.onResume( )\n");
            arrayList.add("Activity B.onStop( )\n");
            arrayList.add("Activity B.onDestroy( )\n");
            showArrayList(arrayList);
            //Conf.CurrentStatus[1] = 0;
            Conf.CurrentStatus[0] = 2;
            Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
            showActivityNum_2();
        }
        if (requestCode == Activity_C_Request)
        {
            if (data == null) return;
            arrayList = Activity_B.getArrayListResult(data);
            arrayList.add("Activity C.onPause( )\n");
            arrayList.add("Activity A.onStart( )\n");
            arrayList.add("Activity A.onResume( )\n");
            arrayList.add("Activity C.onStop( )\n");
            arrayList.add("Activity C.onDestroy( )\n");
            //Conf.CurrentStatus[2] = 0;
            Conf.CurrentStatus[0] = 2;
            Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
            showArrayList(arrayList);
            showActivityNum_3();
        }
    }
    public void showActivityNum_1()
    {
     ActivityCollector.Activity_A_Num++;
     ActivityCollector.ShowActivityNum(ActivityNum);
    }
    public void showActivityNum_2()
    {
        ActivityCollector.Activity_B_Num--;
        ActivityCollector.ShowActivityNum(ActivityNum);
    }
    public void showActivityNum_3()
    {
        ActivityCollector.Activity_C_Num--;
        ActivityCollector.ShowActivityNum(ActivityNum);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        arrayList = new ArrayList<String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        from = getIntent().getIntExtra(Conf.From,0);
        ActivityCollector.addActivity(this);
        showActivityNum_1();
        CurrentNum = ActivityCollector.Activity_A_Num;
       if (isTaskRoot())
       {
           arrayList.add("Activity A.onCreate( )\n");
           arrayList.add("Activity A.onStart( )\n");
           arrayList.add("Activity A.onResume( )\n");
           Conf.CurrentStatus[0] = 2;
           Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
       }
      // getIntent().getStringArrayExtra("")
       if (from == 2)
       {
           arrayList = getIntent().getStringArrayListExtra(Conf.LifeCycleMessage);
           arrayList.add("Activity B.onPause( )\n");
           arrayList.add("Activity A.onCreate( )\n");
           arrayList.add("Activity A.onStart( )\n");
           arrayList.add("Activity A.onResume( )\n");
           arrayList.add("Activity B.onStop( )\n");
           Conf.CurrentStatus[1] = 3;
           Conf.CurrentStatus[0] = 2;
           Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
       }
       if (from == 3)
       {
           arrayList = getIntent().getStringArrayListExtra(Conf.LifeCycleMessage);
           arrayList.add("Activity C.onPause( )\n");
           arrayList.add("Activity A.onCreate( )\n");
           arrayList.add("Activity A.onStart( )\n");
           arrayList.add("Activity A.onResume( )\n");
           arrayList.add("Activity C.onStop( )\n");
           Conf.CurrentStatus[2] = 3;
           Conf.CurrentStatus[0] = 2;
           Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
       }
        showArrayList(arrayList);
        Log.d(TAG, "onCreate() called");
    }
   public void initView()
   {
       StartB = findViewById(R.id.StartB);
       StartC = findViewById(R.id.StartC);
       FinishA = findViewById(R.id.FinishA);
       DialogA = findViewById(R.id.Dialog);
       ActivityNum = findViewById(R.id.textView);
       LifeCycleMethod = findViewById(R.id.textView1);
       LifeCycleMethod.setMovementMethod(ScrollingMovementMethod.getInstance());
       LifeCycleMethod.setSelection(LifeCycleMethod.getText().length());
       ActivityStatus = findViewById(R.id.textView2);
       ActivityStatus.setMovementMethod(ScrollingMovementMethod.getInstance());
       StartB.setOnClickListener(this);
       StartC.setOnClickListener(this);
       FinishA.setOnClickListener(this);
       DialogA.setOnClickListener(this);
   }

    @Override
    public void onClick(View v) {
        int ID = v.getId();
        if (ID == R.id.Dialog)
        {
            arrayList.add("Activity A.onPause( )\n");
            showArrayList(arrayList);
            Conf.CurrentStatus[0] = 1;
            Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
            showDialog();
        }
        else if (ID == R.id.StartB)
        {
            Intent intent = Conf.newIntent(MainActivity.this,Activity_B.class,arrayList,1);
            startActivityForResult(intent,Activity_B_Request);
        }
        else if (ID == R.id.StartC)
        {
            Intent intent = Conf.newIntent(MainActivity.this,Activity_C.class,arrayList,1);
            startActivityForResult(intent,Activity_C_Request);
        }
        else if (ID == R.id.FinishA)
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
    private void setArrayListResult(ArrayList<String> arrayListResult) {
        Intent data = new Intent();
        data.putExtra(ArrayListResult, arrayListResult);
        setResult(RESULT_OK, data);
    }
    public static ArrayList<String> getArrayListResult(Intent intent)
    {
        return intent.getStringArrayListExtra(ArrayListResult);
    }
    public void showArrayList(ArrayList<String> arrayList)
    {
        LifeCycleMethod.setText("");
        for (String s:arrayList)
            LifeCycleMethod.append(s);
        LifeCycleMethod.setSelection(LifeCycleMethod.getText().length());
    }

    public void showDialog()
    {
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.show(getFragmentManager(),"simpleDialog");
    }

    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        if(ActivityCollector.isRepetition(this))
        {
            Conf.CurrentStatus[0] = 3;

        }
        else
        {
            Conf.CurrentStatus[0] = 0;
            //Log.d(TAG, "哈哈哈哈");
        }
        super.onPause();
        //arrayList.add("Activity A.onPause( )\n");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        ActivityCollector.removeActivity(this);
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
