package com.gabzil.focuslite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Speed extends Fragment {
    TextView tv;
    ProgressBar pBar;
    int pStatus;
    private Handler handler = new Handler();
    EditText speed;
    Button Go;
    Double Speed;

    public Speed() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_speed, container, false);
        tv = (TextView) v.findViewById(R.id.textView1);
        pBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        speed = (EditText) v.findViewById(R.id.speed);

        MyOpenHelper db = new MyOpenHelper(getActivity());
        String SpeedValue = db.getInputs();
        speed.setText(SpeedValue);
        Go = (Button) v.findViewById(R.id.go);

        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Validation()){
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    pStatus = 0;
                    pBar.setSecondaryProgress(0);
                    Speed = Double.valueOf(speed.getText().toString().trim());

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            while (pStatus <= Speed) {
                                handler.post(new Runnable() {

                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub
                                        if (pStatus > 100)
                                            pBar.setSecondaryProgress(pStatus-100);
                                        else
                                            pBar.setProgress(pStatus);

                                        tv.setText(Speed + " KM/HR");
                                        tv.setTextSize(15);
                                    }
                                });
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                pStatus++;
                            }
                        }
                    }).start();
                }
            }
        });
        return v;
    }

    private boolean Validation()
    {
        boolean error=false;
        String strmsg= "Please Enter ";

        if(speed.getText().toString().trim().length() == 0)
        {
            strmsg += ", Speed";
            error = true;
        }

        if(error == true)
        {
            String replacedString =strmsg.replace("Please Enter ,", "Please Enter ");
            ShowAlert(replacedString);
        }
        return  error;
    }

    public void ShowAlert(String msg){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(msg);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // cancel the alert box and put a Toast to the user
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }


}