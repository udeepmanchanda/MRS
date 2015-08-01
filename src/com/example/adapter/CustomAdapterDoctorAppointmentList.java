package com.example.adapter;


import java.util.List;

import com.example.mrs.R;
import com.example.mrs.R.id;
import com.example.pojo.VisitDetailsPOJO;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapterDoctorAppointmentList extends ArrayAdapter<VisitDetailsPOJO> {
	
	Context mContext;
	int mResource;
	List<VisitDetailsPOJO> visitList;

	

	public CustomAdapterDoctorAppointmentList(Context context, int resource,
			List<VisitDetailsPOJO> objects) {
		super(context, resource, objects);
		this.mContext = context;
		this.mResource = resource;
		this.visitList = objects;
		
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return visitList.size();
	}


	@Override
	public VisitDetailsPOJO getItem(int position) {
		// TODO Auto-generated method stub
		return visitList.get(position);
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		VisitDetailsPOJO listItem = getItem(position);
		
		 // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
        	
        // inflate the layout
        	LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        		
        	convertView = inflater.inflate(mResource, parent, false);
        	
        	// Creates a ViewHolder and store references to the two children views we want to bind data to.
          
        	holder = new ViewHolder();
            holder.patientName = (TextView) convertView.findViewById(R.id.textView1);
            holder.appointmentDate = (TextView) convertView.findViewById(R.id.textView2);
         //   holder.appointmentId = (TextView)convertView.findViewById(R.id.textView3);
            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView and the ImageView.
        	
            holder = (ViewHolder) convertView.getTag();
        }

        // Bind the data efficiently with the holder.
        holder.patientName.setText(listItem.getPatientName());
        holder.appointmentDate.setText(listItem.getAppointmentDate().toString());
      //  holder.appointmentId.setText(listItem.getAppointmentId());
      
       
        return convertView;
    }

	
	
	
	static class ViewHolder {

		private TextView patientName,appointmentDate ;
		//private ImageView thumbnailImage;
	
	}

}
