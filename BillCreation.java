package com.example.hp.cleavepaylogin;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class BillCreation extends AppCompatActivity implements View.OnClickListener{

    final String TAG = this.getClass().getName();
    Button createbutton;
    EditText name, billid, expense, amount, group, billdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_creation);
        createbutton = (Button) findViewById(R.id.create_button);
        name = (EditText) findViewById(R.id.bill_name);
        billid = (EditText) findViewById(R.id.bill_id);
        group = (EditText) findViewById(R.id.group_id);
        expense = (EditText) findViewById(R.id.expense_id);
        billdate = (EditText) findViewById(R.id.dated);
        amount = (EditText) findViewById(R.id.amount_id);
        //payee = (EditText) findViewById(R.id.payee_id);
        createbutton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        HashMap data=new HashMap();
        Bundle b = this.getIntent().getExtras();
        final String i = b.getString("username");
        final String getGroupid=b.getString("passgroupid");
        data.put("billname",name.getText().toString());
        data.put("billid",billid.getText().toString());
        data.put("groupid",getGroupid);
        data.put("expensetype",expense.getText().toString());
        data.put("billcreated",billdate.getText().toString());
        data.put("amount",amount.getText().toString());
        data.put("payeename",i);
        PostResponseAsyncTask task=new PostResponseAsyncTask(BillCreation.this,data, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.d(TAG, s);
                if(s.contains("success")){
                    AlertDialog.Builder myalert=new AlertDialog.Builder(BillCreation.this);
                    myalert.setMessage("bill added successfully").create();
                    myalert.show();
                }
                else
                {
                    AlertDialog.Builder myalert=new AlertDialog.Builder(BillCreation.this);
                    myalert.setMessage("enter valid group id").create();
                    myalert.show();

                }
            }


        });
        task.execute("https://cleavepay.000webhostapp.com/payment.php");
    }
}
