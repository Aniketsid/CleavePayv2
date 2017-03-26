package com.example.hp.cleavepaylogin;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class SettleBill extends AppCompatActivity implements View.OnClickListener {
    final String TAG = this.getClass().getName();
    Button updatebtn;
   // EditText debtorname,amount;
    String i;
    String grpid;
    String billid;
    String get_debtor_id;
    String get_amt;
    TextView txtgroupid,txtbillid,txtdebtor,txtamt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle_bill);
        updatebtn=(Button) findViewById(R.id.update_btn);
      //  groupid=(EditText) findViewById(R.id.group_id);
      //  billid=(EditText) findViewById(R.id.bill_id);
      //  debtorname=(EditText) findViewById(R.id.debtor_name);
       // amount=(EditText) findViewById(R.id.amount);
        txtgroupid=(TextView) findViewById(R.id.txtgroupid);
        txtbillid=(TextView) findViewById(R.id.txtbillid);
        txtdebtor=(TextView) findViewById(R.id.txtdebtor);
        txtamt=(TextView) findViewById(R.id.txtamt);

        Bundle b = this.getIntent().getExtras();
        i = b.getString("username");
         grpid=b.getString("passgroupid");
       billid=b.getString("passBill");
        get_debtor_id=b.getString("pass_debtor_id");
         get_amt=b.getString("passAmt");
        updatebtn.setOnClickListener(this);

        txtgroupid.setText(grpid);
        txtbillid.setText(billid);
        txtdebtor.setText(get_debtor_id);
        txtamt.setText(get_amt);

    }


    @Override
    public void onClick(View v) {

        HashMap data=new HashMap();


        data.put("group_id",grpid);
        data.put("bill_id",billid);
        data.put("debtor_name",get_debtor_id);
        data.put("payee_name",i);
        data.put("amount",get_amt);
        PostResponseAsyncTask task=new PostResponseAsyncTask(SettleBill.this,data, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.d(TAG, s);
                if(s.contains("success")){
                    AlertDialog.Builder myalert=new AlertDialog.Builder(SettleBill.this);
                    myalert.setMessage("Debt Settled Successfully").create();
                    myalert.show();
                }
                else
                {
                    AlertDialog.Builder myalert=new AlertDialog.Builder(SettleBill.this);
                    myalert.setMessage("Debt already cleared").create();
                    myalert.show();

                }
            }


        });
        task.execute("https://cleavepay.000webhostapp.com/Settle_Bill.php/");

    }
}
