package com.example.hp.cleavepaylogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


public class login extends AppCompatActivity  {
   // Button DWEUW;
    LoginButton loginButton;
    TextView welcome;
    CallbackManager callbackManager;
    private static final String TAG = "login";
    //String emailid, gender, bday, username;
    //Button create_button;
   // EditText etuser;
    //Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
      //  create_button = (Button)findViewById(R.id.create_bill);
        //create_button.setOnClickListener(this);
        //login=(Button)findViewById(R.id.btnLogin);
        //login.setOnClickListener(this);
       // DWEUW=(Button)findViewById(R.id.DWEUW);     //button to next page created
       // etuser=(EditText)findViewById(R.id.etuser);
      //  DWEUW.setOnClickListener(this);
        loginButton = (LoginButton) findViewById(R.id.fb_login_btn);
        loginButton.setReadPermissions("email");
        welcome = (TextView) findViewById(R.id.welcome);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //  welcome.setText("WELCOME");
                //  String userId=loginResult.getAccessToken().getUserId();
                //   welcome.setText("WELCOME"+userId);


                ProgressDialog progressDialog = new ProgressDialog(login.this);
                progressDialog.setMessage("Processing data...");
                progressDialog.show();
                String accessToken = loginResult.getAccessToken().getToken();
                Log.i("accessToken", accessToken);


                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("login", response.toString());
                        // Get facebook data from login
                        Bundle bFacebookData = getFacebookData(object);
                        final String emailid=bFacebookData.getString("email");
                        final String fbuser=bFacebookData.getString("first_name");
                        final String fbuserl=bFacebookData.getString("last_name");
                        //welcome.setText(emailid);
                        HashMap data=new HashMap();
                        data.put("username",emailid);
                        PostResponseAsyncTask task=new PostResponseAsyncTask(login.this,data, new AsyncResponse() {
                            @Override
                            public void processFinish(String s) {
                                Log.d(TAG, s);
                                if(s.contains("success")){

                                  //  AlertDialog.Builder myalert=new AlertDialog.Builder(login.this);
                                  //  myalert.setMessage("Welcome").create();
                                  //  myalert.show();
                                    Intent in= new Intent(login.this,NavigationActivity.class);

                                    in.putExtra("username",emailid);
                                    in.putExtra("fbname",fbuser+" "+fbuserl);
                                    startActivity(in);
                                    finish();

                                }
                                else
                                {
                                    //AlertDialog.Builder myalert=new AlertDialog.Builder(login.this);
                                  //  myalert.setMessage(s+i).create();
                                    //myalert.show();
                                    FacebookSdk.sdkInitialize(getApplicationContext());
                                    LoginManager.getInstance().logOut();
                                   // Intent in= new Intent(NavigationActivity.this,login.class);
                                    //startActivity(in);
                                    finish();
                                   // startActivity(in);

                                }
                            }


                        });
                        task.execute("https://cleavepay.000webhostapp.com/index.php");

//                        Intent in= new Intent(login.this,NavigationActivity.class);
//
//                        in.putExtra("username",emailid);
//                        in.putExtra("fbname",fbuser+" "+fbuserl);
//                        startActivity(in);

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "first_name, last_name, email"); //Parameters ask facebook
                request.setParameters(parameters);
                request.executeAsync();
                //String emailid=parameters.getString("email");
                //welcome.setText(emailid);
                //Intent in= new Intent(login.this,NavigationActivity.class);
                //in.putExtra("username",emailid);
                //startActivity(in);
            }

            @Override
            public void onCancel() {
                String str="LOGIN CANCELLED";
                welcome.setText(str);
            }

            @Override
            public void onError(FacebookException exception) {
                String str="ERROR";
                welcome.setText(str);
                Log.v("login", exception.getCause().toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    protected Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email")) {
                bundle.putString("email", object.getString("email"));

            }
            return bundle;
        } catch (JSONException e) {
            Log.d(TAG, "Error parsing JSON");
            return null;
        }
    }



 /*   @Override
  public void onClick(View v) {
        if(v.getId()==R.id.DWEUW) {
            Intent in = new Intent(login.this, GroupActivity.class);
            in.putExtra("username", etuser.getText().toString());
            startActivity(in);
        }
        if(v.getId()==R.id.create_bill)
        {
            Intent in= new Intent(login.this,BillCreation.class);
            startActivity(in);
        }
        if(v.getId()==R.id.btnLogin){
            Intent in= new Intent(login.this,NavigationActivity.class);
            startActivity(in);
        }

    }*/


}

/*

public class login extends ActionBarActivity {

    private CallbackManager callbackManager;
    String emailid, gender, bday, username;
    private LoginButton loginButton;

    ProfilePictureView profilePictureView;

    TextView info;
    private AccessTokenTracker accessTokenTracker;

    private ProgressDialog pDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        loginButton = (LoginButton) findViewById(R.id.fb_login_btn);

        loginButton.setReadPermissions(Arrays
                .asList("public_profile, email, user_birthday, user_friends"));

        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(login.this);
                        alertDialogBuilder.setMessage(emailid).create();
                        alertDialogBuilder.show();
                        new fblogin().execute(loginResult.getAccessToken());


                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException e) {

                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    class fblogin extends AsyncTask<AccessToken, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(login.this);
            pDialog.setMessage("wait.");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(AccessToken... params) {
            GraphRequest request = GraphRequest.newMeRequest(params[0],
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object,
                                                GraphResponse response) {

                            Log.v("LoginActivity", response.toString());

                            try {

                                username = object.getString("first_name");

                                emailid = object.getString("email");

                                gender = object.getString("gender");

                                bday = object.getString("birthday");

                            } catch (JSONException e) {
                                // TODO Auto-generated catch
                                // block
                                e.printStackTrace();
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields",
                    "id,first_name,email,gender,birthday");
            request.setParameters(parameters);
            request.executeAndWait();

            return null;
        }

        protected void onPostExecute(String file_url) {

            pDialog.dismiss();

        }

    }


}*/
