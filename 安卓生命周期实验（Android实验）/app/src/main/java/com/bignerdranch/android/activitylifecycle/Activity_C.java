package com.bignerdranch.android.activitylifecycle;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class Activity_C extends AppCompatActivity implements View.OnClickListener,SimpleDialog.MyDialogFragment_Listener {

    private Button StartA;
    private Button StartB;
    private Button FinishC;
    private Button DialogC;
    private EditText LifeCycleMethod;
    private EditText ActivityStatus;
    private ArrayList<String> arrayList;
    private EditText ActivityNum;
    private int from;
    private static final  int Activity_A_Request = 4;
    private static final  int Activity_B_Request = 5;
    private static final String ArrayListResult = "ArrayListResult";
    public void getDataFrom_DialogFragment(Boolean isOver)
    {
        if (isOver)
        {
            arrayList.add("Activity C.onResume( )\n");
            showArrayList(arrayList);
            Conf.CurrentStatus[2] = 2;
            Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Activity_A_Request)
        {
            if (data == null) return;
            arrayList = MainActivity.getArrayListResult(data);
            arrayList.add("Activity A.onPause( )\n");
            arrayList.add("Activity C.onStart( )\n");
            arrayList.add("Activity C.onResume( )\n");
            arrayList.add("Activity A.onStop( )\n");
            arrayList.add("Activity A.onDestroy( )\n");
            showArrayList(arrayList);
            //Conf.CurrentStatus[0] = 0;
            Conf.CurrentStatus[2] = 2;
            Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
            showActivityNum_2();
        }
        if (requestCode == Activity_B_Request)
        {
            if (data == null) return;
            arrayList = MainActivity.getArrayListResult(data);
            arrayList.add("Activity B.onPause( )\n");
            arrayList.add("Activity C.onStart( )\n");
            arrayList.add("Activity C.onResume( )\n");
            arrayList.add("Activity B.onStop( )\n");
            arrayList.add("Activity B.onDestroy( )\n");
            showArrayList(arrayList);
            //Conf.CurrentStatus[1] = 0;
            Conf.CurrentStatus[2] = 2;
            Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
            showActivityNum_3();
        }
    }
    public void showActivityNum_1()
    {
        ActivityCollector.Activity_C_Num++;
        ActivityCollector.ShowActivityNum(ActivityNum);
    }
    public void showActivityNum_2()
    {
        ActivityCollector.Activity_A_Num--;
        ActivityCollector.ShowActivityNum(ActivityNum);
    }
    public void showActivityNum_3()
    {
        ActivityCollector.Activity_B_Num--;
        ActivityCollector.ShowActivityNum(ActivityNum);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__c);
        initView();
        from = getIntent().getIntExtra(Conf.From,0);
        ActivityCollector.addActivity(this);
        showActivityNum_1();
        arrayList = getIntent().getStringArrayListExtra(Conf.LifeCycleMessage);
        if (from==1)
        {
            arrayList.add("Activity A.onPause( )\n");
            arrayList.add("Activity C.onCreate( )\n");
            arrayList.add("Activity C.onStart( )\n");
            arrayList.add("Activity C.onResume( )\n");
            arrayList.add("Activity A.onStop( )\n");
            Conf.CurrentStatus[0] = 3;
            Conf.CurrentStatus[2] = 2;
            Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
        }
        if (from==2)
        {
            arrayList.add("Activity B.onPause( )\n");
            arrayList.add("Activity C.onCreate( )\n");
            arrayList.add("Activity C.onStart( )\n");
            arrayList.add("Activity C.onResume( )\n");
            arrayList.add("Activity B.onStop( )\n");
            Conf.CurrentStatus[1] = 3;
            Conf.CurrentStatus[2] = 2;
            Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
        }
        showArrayList(arrayList);
    }
    public void initView()
    {
        StartA = findViewById(R.id.StartA);
        StartB = findViewById(R.id.StartB);
        FinishC = findViewById(R.id.FinishC);
        DialogC = findViewById(R.id.Dialog);
        ActivityNum = findViewById(R.id.textView);
        LifeCycleMethod = findViewById(R.id.textView1);
        LifeCycleMethod.setMovementMethod(ScrollingMovementMethod.getInstance());
        LifeCycleMethod.setSelection(LifeCycleMethod.getText().length());
        ActivityStatus = findViewById(R.id.textView2);
        ActivityStatus.setMovementMethod(ScrollingMovementMethod.getInstance());
        StartA.setOnClickListener(this);
        StartB.setOnClickListener(this);
        FinishC.setOnClickListener(this);
        DialogC.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int ID = v.getId();
        if (ID == R.id.Dialog)
        {
            arrayList.add("Activity C.onPause( )\n");
            showArrayList(arrayList);
            Conf.CurrentStatus[2] = 1;
            Conf.showCurrentStatus(ActivityStatus,Conf.CurrentStatus);
            showDialog();
        }
        else if (ID == R.id.StartA)
        {
            Intent intent = Conf.newIntent(Activity_C.this,MainActivity.class,arrayList,3);
            startActivityForResult(intent,Activity_A_Request);
        }
        else if (ID == R.id.StartB)
        {
            Intent intent = Conf.newIntent(Activity_C.this,Activity_B.class,arrayList,3);
            startActivityForResult(intent,Activity_B_Request);
        }
        else if (ID == R.id.FinishC)
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
    public  void  showArrayList(ArrayList<String> arrayList)
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
    private void setArrayListResult(ArrayList<String> arrayListResult) {
        Intent data = new Intent();
        data.putExtra(ArrayListResult, arrayListResult);
        setResult(RESULT_OK, data);
    }
    public static ArrayList<String> getArrayListResult(Intent intent)
    {
        return intent.getStringArrayListExtra(ArrayListResult);
    }

    @Override
    protected void onPause() {
        if(ActivityCollector.isRepetition(this))
        {
            Conf.CurrentStatus[2] = 3;

        }
        else
        {
            Conf.CurrentStatus[2] = 0;
            //Log.d(TAG, "哈哈哈哈");
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }
}
