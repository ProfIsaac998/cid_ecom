package com.example.ecomapp.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecomapp.AppActivity;
import com.example.ecomapp.Data.Config;
import com.example.ecomapp.Data.GlobalData;
import com.example.ecomapp.Login;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DB
{
	public static class Response
	{
		public int status = -1;
		public int code = -1;
		public String msg = "";
		public List<String> listMsg = new ArrayList<String>();
		public Object data = null;
	}
	
	public interface ResponseListener
	{
		void onResponse(boolean ok, Object response);
	}
	public interface OkResponseListener
	{
		void onOkResponse(Response response);
	}
	public interface ErrorResponseListener
	{
		void onErrorResponse(VolleyError error);
	}
	
	static RequestQueue queue = null;
	
	public static void init(Context context)
	{
		queue = Volley.newRequestQueue(context);
	}
	
	public static void request(final String urlPage, Map<String, String> params, final Map<String, String> headers, final ResponseListener respLis, final OkResponseListener okLis, final ErrorResponseListener errLis)
	{
		try
		{
			JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, Config.DB_URL + "/" + urlPage,
					new JSONObject(params),
					new com.android.volley.Response.Listener<JSONObject>()
					{
						@Override
						public void onResponse(JSONObject response)
						{
							Log.d("Response", response.toString());
							
							Response resp = parseResponse(response);
							
							if(resp.code == Config.DB_UNAUTH_CODE)
							{
								GlobalData.currentActivity.finish();
								GlobalData.currentActivity.startActivity(new Intent(GlobalData.currentActivity, Login.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
								
								AppActivity.unauthText = resp.msg;
							}
							else
							{
								if(okLis != null) okLis.onOkResponse(resp);
								if(respLis != null) respLis.onResponse(true, parseResponse(response));
							}
						}
					},
					new com.android.volley.Response.ErrorListener()
					{
						@Override
						public void onErrorResponse(VolleyError error)
						{
							Log.e("ResponseError", "Request: " + Config.DB_URL + "/" + urlPage + "\n" + error.toString());
							
							MessageUI.snackbar(error.toString(), 6500);
							
							if(errLis != null) errLis.onErrorResponse(error);
							if(respLis != null) respLis.onResponse(false, error);
						}
					})
			{
				@Override
				public Map<String, String> getHeaders()
				{
					return headers;
				}
			};
			
			queue.add(req);
		}
		catch(Exception e)
		{
			Log.e("DB.request", e.toString());
		}
	}
	
	public static Response parseResponse(JSONObject json)
	{
		Response res = new Response();
		
		try
		{
			res.status = json.getInt("status");
			if(!json.isNull("code")) res.code = json.getInt("code");
			if(!json.isNull("message"))
			{
				if(json.get("message") instanceof JSONArray)
				{
					JSONArray jArray = json.getJSONArray("message");
					for(int i = 0; i < jArray.length(); i++)
					{
						res.listMsg.add(jArray.getString(i));
					}
					
					res.msg = res.listMsg.get(0);
				}
				else
				{
					res.msg = json.getString("message");
					res.listMsg.add(res.msg);
				}
			}
			if(!json.isNull("data")) res.data = json.get("data");
		}
		catch(Exception e)
		{
			Log.e("DB.parseResponse", e.toString());
		}
		
		return res;
	}
	
	public static void query(String urlPage)
	{
		query(urlPage, new HashMap<String, String>(), null, null, null);
	}
	public static void query(String urlPage, Map<String, String> params)
	{
		query(urlPage, params, null, null, null);
	}
	public static void query(String urlPage, Map<String, String> params, ResponseListener respLis)
	{
		query(urlPage, params, respLis, null, null);
	}
	public static void query(String urlPage, Map<String, String> params, ResponseListener respLis, OkResponseListener okLis)
	{
		query(urlPage, params, respLis, okLis, null);
	}
	public static void query(String urlPage, Map<String, String> params, ResponseListener respLis, OkResponseListener okLis, ErrorResponseListener errLis)
	{
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		if(!TextUtils.isEmpty(GlobalData.accessToken)) headers.put("Authorization", "Bearer " + GlobalData.accessToken);
		
		request(urlPage, params, headers, respLis, okLis, errLis);
	}
	
	public static void raw(final String query, ResponseListener respLis)
	{
		query(Config.DB_RAW_URL, new HashMap<String, String>()
		{{
			put("query", query);
		}}, respLis);
	}
	
}
