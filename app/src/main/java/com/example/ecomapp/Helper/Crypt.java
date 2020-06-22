package com.example.ecomapp.Helper;

import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypt
{
	public static String[] encrypt(String key, String plainText)
	{
		String[] ret = {"", ""};
		
		try
		{
			SecretKeySpec keySpec = new SecretKeySpec(Base64.decode(key, Base64.DEFAULT), "AES");
			
			Cipher cipher = Cipher.getInstance("AES_256/CBC/PKCS7PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			
			byte[] cipherText = cipher.doFinal(plainText.getBytes());
			byte[] iv = cipher.getIV();
			
			ret[0] = Base64.encodeToString(iv, Base64.DEFAULT);
			ret[1] = Base64.encodeToString(cipherText, Base64.DEFAULT);
		}
		catch(Exception e)
		{
			Log.e("Crypt.encrypt", e.toString());
		}
		
		return ret;
	}
	
	public static String decrypt(String key, String iv, String cipherText)
	{
		String ret = "";
		
		try
		{
			SecretKeySpec keySpec = new SecretKeySpec(Base64.decode(key, Base64.DEFAULT), "AES");
			//JSONObject data = new JSONObject(new String(Base64.decode(text, Base64.DEFAULT)));
			
			Cipher cipher = Cipher.getInstance("AES_256/CBC/PKCS7PADDING");
			cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(Base64.decode(iv, Base64.DEFAULT)));
			
			byte[] plainText = cipher.doFinal(Base64.decode(cipherText, Base64.DEFAULT));
			
			ret = new String(plainText);
		}
		catch(Exception e)
		{
			Log.e("Crypt.decrypt", e.toString());
		}
		
		return ret;
	}
}
