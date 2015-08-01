package com.example.mrs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pojo.DoctorPOJO;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class EditDoctorDetails extends Activity {

	EditText etUserName, etFirstName, etLastName, etSpeciality, etContact,
			etHospitalName, etRoomNo, etCity, etState, etCountry, etZip;

	Button submitBtn, resetBtn;
	long userDoctorId;
	DoctorPOJO doctorList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_doctor_details);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		doctorList = (DoctorPOJO) getIntent().getExtras().getSerializable("DoctorObj");
		userDoctorId = doctorList.getDoctorId();
		
		submitBtn = (Button) findViewById(R.id.btnSubmit);
		resetBtn = (Button) findViewById(R.id.btnReset);
		etUserName = (EditText) findViewById(R.id.etUserName);
		etFirstName = (EditText) findViewById(R.id.etFirstName);
		etLastName = (EditText) findViewById(R.id.etLastName);
		etSpeciality = (EditText) findViewById(R.id.etSpeciality);
		etContact = (EditText) findViewById(R.id.etContact);
		etHospitalName = (EditText) findViewById(R.id.etHospitlName);
		etRoomNo = (EditText) findViewById(R.id.etRoomNo);
		etCity = (EditText) findViewById(R.id.etCity);
		etState = (EditText) findViewById(R.id.etState);
		etCountry = (EditText) findViewById(R.id.etCountry);
		etZip = (EditText) findViewById(R.id.etZip);

		
		etUserName.setText(doctorList.getUserName().toString());
		etFirstName.setText(doctorList.getfName().toString());
		etLastName.setText(doctorList.getlName().toString());
		etSpeciality.setText(doctorList.getSpeciality().toString());
		etContact.setText(String.valueOf(doctorList.getContact()));
		etHospitalName.setText(doctorList.getHospitalName().toString());
		etRoomNo.setText(doctorList.getRoomNo().toString());
		etCity.setText(doctorList.getCity().toString());
		etState.setText(doctorList.getState().toString());
		etCountry.setText(doctorList.getCountry().toString());
		etZip.setText(String.valueOf(doctorList.getZip()));
		
		
		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (etFirstName.getText().toString().trim().isEmpty()
						|| etLastName.getText().toString().trim().isEmpty()
						|| etSpeciality.getText().toString().trim().isEmpty()
						|| etContact.getText().toString().trim().isEmpty()
						|| etHospitalName.getText().toString().trim().isEmpty()
						|| etRoomNo.getText().toString().trim().isEmpty()
						|| etCity.getText().toString().trim().isEmpty()
						|| etState.getText().toString().trim().isEmpty()
						|| etCountry.getText().toString().trim().isEmpty()
						|| etZip.getText().toString().trim().isEmpty()
						|| etUserName.getText().toString().trim().isEmpty()) {

					Toast.makeText(EditDoctorDetails.this,
							"No Fields can be left blank", Toast.LENGTH_SHORT)
							.show();
				} else {

					ParseQuery<ParseObject> query = ParseQuery.getQuery("DoctorDetails");
					query.whereEqualTo("doctorId", userDoctorId);
					
					ParseObject appDetailsObj = null;
					
					try {
						appDetailsObj = query.getFirst();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
										

								appDetailsObj.put("doctorId", userDoctorId);
								appDetailsObj.put("userName", etUserName
										.getText().toString());
								appDetailsObj.put("fName", etFirstName
										.getText().toString());
								appDetailsObj.put("lName", etLastName.getText()
										.toString());
								appDetailsObj.put("speciality", etSpeciality
										.getText().toString());
								appDetailsObj.put("contact", Integer
										.parseInt(etContact.getText()
												.toString()));
								appDetailsObj.put("hospitalName", etHospitalName.getText()
										.toString());
								appDetailsObj.put("roomNo", etRoomNo.getText()
										.toString());
								appDetailsObj.put("city", etCity.getText()
										.toString());
								appDetailsObj.put("state", etState.getText()
										.toString());
								appDetailsObj.put("country", etCountry
										.getText().toString());
								appDetailsObj.put("zip", Integer.parseInt(etZip
										.getText().toString()));

								try {
									appDetailsObj.save();
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
								Intent i = new Intent(EditDoctorDetails.this, SearchDoctorByManager.class);
								startActivity(i);
									finish();
				}
			}
		});
	resetBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			etUserName.setText("");
			etFirstName.setText("");
			etLastName.setText("");
			etSpeciality.setText("");
			etHospitalName.setText("");
			etRoomNo.setText("");
			etContact.setText("");
			etCity.setText("");
			etState.setText("");
			etCountry.setText("");
			etZip.setText("");
			
			
		}
	});
	}

	}

