package com.example.ecomapp.DBStruct;

import org.json.JSONObject;

public class User
{
	public int id = -1;
	public String username = "";
	public String name = "";
	
	public User()
	{
	
	}
	
	public void deserialise(JSONObject data)
	{
	
	}
	
	public static User deserialiseToObj(JSONObject data)
	{
		User ret = new User();
		ret.deserialise(data);
		
		return ret;
	}
}
