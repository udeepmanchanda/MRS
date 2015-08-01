
package com.example.adapter;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.mrs.DoctorCheckPatientHistory;
import com.example.mrs.R;
import com.example.mrs.R.drawable;
import com.example.mrs.R.id;
import com.example.pojo.PatientPOJO;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapterDoctorPatientSearch extends ArrayAdapter<PatientPOJO> {
	
	Context mContext;
	int mResource;
	List<PatientPOJO> patientList;
	private ArrayList<PatientPOJO> arraylist;
	

	public CustomAdapterDoctorPatientSearch(Context context, int resource,
			List<PatientPOJO> objects) {
		super(context, resource, objects);
		this.mContext = context;
		this.mResource = resource;
		this.patientList = objects;
		this.arraylist = new ArrayList<PatientPOJO>();
		this.arraylist.addAll(patientList);
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return patientList.size();
	}


	@Override
	public PatientPOJO getItem(int position) {
		// TODO Auto-generated method stub
		return patientList.get(position);
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		PatientPOJO listItem = getItem(position);
		
		 // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
        	
        // inflate the layout
        	LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        		
        	convertView = inflater.inflate(mResource, parent, false);
        	
        	// Creates a ViewHolder and store references to the two children views we want to bind data to.
          
        	holder = new ViewHolder();
            holder.fName = (TextView) convertView.findViewById(R.id.textView1);
       //     holder.lName = (TextView) convertView.findViewById(R.id.textView2);
			holder.thumbnailImage =(ImageView) convertView.findViewById(R.id.imageView1);
          
            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView and the ImageView.
        	
            holder = (ViewHolder) convertView.getTag();
        }

        // Bind the data efficiently with the holder.
        holder.fName.setText(listItem.getfName() + " " + listItem.getlName());
    //    holder.lName.setText(listItem.getlName());
        
        if(listItem.getPicUrl() != null){
	        Picasso.with(mContext).load(listItem.getPicUrl()).resize(100, 100).into(holder.thumbnailImage);
	  }else{
	       Picasso.with(mContext).load(R.drawable.photo_not_found).resize(100, 100).into(holder.thumbnailImage);
	   }
      
        convertView.setOnClickListener(new OnClickListener() {
        	 
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(mContext, DoctorCheckPatientHistory.class);
				
				intent.putExtra("PatientObj",(patientList.get(position)));
				
				mContext.startActivity(intent);
			
			}
		});
 

      

        //convertView.setTag(photoList.get(position));
        
        
        
        return convertView;
    }

	
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		patientList.clear();
		if (charText.length() == 0) {
			patientList.addAll(arraylist);
		} 
		else 
		{
			for (PatientPOJO wp : arraylist) 
			{
				if (wp.getfName().toLowerCase(Locale.getDefault()).contains(charText)) 
				{
					patientList.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}
	
	static class ViewHolder {

		private TextView fName,lName ;
		private ImageView thumbnailImage;
	
	}

}
