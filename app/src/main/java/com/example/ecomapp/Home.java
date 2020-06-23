package com.example.ecomapp;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecomapp.Data.GlobalData;
import com.example.ecomapp.Helper.DB;
import com.example.ecomapp.Helper.MessageUI;

import org.json.JSONArray;
import org.json.JSONObject;

public class Home extends AppActivity
{
	final Activity activity = this;
	
	// Loading Overlay
	ConstraintLayout loadingOverlay = null;
	TextView txtLoadingOverlay = null;
	
	Home_ProductRVAdapter prodListAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		// Loading Overlay
		loadingOverlay = (ConstraintLayout)findViewById(R.id.loadingOverlay);
		txtLoadingOverlay = (TextView)findViewById(R.id.txtLoadingOverlay);
		
		loadingOverlayShow();
		DB.query("user/detail", new DB.ResponseListener()
		{
			@Override
			public void onResponse(boolean ok, Object response)
			{
				if(ok)
				{
					DB.Response resp = (DB.Response)response;
					
					if(resp.status == 1)
					{
						try
						{
							GlobalData.userID = ((JSONObject)resp.data).getInt("id");
							GlobalData.userName = ((JSONObject)resp.data).getString("name");
							
							loadProductList();
						}
						catch(Exception e)
						{
							Log.e("Home.onCreate.onResponse", e.toString());
						}
					}
					else
					{
						loadingOverlayHide();
						if(resp.listMsg.size() == 1)
							MessageUI.toast(resp.msg, Toast.LENGTH_LONG);
						else
						{
							String msg = "";
							for(String m:resp.listMsg)
							{
								msg += m + "\n";
							}
							
							MessageUI.alert("Validation Error", msg);
						}
					}
				}
				else
				{
					loadingOverlayHide();
				}
			}
		});
	}
	
	private void loadProductList()
	{
		DB.query("home/list_products", new DB.ResponseListener()
		{
			@Override
			public void onResponse(boolean ok, Object response)
			{
				if(ok)
				{
					DB.Response resp = (DB.Response)response;
					
					if(resp.status == 1)
					{
						try
						{
							RecyclerView rv = findViewById(R.id.prodListView);
							prodListAdapter = new Home_ProductRVAdapter((JSONArray)resp.data);
							rv.setAdapter(prodListAdapter);
						}
						catch(Exception e)
						{
							Log.e("Home.loadProductList.onResponse", e.toString());
						}
					}
					else
					{
						if(resp.listMsg.size() == 1)
							MessageUI.toast(resp.msg, Toast.LENGTH_LONG);
						else
						{
							String msg = "";
							for(String m:resp.listMsg)
							{
								msg += m + "\n";
							}
							
							MessageUI.alert("Validation Error", msg);
						}
					}
				}
				loadingOverlayHide();
				MessageUI.toast("Welcome, " + GlobalData.userName, Toast.LENGTH_LONG);
			}
		});
	}
	
	// Loading Overlay
	public void loadingOverlayShow()
	{
		loadingOverlayShow(null);
	}
	public void loadingOverlayShow(String msg)
	{
		if(loadingOverlay != null)
		{
			if(msg != null)
				txtLoadingOverlay.setText(msg);
			else
				txtLoadingOverlay.setText("");
			loadingOverlay.setVisibility(View.VISIBLE);
		}
	}
	public void loadingOverlayHide()
	{
		if(loadingOverlay != null) loadingOverlay.setVisibility(View.GONE);
		txtLoadingOverlay.setText("");
	}
}