package com.example.hp.cleavepaylogin;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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


public class GroupMembers extends Activity implements AdapterView.OnItemClickListener{

	ListView listCollege;
	ProgressBar proCollageList;
    Bundle b;
    String i;
    String btnset;
	String get_bill_id;
	String get_group_id;
	String get_amt;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_group_members);
		b = this.getIntent().getExtras();
		i = b.getString("username");
        btnset=b.getString("btnsettle");
		get_bill_id=b.getString("passBill");
		get_group_id=b.getString("passgroupid");
		get_amt=b.getString("passAmt");
		listCollege = (ListView)findViewById(R.id.listCollege);
		proCollageList = (ProgressBar)findViewById(R.id.proCollageList);

		listCollege.setOnItemClickListener(this);
		new GetHttpResponse(this).execute();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String txtuserid = (String) ((TextView) view.findViewById(R.id.adapter_text_title)).getText();
        Toast.makeText(getApplicationContext(), txtuserid, Toast.LENGTH_LONG).show();

            Intent in = new Intent(GroupMembers.this, SettleBill.class);
            in.putExtra("username", i);
            in.putExtra("passgroupid", get_group_id);
			in.putExtra("passBill",get_bill_id);
			in.putExtra("pass_debtor_id",txtuserid);
			in.putExtra("passAmt",get_amt);
			Toast.makeText(getApplicationContext(), btnset, Toast.LENGTH_LONG).show();
            startActivity(in);

    }

	private class GetHttpResponse extends AsyncTask<Void, Void, Void>
	{

		//final String i = "alkakumari42@yahoo.com";
		private Context context;
		String result;
		List<cources> collegeList;
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

			HttpService httpService = new HttpService("https://cleavepay.000webhostapp.com/GroupMembers.php?group_id="+get_group_id+"&userid="+i);
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
							collegeList = new ArrayList<cources>();
							for(int i=0; i<jsonArray.length(); i++)
							{
								college = new cources();
								object = jsonArray.getJSONObject(i);

								college.groupid = object.getString("userid");
								college.groupname = object.getString("username");

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
			ListAdapter adapter = new ListAdapter(collegeList, context);
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

		List<cources> valueList;
		public ListAdapter(List<cources> listValue, Context context)
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
				viewItem = new ViewItem();
				LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				//LayoutInflater layoutInfiater = LayoutInflater.from(context);
				convertView = layoutInfiater.inflate(R.layout.group_members_adapter_view, null);

				viewItem.txtTitle = (TextView)convertView.findViewById(R.id.adapter_text_title);
				viewItem.txtDescription = (TextView)convertView.findViewById(R.id.adapter_text_description);

				convertView.setTag(viewItem);

			}
			else
			{
				viewItem = (ViewItem) convertView.getTag();
			}

			viewItem.txtTitle.setText(valueList.get(position).groupid);
			viewItem.txtDescription.setText(valueList.get(position).groupname);
			return convertView;
		}
	}

	class ViewItem
	{
		TextView txtTitle;
		TextView txtDescription;
	}
	public class cources
	{
		public String groupid;
		public String groupname;

	}


}
