package com.example.ecomapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecomapp.Data.GlobalData;
import com.example.ecomapp.Helper.DB;
import com.example.ecomapp.Helper.MessageUI;

import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppActivity
{
	final Activity activity = this;
	
	// Loading Overlay
	ConstraintLayout loadingOverlay = null;
	TextView txtLoadingOverlay = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// Loading Overlay
		loadingOverlay = (ConstraintLayout)findViewById(R.id.loadingOverlay);
		txtLoadingOverlay = (TextView)findViewById(R.id.txtLoadingOverlay);
		
		if(!TextUtils.isEmpty(unauthText))
		{
			MessageUI.snackbar(unauthText, 5000);
			unauthText = null;
		}
	}
	
	public void btnLoginClicked(View v)
	{
		loadingOverlayShow("Logging in...");
		
		final EditText fldUsername = (EditText)findViewById(R.id.fldUsername);
		final EditText fldPassword = (EditText)findViewById(R.id.fldPassword);
		
		DB.query("login", new HashMap<String, String>()
		{{
			put("username", fldUsername.getText().toString());
			put("password", fldPassword.getText().toString());
		}}, new DB.ResponseListener()
		{
			@Override
			public void onResponse(boolean ok, Object response)
			{
				loadingOverlayHide();
				
				if(ok)
				{
					DB.Response resp = (DB.Response) response;
					
					if(resp.status == 1)
					{
						try
						{
							//GlobalData.user = User.serialiseToObj((JSONObject)response.data);
							GlobalData.accessToken = ((JSONObject)resp.data).getString("access_token");
							MessageUI.toast("Logged in", Toast.LENGTH_LONG);
							
							activity.finish();
							startActivity(new Intent(activity, Home.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
						}
						catch(Exception e)
						{
							Log.e("btnLoginClicked.onResponse", e.toString());
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
			}
		});
	}
	
	public void btnRegisterClicked(View v)
	{
		startActivity(new Intent(this, Register.class));
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
