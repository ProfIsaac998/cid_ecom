package com.example.ecomapp;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Register extends AppActivity
{
	final Context activityCtx = this;
	
	// Loading Overlay
	ConstraintLayout loadingOverlay = null;
	TextView txtLoadingOverlay = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		// Loading Overlay
		loadingOverlay = (ConstraintLayout)findViewById(R.id.loadingOverlay);
		txtLoadingOverlay = (TextView)findViewById(R.id.txtLoadingOverlay);
	}
	
	public void btnRegisterClicked(View v)
	{
		loadingOverlayShow("Registering...");
	}
	
	public void btnLoginClicked(View v)
	{
		finish();
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
