package com.example.ecomapp.Helper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.example.ecomapp.Data.GlobalData;

public class MessageUI
{
	public static void toast(String text)
	{
		toast(text, Toast.LENGTH_SHORT);
	}
	public static void toast(String text, int duration)
	{
		Toast.makeText(GlobalData.currentActivity, text, duration).show();
	}
	
	public static void snackbar(String text, int duration)
	{
		Snackbar.make(GlobalData.currentActivity.findViewById(android.R.id.content), text, duration)
				.setAction("Close", new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
					}
				})
				.show();
	}
	
	public static void alert(String title, String text)
	{
		new AlertDialog.Builder(GlobalData.currentActivity)
				.setTitle(title)
				.setMessage(text)
				.setPositiveButton("Close", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
					}
				})
				.show();
	}
}