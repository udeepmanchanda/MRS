package com.example.mrs;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pojo.PatientPOJO;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class EditPatientDetails extends Activity {

	EditText etFirstName, etLastName, etDOB, etContact, etAdd1, etAdd2, etCity,
			etState, etCountry, etZip, etUserName;
	Date dob;
	
	PatientPOJO patientObject;

	boolean isExist = false;
	long userPatientID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_patient_details);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		patientObject = (PatientPOJO) getIntent().getExtras().getSerializable("PatientObj");
		
		userPatientID = patientObject.getPatientId();
		
		Button submitBtn = (Button) findViewById(R.id.button1);
		Button resetBtn = (Button) findViewById(R.id.button2);
		etFirstName = (EditText) findViewById(R.id.editText1);
		etLastName = (EditText) findViewById(R.id.editText2);
		etDOB = (EditText) findViewById(R.id.editText3);
		etContact = (EditText) findViewById(R.id.editText4);
		etAdd1 = (EditText) findViewById(R.id.editText5);
		etAdd2 = (EditText) findViewById(R.id.editText6);
		etCity = (EditText) findViewById(R.id.editText7);
		etState = (EditText) findViewById(R.id.editText8);
		etCountry = (EditText) findViewById(R.id.editText9);
		etZip = (EditText) findViewById(R.id.editText10);
		etUserName = (EditText) findViewById(R.id.editText11);
		
		etFirstName.setText(patientObject.getfName().toString());
		etLastName.setText(patientObject.getlName().toString());
		etDOB.setText(patientObject.getDate().toString());
		etContact.setText(String.valueOf(patientObject.getContact()));
	//	int valueContact = patientObject.getContact();
		etAdd1.setText(patientObject.getAddress1());
		etAdd2.setText(patientObject.getAddress2());
		etCity.setText(patientObject.getCity());
		etState.setText(patientObject.getState());
		etCountry.setText(patientObject.getCountry());
		etZip.setText(String.valueOf(patientObject.getZip()));
		etUserName.setText(patientObject.getUserName());
			
		etDOB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new DatePickerDialog(v.getContext(), date, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();

			}
		});
		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (etFirstName.getText().toString().isEmpty()
						|| etLastName.getText().toString().isEmpty()
						|| etDOB.getText().toString().isEmpty()
						|| etContact.getText().toString().isEmpty()
						|| etAdd1.getText().toString().isEmpty()
						|| etAdd2.getText().toString().isEmpty()
						|| etCity.getText().toString().isEmpty()
						|| etState.getText().toString().isEmpty()
						|| etCountry.getText().toString().isEmpty()
						|| etZip.getText().toString().isEmpty()
						|| etUserName.getText().toString().isEmpty()) {

					Toast.makeText(EditPatientDetails.this,
							"No Fields can be left blank", Toast.LENGTH_SHORT)
							.show();
				} else {
						ParseQuery<ParseObject> query = ParseQuery.getQuery("PatientDetails");
						query.whereEqualTo("patientId", userPatientID);
						
						ParseObject parseObj = null;
						
						try {
							parseObj = query.getFirst();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						

												
						parseObj.put("fName", etFirstName.getText().toString());
						parseObj.put("lName", etLastName.getText().toString());
						SimpleDateFormat  format = new SimpleDateFormat("MM/dd/yyyy");  
						Date dob = new Date();
					    
						try {
							dob = format.parse(etDOB.getText().toString());
						} catch (java.text.ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  
					
						parseObj.put("dob", dob);
						parseObj.put("contact", Integer.parseInt(etContact.getText().toString()));
						parseObj.put("address1", etAdd1.getText().toString());
						parseObj.put("address2", etAdd2.getText().toString());
						parseObj.put("city", etCity.getText().toString());
						parseObj.put("state", etState.getText().toString());
						parseObj.put("country", etCountry.getText().toString());
						parseObj.put("zip", Integer.parseInt(etZip.getText().toString()));
						parseObj.put("userName", etUserName.getText().toString());
						try {
							parseObj.save();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					Intent i = new Intent(EditPatientDetails.this, SearchByManager.class);
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
				etDOB.setText("");
				etContact.setText("");
				etAdd1.setText("");
				etAdd2.setText("");
				etCity.setText("");
				etState.setText("");
				etCountry.setText("");
				etZip.setText("");

			}
		});
	

	}
	final Calendar myCalendar = Calendar.getInstance();
	DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			myCalendar.set(Calendar.YEAR, year);
			myCalendar.set(Calendar.MONTH, monthOfYear);
			myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

			String myFormat = "MM/dd/yyyy"; // In which you need put here
			SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

			etDOB.setText(sdf.format(myCalendar.getTime()));
		}

	};
}
