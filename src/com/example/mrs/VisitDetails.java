package com.example.mrs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.pojo.PatientPOJO;
import com.example.pojo.VisitDetailsPOJO;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class VisitDetails extends Activity implements OnClickListener {

	
	TextView doctorNameTv,diseaseTv,medicinesTv,visitedDateTv,commentsTv;
	PatientPOJO patientObj;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visit_details);
		
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(this);
		
		doctorNameTv = (TextView)findViewById(R.id.doctorNameTv);
		diseaseTv = (TextView)findViewById(R.id.diseaseTv);
		medicinesTv = (TextView)findViewById(R.id.medicinesTv);
		visitedDateTv = (TextView)findViewById(R.id.visitedDateTv);
		commentsTv = (TextView)findViewById(R.id.commentsTv);
		
		VisitDetailsPOJO vdp = (VisitDetailsPOJO) getIntent().getExtras().getSerializable("VisitObj");
		patientObj = (PatientPOJO) this.getIntent().getSerializableExtra("PatientObj");
		
		doctorNameTv.setText(vdp.getDoctorName());
		visitedDateTv.setText(vdp.getAppointmentDate().toString());
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("MedicalRecords");
		query.whereEqualTo("appointmentId", vdp.getAppointmentId());
		
		ParseObject medRecObj = new ParseObject("MedicalRecords");
		
		try {
			medRecObj = query.getFirst();
		}catch (ParseException e) {
			e.printStackTrace();
		}		
		
		
		diseaseTv.setText(medRecObj.getString("disease"));
		medicinesTv.setText(medRecObj.getString("medication"));
		commentsTv.setText(medRecObj.getString("comments"));
				
		
	}

	@Override
	public void onClick(View v) {
		super.onBackPressed();
		
	}
}
