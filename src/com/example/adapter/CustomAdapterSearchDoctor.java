package com.example.adapter;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.mrs.EditDoctorDetails;
import com.example.mrs.R;
import com.example.mrs.R.id;
import com.example.pojo.DoctorPOJO;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapterSearchDoctor extends ArrayAdapter<DoctorPOJO> {
	
	Context mContext;
	int mResource;
	List<DoctorPOJO> doctorList;
	private ArrayList<DoctorPOJO> arraylist;
	

	public CustomAdapterSearchDoctor(Context context, int resource,
			List<DoctorPOJO> objects) {
		super(context, resource, objects);
		this.mContext = context;
		this.mResource = resource;
		this.doctorList = objects;
		this.arraylist = new ArrayList<DoctorPOJO>();
		this.arraylist.addAll(doctorList);
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return doctorList.size();
	}


	@Override
	public DoctorPOJO getItem(int position) {
		// TODO Auto-generated method stub
		return doctorList.get(position);
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		DoctorPOJO listItem = getItem(position);

        if (convertView == null) {

        	LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        		
        	convertView = inflater.inflate(mResource, parent, false);
        
        	holder = new ViewHolder();
            holder.fName = (TextView) convertView.findViewById(R.id.textView1);
           // holder.lName = (TextView) convertView.findViewById(R.id.textView2);
          
            convertView.setTag(holder);
        } else {        	
            holder = (ViewHolder) convertView.getTag();
        }
        // Bind the data efficiently with the holder.
        holder.fName.setText(listItem.getfName() + " " + listItem.getlName());
       // holder.lName.setText(listItem.getlName());
      
        convertView.setOnClickListener(new OnClickListener() {
        	 
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(mContext, EditDoctorDetails.class);
					intent.putExtra("DoctorObj", (doctorList.get(position)));

					mContext.startActivity(intent);
					((Activity)mContext).finish();

			}
		});
         return convertView;
    }

	
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		doctorList.clear();
		if (charText.length() == 0) {
			doctorList.addAll(arraylist);
		} 
		else 
		{
			for (DoctorPOJO wp : arraylist) 
			{
				if (wp.getfName().toLowerCase(Locale.getDefault()).contains(charText)) 
				{
					doctorList.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}
	
	static class ViewHolder {

		private TextView fName,lName ;
		//private ImageView thumbnailImage;
	
	}

}
