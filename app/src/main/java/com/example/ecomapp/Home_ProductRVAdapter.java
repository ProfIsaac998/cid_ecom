package com.example.ecomapp;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecomapp.Data.GlobalData;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

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
	public RecyclerView rv = null;
	
	public Home_ProductRVAdapter(RecyclerView _rv, JSONArray _data)
	{
		rv = _rv;
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
			/*if(i < 2)
			{
				ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)holder.item.getLayoutParams();
				params.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, GlobalData.currentActivity.getResources().getDisplayMetrics());
				Log.d("D", "Int: ");
				holder.item.setLayoutParams(params);
			}*/
			
			Picasso.get()
			.load(data.getJSONObject(i).getString("img_url"))
			.into(holder.imgProduct);
			holder.txtProdName.setText(data.getJSONObject(i).getString("name_formatted"));
			holder.txtProdPrice.setText(data.getJSONObject(i).getString("price_text"));
			holder.txtProdSold.setText(data.getJSONObject(i).getString("sold") + " sold");
			
			final int id = data.getJSONObject(i).getInt("id");
			holder.item.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Toast.makeText(GlobalData.currentActivity, "Clicked product " + id, Toast.LENGTH_SHORT).show();
				}
			});
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
