package com.bignerdranch.android.activitylifecycle;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by 杨文卓 on 2018/10/28.
 */

public class SimpleDialog extends DialogFragment {

    private MyDialogFragment_Listener myDialogFragment_Listener;
    public interface MyDialogFragment_Listener {
        void getDataFrom_DialogFragment(Boolean isOver);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       try {
           myDialogFragment_Listener = (MyDialogFragment_Listener)activity;
       }catch (ClassCastException e )
       {
       }
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog,container);
        Button button = view.findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myDialogFragment_Listener!=null)
                    myDialogFragment_Listener.getDataFrom_DialogFragment(true);
                dismiss();
            }
        });
        return  view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) { super.onActivityCreated(savedInstanceState);

        WindowManager.LayoutParams attributes = getDialog().getWindow().getAttributes();
        attributes.width = 800;
        attributes.height = -2;
        getDialog().getWindow().setAttributes(attributes);
    }

}
