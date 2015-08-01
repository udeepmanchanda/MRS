package com.example.mrs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class AddDoctorActivity extends Activity {

	EditText etUserName, etFirstName, etLastName, etSpeciality, etContact, etHospitalName,
			etRoomNo, etCity, etState, etCountry, etZip;

	Button submitBtn, resetBtn;
	long userDoctorId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_doctor);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

					Toast.makeText(AddDoctorActivity.this,
							"No Fields can be left blank", Toast.LENGTH_SHORT)
							.show();
				} else {

					long userId = 0L;
					ParseQuery<ParseObject> query1 = ParseQuery
							.getQuery("_User");
					query1.orderByDescending("userId");
					try {
						userId = query1.getFirst().getInt("userId") + 1;
						
						 userDoctorId = userId;
					} catch (ParseException e1) {

						e1.printStackTrace();
					}
					ParseUser userObj = new ParseUser();
					userObj.setUsername(etUserName.getText().toString());
					userObj.setPassword(etUserName.getText().toString());
					userObj.put("Role", "Doctor");
					userObj.put("userId", userId);
					userObj.signUpInBackground(new SignUpCallback() {

						@Override
						public void done(ParseException e) {
							if (e == null) {

							//	long doctorId = userDoctorId;
								
								ParseObject appDetailsObj = new ParseObject(
										"DoctorDetails");
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

								appDetailsObj
										.saveInBackground(new SaveCallback() {

											@Override
											public void done(ParseException e) {
												if (e == null) {
													Toast.makeText(
															AddDoctorActivity.this,
															"Doctor added successfully!",
															Toast.LENGTH_SHORT)
															.show();
												/*	Intent i = new Intent(
															AddDoctorActivity.this,
															SearchActivity.class);
													startActivity(i);*/
													finish();
												} else {
													Toast.makeText(
															AddDoctorActivity.this,
															e.getLocalizedMessage(),
															Toast.LENGTH_SHORT)
															.show();
												}

											}
										});

							} else {
								e.printStackTrace();
							}

						}
					});

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
