package com.example.hp.cleavepaylogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.example.hp.cleavepaylogin.R.id.txtUser;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {
    ListView listCollege;
    ProgressBar proCollageList;
   // TextView tv,tv0;
    String i;
    final String TAG=this.getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Bundle b = this.getIntent().getExtras();
        i = b.getString("username");

//        HashMap data=new HashMap();
//        data.put("username",i);
//        PostResponseAsyncTask task=new PostResponseAsyncTask(NavigationActivity.this,data, new AsyncResponse() {
//            @Override
//            public void processFinish(String s) {
//                Log.d(TAG, s);
//                if(s.contains("success")){
//
//                    AlertDialog.Builder myalert=new AlertDialog.Builder(NavigationActivity.this);
//                    myalert.setMessage("Welcome"+i).create();
//                    myalert.show();
//
//                }
//                else
//                {
//                    AlertDialog.Builder myalert=new AlertDialog.Builder(NavigationActivity.this);
//                    myalert.setMessage(s+i).create();
//                    myalert.show();
//                    FacebookSdk.sdkInitialize(getApplicationContext());
//                    LoginManager.getInstance().logOut();
//                    Intent in= new Intent(NavigationActivity.this,login.class);
//                    startActivity(in);
//                    finish();
//                    startActivity(in);
//
//                }
//            }
//
//
//        });
//        task.execute("https://cleavepay.000webhostapp.com/index.php");



      /*  btnGroup = (Button)findViewById(R.id.btnGroup);
        btnGroup.setOnClickListener(this);
        btnAddbill = (Button)findViewById(R.id.btnAddbill);
        btnAddbill.setOnClickListener(this);
        btnViewdebt = (Button)findViewById(R.id.btnViewdebt);
        btnViewdebt.setOnClickListener(this);*/
        listCollege = (ListView)findViewById(R.id.listCollege);
        proCollageList = (ProgressBar)findViewById(R.id.proCollageList);
        listCollege.setOnItemClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      //  AlertDialog.Builder myalert=new AlertDialog.Builder(NavigationActivity.this);
        //myalert.setMessage(i).create();
        //myalert.show();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        new NavigationActivity.GetHttpResponse(this).execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        TextView tv=(TextView)findViewById(R.id.tv1);
        TextView tv0=(TextView)findViewById(R.id.tv);
        Bundle b = this.getIntent().getExtras();
        final String user_mail = b.getString("username");
        final String fbuser=b.getString("fbname");
        tv.setText(user_mail);
        tv0.setText(fbuser);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle b = this.getIntent().getExtras();
        final String i = b.getString("username");

if (id == R.id.nav_home) {

        } else if (id == R.id.nav_contactus) {
    Intent in = new Intent(NavigationActivity.this, ContactsActivity.class);
    in.putExtra("username", i);
    startActivity(in);

        } else if (id == R.id.nav_logout) {
            FacebookSdk.sdkInitialize(getApplicationContext());
            LoginManager.getInstance().logOut();
            Intent in = new Intent(NavigationActivity.this,login.class);
            startActivity(in);
            finish();
        }
else if (id == R.id.btnGroup) {
    Intent in = new Intent(NavigationActivity.this, GroupActivity.class);
    in.putExtra("username", i);
    startActivity(in);
}
else if (id == R.id.btnAddbill) {
    Intent in= new Intent(NavigationActivity.this,MainActivity.class);
    in.putExtra("username", i);
    in.putExtra("btnsettle","btnBill");

    startActivity(in);
}
else if (id == R.id.btnViewdebt) {
    Intent in= new Intent(NavigationActivity.this,MainActivity.class);
    in.putExtra("username", i);
    in.putExtra("btnsettle","btnSettle");
    startActivity(in);
}
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //String txtGroupid = (String) ((TextView) view.findViewById(R.id.adapter_text_title)).getText();
       // Toast.makeText(getApplicationContext(), txtGroupid, Toast.LENGTH_LONG).show();

        Intent in = new Intent(NavigationActivity.this, LeaveActivity.class);
        in.putExtra("username", i);
        //in.putExtra("passgroupid", txtGroupid);
        Toast.makeText(getApplicationContext(),i, Toast.LENGTH_LONG).show();
        startActivity(in);

    }


    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {

        //final String i = "alkakumari42@yahoo.com";
        private Context context;
        String result;
        List<NavigationActivity.cources> collegeList;
        public GetHttpResponse(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {

            NavigationActivity.HttpService httpService = new NavigationActivity.HttpService("https://cleavepay.000webhostapp.com/userprofile.php?em="+i);
            try
            {
                httpService.ExecutePostRequest();

                if(httpService.getResponseCode() == 200)
                {
                    result = httpService.getResponse();
                    Log.d("Result", result);
                    if(result != null)
                    {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(result);

                            JSONObject object;
                            JSONArray array;
                            cources college;
                            collegeList = new ArrayList<NavigationActivity.cources>();
                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                college = new NavigationActivity.cources();
                                object = jsonArray.getJSONObject(i);

                                college.Tot_debt = object.getString("totaldebt");
                                college.email_user = object.getString("userid");
                                college.name_user=object.getString("username");
                                college.phone_user=object.getString("phoneno");
                                collegeList.add(college);
                            }
                        }
                        catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    Toast.makeText(context, httpService.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {
            proCollageList.setVisibility(View.GONE);
            listCollege.setVisibility(View.VISIBLE);
            if(collegeList != null)
            {
                NavigationActivity.ListAdapter adapter = new NavigationActivity.ListAdapter(collegeList, context);
                listCollege.setAdapter(adapter);
            }
        }
    }

    public class HttpService
    {
        private ArrayList <NameValuePair> params;
        private ArrayList <NameValuePair> headers;

        private String url;
        private int responseCode;
        private String message;
        private String response;

        public String getResponse()
        {
            return response;
        }

        public String getErrorMessage()
        {
            return message;
        }

        public int getResponseCode()
        {
            return responseCode;
        }

        public HttpService(String url)
        {
            this.url = url;
            params = new ArrayList<NameValuePair>();
            headers = new ArrayList<NameValuePair>();
        }

        public void AddParam(String name, String value)
        {
            params.add(new BasicNameValuePair(name, value));
        }

        public void AddHeader(String name, String value)
        {
            headers.add(new BasicNameValuePair(name, value));
        }

        public void ExecuteGetRequest() throws Exception
        {
            String combinedParams = "";
            if(!params.isEmpty())
            {
                combinedParams += "?";
                for(NameValuePair p : params)
                {
                    String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(),"UTF-8");
                    if(combinedParams.length() > 1)
                    {
                        combinedParams  +=  "&" + paramString;
                    }
                    else
                    {
                        combinedParams += paramString;
                    }
                }
            }

            HttpGet request = new HttpGet(url + combinedParams);
            for(NameValuePair h : headers)
            {
                request.addHeader(h.getName(), h.getValue());
            }

            executeRequest(request, url);
        }

        public void ExecutePostRequest() throws Exception
        {
            HttpPost request = new HttpPost(url);
            for(NameValuePair h : headers)
            {
                request.addHeader(h.getName(), h.getValue());
            }

            if(!params.isEmpty())
            {
                request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            }

            executeRequest(request, url);
        }

        private void executeRequest(HttpUriRequest request, String url)
        {
            HttpParams httpParameters = new BasicHttpParams();
            int timeoutConnection = 10000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            int timeoutSocket = 10000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

            HttpClient client = new DefaultHttpClient(httpParameters);
            HttpResponse httpResponse;
            try
            {
                httpResponse = client.execute(request);
                responseCode = httpResponse.getStatusLine().getStatusCode();
                message = httpResponse.getStatusLine().getReasonPhrase();

                HttpEntity entity = httpResponse.getEntity();
                if (entity != null)
                {
                    InputStream instream = entity.getContent();
                    response = convertStreamToString(instream);
                    instream.close();
                }
            }
            catch (ClientProtocolException e)
            {
                client.getConnectionManager().shutdown();
                e.printStackTrace();
            }
            catch (IOException e)
            {
                client.getConnectionManager().shutdown();
                e.printStackTrace();
            }
        }

        private String convertStreamToString(InputStream is)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try
            {
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }
    public class ListAdapter extends BaseAdapter
    {
        Context context;

        List<NavigationActivity.cources> valueList;
        public ListAdapter(List<NavigationActivity.cources> listValue, Context context)
        {
            this.context = context;
            this.valueList = listValue;
        }

        @Override
        public int getCount()
        {
            return this.valueList.size();
        }

        @Override
        public Object getItem(int position)
        {
            return this.valueList.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewItem viewItem = null;
            if(convertView == null)
            {
                viewItem = new NavigationActivity.ViewItem();
                LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                //LayoutInflater layoutInfiater = LayoutInflater.from(context);
                convertView = layoutInfiater.inflate(R.layout.main_adapter_view, null);

                viewItem.txtTitle = (TextView)convertView.findViewById(R.id.txtDebt);
                viewItem.txtDescription = (TextView)convertView.findViewById(txtUser);
                viewItem.txtPhone = (TextView)convertView.findViewById(R.id.textView5);
                viewItem.txtuser = (TextView)convertView.findViewById(R.id.txtEmail);

                convertView.setTag(viewItem);

            }
            else
            {
                viewItem = (NavigationActivity.ViewItem) convertView.getTag();
            }

            viewItem.txtTitle.setText(valueList.get(position).Tot_debt);
            viewItem.txtDescription.setText(valueList.get(position).email_user);
            viewItem.txtPhone.setText(valueList.get(position).name_user);
            viewItem.txtuser.setText(valueList.get(position).phone_user);
            return convertView;
        }
    }

    class ViewItem
    {
        TextView txtTitle;
        TextView txtDescription;
        TextView txtPhone;
        TextView txtuser;
    }
    public class cources
    {
        public String Tot_debt;
        public String email_user;
        public String name_user;
        public String phone_user;

    }



}
