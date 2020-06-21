package com.example.ecomapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ecomapp.Data.GlobalData;

public class AppActivity extends AppCompatActivity
{
	public static String unauthText = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		GlobalData.currentActivity = this;
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		GlobalData.currentActivity = this;
	}
}
