package com.example.mrs;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pojo.CapturePatientExistingDataPOJO;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.squareup.picasso.Picasso;

public class AddPatientActivity extends Activity {

	EditText etFirstName, etLastName, etDOB, etContact, etAdd1, etAdd2, etCity,
			etState, etCountry, etZip, etUserName;
	Date dob;
	ParseImageView picView;
	private ImageView photoButton;
	byte[] mImageData;
	ParseFile imageParseFile;
	long userPatientID;
	CapturePatientExistingDataPOJO data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_patient);

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
		picView = (ParseImageView) findViewById(R.id.preview_image);
		picView.setVisibility(View.INVISIBLE);
		
		photoButton = ((ImageView) findViewById(R.id.photo_button));
		Picasso.with(AddPatientActivity.this).load(R.drawable.camera).resize(90, 90).into(photoButton);
		photoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) AddPatientActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(etUserName.getWindowToken(), 0);
				AddPatientActivity.this.startCamera();
			}
		});
		
		
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

					Toast.makeText(AddPatientActivity.this,
							"No Fields can be left blank", Toast.LENGTH_SHORT)
							.show();
				} else {

					long userId = 0L;
					ParseQuery<ParseObject> query1 = ParseQuery
							.getQuery("_User");
					query1.orderByDescending("userId");
					try {
						userId = query1.getFirst().getInt("userId") + 1;
					} catch (ParseException e1) {

						e1.printStackTrace();
					}
					userPatientID = userId;
					ParseUser userObj = new ParseUser();
					userObj.setUsername(etUserName.getText().toString());
					userObj.setPassword(etUserName.getText().toString());
					userObj.put("Role", "Patient");
					userObj.put("userId", userId);
					userObj.signUpInBackground(new SignUpCallback() {

						@Override
						public void done(ParseException e) {
							if (e == null) {

								long patientId = userPatientID;
								
								ParseObject appDetailsObj = new ParseObject(
										"PatientDetails");
								appDetailsObj.put("patientId", patientId);
								appDetailsObj.put("userName", etUserName
										.getText().toString());
								appDetailsObj.put("fName", etFirstName
										.getText().toString());
								appDetailsObj.put("lName", etLastName.getText()
										.toString());
								SimpleDateFormat  format = new SimpleDateFormat("MM/dd/yyyy");  
								
								    
									try {
										dob = format.parse(etDOB.getText().toString());
									} catch (java.text.ParseException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}  
								   
								
								appDetailsObj.put("dob", dob);
								appDetailsObj.put("contact", Integer
										.parseInt(etContact.getText()
												.toString()));
								appDetailsObj.put("address1", etAdd1.getText()
										.toString());
								appDetailsObj.put("address2", etAdd2.getText()
										.toString());
								appDetailsObj.put("city", etCity.getText()
										.toString());
								appDetailsObj.put("state", etState.getText()
										.toString());
								appDetailsObj.put("country", etCountry
										.getText().toString());
								appDetailsObj.put("zip", Integer.parseInt(etZip
										.getText().toString()));
								appDetailsObj.put("patientImage", imageParseFile);
								appDetailsObj
										.saveInBackground(new SaveCallback() {

											@Override
											public void done(ParseException e) {
												if (e == null) {
													Toast.makeText(
															AddPatientActivity.this,
															"Patient added successfully!",
															Toast.LENGTH_SHORT)
															.show();
													Intent i = new Intent(
															AddPatientActivity.this,
															SearchActivity.class);
													startActivity(i);
													finish();
												} else {
													Toast.makeText(
															AddPatientActivity.this,
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
	Calendar myCalendar = Calendar.getInstance();
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
	
	@Override
	public void onResume() {
		
		
		if(!this.getIntent().getExtras().get("photoUrl").equals("abc")){
			 mImageData = (byte[]) this.getIntent().getExtras().get("photoUrl");
			final Bitmap mBitmap = BitmapFactory.decodeByteArray(mImageData, 0, mImageData.length);
			loadCapturedData();
			picView.setImageBitmap(mBitmap);
			imageParseFile = new ParseFile(mImageData);
			picView.setVisibility(View.VISIBLE);
			super.onResume();
		}else{
			super.onResume();
		}
		
		
	}
	
	private void loadCapturedData() {
		CapturePatientExistingDataPOJO data = new CapturePatientExistingDataPOJO();
		data = (CapturePatientExistingDataPOJO) this.getIntent().getExtras().getSerializable("existingPatientData");
		
		etFirstName.setText(data.getEtFirstName());
		etLastName.setText(data.getEtLastName());
		if(data.getEtDOB() != null){
		etDOB.setText(data.getEtDOB().toString());
		}
	//	etContact.setText(data.getEtContact());
		etAdd1.setText(data.getEtAdd1());
		etAdd2.setText(data.getEtAdd2());
		etCity.setText(data.getEtCity());
		etState.setText(data.getEtState());
		etCountry.setText(data.getEtCountry());
	//	etZip.setText(data.getEtZip());
		etUserName.setText(data.getEtUserName());
		
		
	}

	public void startCamera() {
		Intent i = new Intent(AddPatientActivity.this,CameraActivity.class);
		captureData();
		i.putExtra("patientData", data);
		startActivity(i);
		finish();
	}

	private void captureData() {
		
		 data = new CapturePatientExistingDataPOJO();
		 data.setEtAdd1(this.etAdd1.getText().toString());
		 data.setEtAdd2(this.etAdd2.getText().toString());
		 data.setEtFirstName(this.etFirstName.getText().toString());
		 data.setEtLastName(this.etLastName.getText().toString());
		 SimpleDateFormat  format = new SimpleDateFormat("MM/dd/yyyy");  
			try {
				dob = format.parse(this.etDOB.getText().toString());
			} catch (java.text.ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
		 data.setEtDOB(dob);

		 data.setEtCity(this.etCity.getText().toString());
		 data.setEtState(this.etState.getText().toString());
		 data.setEtCountry(this.etCountry.getText().toString());
		
		 data.setEtUserName(this.etUserName.getText().toString());
		 
		
		
	}
	

}
