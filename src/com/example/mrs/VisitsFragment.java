package com.example.mrs;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adapter.CustomAdapterVisitList;
import com.example.pojo.PatientPOJO;
import com.example.pojo.VisitDetailsPOJO;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
 
public class VisitsFragment extends Fragment {
	
	ListView lv;
	int patientId;
	String patientName;
	TextView doctorName,appointmentDate;
	ArrayList<VisitDetailsPOJO> visitList;
	ArrayAdapter<VisitDetailsPOJO> adapter;
	 PatientPOJO patientObj;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        final View rootView = inflater.inflate(R.layout.fragment_visits, container, false);
        lv = (ListView) rootView.findViewById(R.id.listView1);
        
        patientObj = (PatientPOJO) getActivity().getIntent().getSerializableExtra("PatientObj");
        patientId = patientObj.getPatientId();
        patientName = patientObj.getfName() + " " + patientObj.getlName();
        
        
        ParseQuery<ParseObject> query = ParseQuery.getQuery("AppointmentDetails");
        query.whereEqualTo("patientId", patientId);
        query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if(e == null && objects != null && objects.size()>0){
					visitList = new ArrayList<VisitDetailsPOJO>();
					for(ParseObject pObj : objects){
						
						VisitDetailsPOJO vdp = new VisitDetailsPOJO();
						
						vdp.setPatientName(patientName);
						vdp.setPatientId(patientId);
						vdp.setAppointmentId(pObj.getInt("appointmentId"));
						vdp.setDoctorId(pObj.getInt("doctorId"));
						vdp.setAppointmentDate(pObj.getDate("appointmentDate"));
						
					    ParseQuery<ParseObject> query = ParseQuery.getQuery("DoctorDetails");
					    query.whereEqualTo("doctorId", pObj.getInt("doctorId"));
					    try {
							vdp.setDoctorName(query.getFirst().getString("fName"));
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
							visitList.add(vdp);	
						
					}
					
				     
			    	
					adapter = new CustomAdapterVisitList(rootView.getContext(), R.layout.visit_frag_listview, visitList);
					lv.setAdapter(adapter);
				
					
				}
				
			}
		});
        
  
		
       
       
       
        lv.setAdapter(adapter);
        
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getActivity(),VisitDetails.class);
				i.putExtra("VisitObj", visitList.get(position));
				i.putExtra("PatientObj", patientObj); 
				startActivity(i);
				
				
			}
		});
        
       
        
        return rootView;
    }
}