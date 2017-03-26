package com.example.hp.cleavepaylogin;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class GroupActivity extends AppCompatActivity implements View.OnClickListener {

    final String TAG=this.getClass().getName();

    Button btncreate;
    EditText etGpname;
    EditText etGpid;
   // EditText etGpadmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        btncreate=(Button)findViewById(R.id.btncreate);
        etGpname=(EditText)findViewById(R.id.etGpname);
        etGpid=(EditText)findViewById(R.id.etgpid);
        btncreate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Bundle b = this.getIntent().getExtras();
        final String i = b.getString("username");
        HashMap data=new HashMap();
        data.put("grpname",etGpname.getText().toString());
        data.put("grpadmin",i);
        data.put("grpid",etGpid.getText().toString());
        PostResponseAsyncTask task=new PostResponseAsyncTask(GroupActivity.this,data, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.d(TAG, s);
                if(s.contains("success")){
                    Intent in= new Intent(GroupActivity.this,MemberActivity.class);
                    in.putExtra("grpid",etGpid.getText().toString());
                    in.putExtra("username",i);
                    startActivity(in);
                }
                else
                {
                    AlertDialog.Builder myalert=new AlertDialog.Builder(GroupActivity.this);
                    myalert.setMessage(s+i).create();
                    myalert.show();

                }
            }


        });
        task.execute("https://cleavepay.000webhostapp.com/groupcreate.php/");
    }
    }
