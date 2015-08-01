package com.example.mrs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pojo.DoctorPOJO;
import com.example.pojo.VisitDetailsPOJO;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class UpdateDoctorByManager extends Activity implements OnClickListener{

	TextView doctorNameTv;
	EditText roomNoTv,contactTv,commentsTv,specialityTv;
	DoctorPOJO doctorObj;
	VisitDetailsPOJO vdp;
	boolean isExist = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_doctor_by_manager);
		
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(this);
		
		doctorNameTv = (TextView)findViewById(R.id.doctorNameTv);
		roomNoTv = (EditText)findViewById(R.id.roomNo);
		contactTv = (EditText)findViewById(R.id.contact);
		specialityTv = (EditText)findViewById(R.id.speciality);
		commentsTv = (EditText)findViewById(R.id.commentsTv);
		
		
	//	vdp = (VisitDetailsPOJO) getIntent().getExtras().getSerializable("visitOBJ");
		doctorObj = (DoctorPOJO) this.getIntent().getSerializableExtra("DoctorObj");
		
		doctorNameTv.setText(doctorObj.getfName().toString());
		roomNoTv.setText(doctorObj.getRoomNo());
		specialityTv.setText(doctorObj.getSpeciality().toString());
		contactTv.setText(doctorObj.getContact());
	//	commentsTv.setText(doctorObj..toString());
		ParseQuery<ParseObject> query1 = ParseQuery.getQuery("DoctorDetails");
		query1.whereEqualTo("doctorId", vdp.getAppointmentId());
		ParseObject obj = null;
		try {
			obj = query1.getFirst();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (obj != null){
			isExist = true;
			doctorNameTv.setText(obj.getString("fName"));
			specialityTv.setText(obj.getString("speciality"));
//			commentsTv.setText(obj.getString("comments"));
			roomNoTv.setText(obj.getString("roomNo"));
			contactTv.setText(obj.getString("contact"));
		}
	}

	@Override
	public void onClick(View v) {
		if (isExist){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("DoctorDetails");
		query.whereEqualTo("doctorId", vdp.getAppointmentId());
		
		ParseObject parseObj = null;
		
		try {
			parseObj = query.getFirst();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parseObj.put("fName", doctorNameTv.getText().toString());
		parseObj.put("speciality", specialityTv.getText().toString());
		parseObj.put("roomNo", roomNoTv.getText().toString());
		parseObj.put("contact", contactTv.getText().toString());
		parseObj.put("comments", commentsTv.getText().toString());
		try {
			parseObj.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} else {
		
			ParseObject parseObj = new ParseObject("DoctorDetails");
			
			parseObj.put("doctorId", vdp.getAppointmentId());
			parseObj.put("fName", doctorNameTv.getText().toString());
			parseObj.put("speciality", specialityTv.getText().toString());
			parseObj.put("roomNo", roomNoTv.getText().toString());
			parseObj.put("contact", contactTv.getText().toString());
			parseObj.put("comments", commentsTv.getText().toString());
			try {
				parseObj.save();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		finish();
		
	}
}
