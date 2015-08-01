package com.example.mrs;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.CustomAdapterVisitList;
import com.example.pojo.PatientPOJO;
import com.example.pojo.VisitDetailsPOJO;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class DoctorCheckPatientHistory extends Activity {
	
	ListView lv;
	int patientId;
	String patientName;
	TextView doctorName,appointmentDate;
	ArrayList<VisitDetailsPOJO> visitList;
	ArrayAdapter<VisitDetailsPOJO> adapter;
	PatientPOJO patientObj;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_check_patient_history);
		
		
		 lv = (ListView) this.findViewById(R.id.listView1);
	        
	        patientObj = (PatientPOJO) this.getIntent().getSerializableExtra("PatientObj");
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
						
					     
				    	
						adapter = new CustomAdapterVisitList(DoctorCheckPatientHistory.this, R.layout.visit_frag_listview, visitList);
						lv.setAdapter(adapter);
					
						
					}
					
				}
			});
	        
	        
	        lv.setAdapter(adapter);
	        
	        lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent i = new Intent(DoctorCheckPatientHistory.this,VisitDetails.class);
					i.putExtra("VisitObj", visitList.get(position));
					i.putExtra("PatientObj", patientObj); 
					startActivity(i);
					
					
				}
			});
	        
	        
	}

	
}
