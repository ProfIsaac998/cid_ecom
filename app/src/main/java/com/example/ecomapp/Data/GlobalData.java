package com.example.ecomapp.Data;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class GlobalData
{
	public static String fcmToken = "";
	public static String accessToken = "";
	public static int userID = 0;
	public static String userName = "";
	
	public static Activity currentActivity = null;
	public static SharedPreferences prefs = null;
	
	public static void loadAppData()
	{
		prefs = PreferenceManager.getDefaultSharedPreferences(currentActivity);
		fcmToken = prefs.getString("fcmToken", "");
		accessToken = prefs.getString("accessToken", "");
		userID = prefs.getInt("userID", 0);
	}
	
	public static void saveAppData()
	{
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("fcmToken", fcmToken);
		editor.putString("accessToken", accessToken);
		editor.putInt("userID", userID);
		editor.commit();
	}
}
