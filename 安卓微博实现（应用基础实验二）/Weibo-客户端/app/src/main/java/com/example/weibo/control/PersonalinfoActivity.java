package com.example.weibo.control;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weibo.others.LoginService;
import com.example.weibo.basicaldata.Public;
import com.example.weibo.R;

public class PersonalinfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        final EditText mRegion = (EditText)findViewById(R.id.edit_region);
        final EditText mBirth = (EditText)findViewById(R.id.edit_birth);
        final EditText mHobby = (EditText)findViewById(R.id.edit_hobby);
        Button confirm = (Button)findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Birth=mBirth.getText().toString();
                Public.currentUser.setBirth(Birth);
                String Hobby=mHobby.getText().toString();
                Public.currentUser.setHobby(Hobby);
                String Region=mRegion.getText().toString();
                Public.currentUser.setreligion(Region);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String response = LoginService.writePersonalInfoItems(Public.currentUser.getUserName(),Public.currentUser.getBirth(),Public.currentUser.getHobby(),Public.currentUser.getreligion());
                        Looper.prepare();
                        Toast.makeText(PersonalinfoActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }).start();
                finish();
            }
        });



    }


}
