package com.example.mrs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pojo.PatientPOJO;
import com.example.pojo.VisitDetailsPOJO;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class UpdateMedicalRecordsByDoc extends Activity implements OnClickListener{

	TextView doctorNameTv,visitedDateTv;
	EditText diseaseTv,medicinesTv,commentsTv;
	PatientPOJO patientObj;
	VisitDetailsPOJO vdp;
	boolean isExist = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_medical_records_by_doc);
		
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(this);
		
		doctorNameTv = (TextView)findViewById(R.id.doctorNameTv);
		diseaseTv = (EditText)findViewById(R.id.diseaseTv);
		medicinesTv = (EditText)findViewById(R.id.medicinesTv);
		visitedDateTv = (TextView)findViewById(R.id.visitedDateTv);
		commentsTv = (EditText)findViewById(R.id.commentsTv);
	
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		vdp = (VisitDetailsPOJO) getIntent().getExtras().getSerializable("visitOBJ");
	//	patientObj = (PatientPOJO) this.getIntent().getSerializableExtra("PatientObj");*/
		
		doctorNameTv.setText(vdp.getPatientName());
		visitedDateTv.setText(vdp.getAppointmentDate().toString());
		ParseQuery<ParseObject> query1 = ParseQuery.getQuery("MedicalRecords");
		query1.whereEqualTo("appointmentId", vdp.getAppointmentId());
		ParseObject obj = null;
		try {
			obj = query1.getFirst();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (obj != null){
			isExist = true;
			diseaseTv.setText(obj.getString("disease"));
			medicinesTv.setText(obj.getString("medication"));
			commentsTv.setText(obj.getString("comments"));
		}
	}

	@Override
	public void onClick(View v) {
		if (isExist){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("MedicalRecords");
		query.whereEqualTo("appointmentId", vdp.getAppointmentId());
		
		ParseObject parseObj = null;
		
		try {
			parseObj = query.getFirst();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parseObj.put("disease", diseaseTv.getText().toString());
		parseObj.put("medication", medicinesTv.getText().toString());
		parseObj.put("patientId", vdp.getPatientId());
		parseObj.put("doctorId", vdp.getDoctorId());
		parseObj.put("comments", commentsTv.getText().toString());
		try {
			parseObj.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} else {
		
			ParseObject parseObj = new ParseObject("MedicalRecords");
			
			parseObj.put("appointmentId", vdp.getAppointmentId());
			parseObj.put("disease", diseaseTv.getText().toString());
			parseObj.put("medication", medicinesTv.getText().toString());
			parseObj.put("patientId", vdp.getPatientId());
			parseObj.put("doctorId", vdp.getDoctorId());
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
