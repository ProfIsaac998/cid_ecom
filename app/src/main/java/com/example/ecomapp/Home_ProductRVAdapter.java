package com.example.ecomapp;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecomapp.Data.GlobalData;

import org.json.JSONArray;

import java.util.List;

public class Home_ProductRVAdapter extends RecyclerView.Adapter<Home_ProductRVAdapter.ViewHolder>
{
	public static class ViewHolder extends RecyclerView.ViewHolder
	{
		public ConstraintLayout item;
		public ImageView imgProduct;
		public TextView txtProdName;
		public TextView txtProdPrice;
		public TextView txtProdSold;
		
		public ViewHolder(ConstraintLayout v)
		{
			super(v);
			item = v;
			imgProduct = v.findViewById(R.id.imgProduct);
			txtProdName = v.findViewById(R.id.txtProdName);
			txtProdPrice = v.findViewById(R.id.txtProdPrice);
			txtProdSold = v.findViewById(R.id.txtProdSold);
		}
	}
	
	public JSONArray data = null;
	
	public Home_ProductRVAdapter(JSONArray _data)
	{
		data = _data;
	}
	
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
	{
		return new ViewHolder((ConstraintLayout)LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_productlistview, parent, false));
	}
	
	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int i)
	{
		try
		{
			Glide.with(GlobalData.currentActivity)
			.load(data.getJSONObject(i).getString("img_url"))
			.into(holder.imgProduct);
			holder.txtProdName.setText(data.getJSONObject(i).getString("name_formatted"));
			holder.txtProdPrice.setText(data.getJSONObject(i).getString("price_text"));
			holder.txtProdSold.setText(data.getJSONObject(i).getString("sold") + " sold");
		}
		catch(Exception e)
		{
			Log.e("Home_ProductRVAdapter.onBindViewHolder", e.toString());
		}
	}
	
	@Override
	public int getItemCount()
	{
		return data.length();
	}
}
