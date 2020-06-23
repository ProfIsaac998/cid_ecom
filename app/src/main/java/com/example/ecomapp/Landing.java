package com.example.ecomapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ecomapp.Data.Config;
import com.example.ecomapp.Data.GlobalData;
import com.example.ecomapp.Helper.Crypt;
import com.example.ecomapp.Helper.DB;
import com.example.ecomapp.Helper.DB.Response;
import com.example.ecomapp.Helper.DB.ResponseListener;

import org.json.JSONObject;

import java.util.HashMap;

public class Landing extends AppActivity
{
	public Activity activity = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing);
		
		// Loading
		DB.init(this, Config.DB_KEY);
		
		// Test
		/*DB.query("login", new ResponseListener()
		{
			@Override
			public void onResponse(boolean ok, Object response)
			{
				if(ok)
				{
					Response resp = (Response)response;
					//Log.d("Response", response.msg);
				}
				
				startActivity(new Intent(activity, Login.class));
			}
		}, new HashMap<String, String>()
		{{
			put("username", "");
			put("password", "");
		}};*/
		startActivity(new Intent(activity, Login.class));
	}
}
