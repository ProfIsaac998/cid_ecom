package com.example.ecomapp;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Home extends AppActivity
{
	final Activity activity = this;
	
	// Loading Overlay
	ConstraintLayout loadingOverlay = null;
	TextView txtLoadingOverlay = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		// Loading Overlay
		loadingOverlay = (ConstraintLayout)findViewById(R.id.loadingOverlay);
		txtLoadingOverlay = (TextView)findViewById(R.id.txtLoadingOverlay);
		
		
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