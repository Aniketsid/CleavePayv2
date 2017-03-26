package com.example.hp.cleavepaylogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class MemberActivity extends AppCompatActivity implements View.OnClickListener {

    final String TAG = this.getClass().getName();
    Button addmembutton;
    EditText name1,name2, name3, name4, name5, name6, name7, name8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addmembutton = (Button) findViewById(R.id.addbutton);
        name1 = (EditText) findViewById(R.id.mem1);
        name2 = (EditText) findViewById(R.id.mem2);
        name3 = (EditText) findViewById(R.id.mem3);
        name4 = (EditText) findViewById(R.id.mem4);
        name5 = (EditText) findViewById(R.id.mem5);
        name6 = (EditText) findViewById(R.id.mem6);
        name7 = (EditText) findViewById(R.id.mem7);
        addmembutton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        HashMap data=new HashMap();
        Bundle b = this.getIntent().getExtras();
        final String i = b.getString("username");
        final String grpid=b.getString("grpid");
        data.put("grp",grpid);
        data.put("mem1",name1.getText().toString());
        data.put("mem2",name2.getText().toString());
        data.put("mem3",name3.getText().toString());
        data.put("mem4",name4.getText().toString());
        data.put("mem5",name5.getText().toString());
        data.put("mem6",name6.getText().toString());
        data.put("mem7",name7.getText().toString());
        PostResponseAsyncTask task=new PostResponseAsyncTask(MemberActivity.this,data, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.d(TAG, s);
                if(s.contains("success")){

                    AlertDialog.Builder myalert=new AlertDialog.Builder(MemberActivity.this);
                    myalert.setMessage("Member added").create();
                    myalert.show();
                    Intent in= new Intent(MemberActivity.this,NavigationActivity.class);
                    startActivity(in);
                }
                else
                {
                    AlertDialog.Builder myalert=new AlertDialog.Builder(MemberActivity.this);
                    myalert.setMessage("check userid or enter atleast 1 valid userid").create();
                    myalert.show();

                }
            }


        });
        task.execute("https://cleavepay.000webhostapp.com/add_member.php");
    }
}
