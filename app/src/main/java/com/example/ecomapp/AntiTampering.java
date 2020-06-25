package com.example.ecomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecomapp.Data.Config;
import com.example.ecomapp.Data.GlobalData;
import com.example.ecomapp.Helper.DB;
import com.example.ecomapp.Helper.MessageUI;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Map;

public class AntiTampering extends AppCompatActivity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anti_tampering);
		
		try
		{
			JSONObject params = new JSONObject();
			params.put("merchant", "m12345");
			params.put("product", "product12345");
			params.put("amount", "12.99");
			
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			String hash = Base64.encodeToString(digest.digest(new String("m12345" + "product12345" + "12.99").getBytes()), Base64.DEFAULT);
			params.put("hash", hash);
			
			Log.d("AntiTamperingHash", "Hash: " + hash);
			
			RequestQueue queue = null;
			queue = Volley.newRequestQueue(this);
			JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, "PAYMENT GATEWAY URL HERE",
					params,
					new com.android.volley.Response.Listener<JSONObject>()
					{
						@Override
						public void onResponse(JSONObject response)
						{
							Log.d("Response", response.toString());
						}
					},
					new com.android.volley.Response.ErrorListener()
					{
						@Override
						public void onErrorResponse(VolleyError error)
						{
							Log.e("ResponseError", error.toString());
						}
					});
			
			queue.add(req);
		}
		catch(Exception e)
		{
			Log.e("Error", e.toString());
		}
	}
}