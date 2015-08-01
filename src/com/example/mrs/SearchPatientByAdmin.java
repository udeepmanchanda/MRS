package com.example.mrs;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.CustomAdapterSearchPatientByAdmin;
import com.example.pojo.PatientPOJO;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class SearchPatientByAdmin extends Activity {

	ArrayList<PatientPOJO> aList;
	ArrayList<String> patientNameList;
	EditText searchText;
	ArrayAdapter<PatientPOJO> adapter;
	ListView lv;
	int patientIdRemove;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_patient_by_admin);

		lv = (ListView) findViewById(R.id.listView1);
		searchText = (EditText) findViewById(R.id.editText1);

		aList = new ArrayList<PatientPOJO>();
		patientNameList = new ArrayList<String>();

		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
				"PatientDetails");

		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null && objects != null) {

					for (ParseObject pObj : objects) {
						PatientPOJO p = new PatientPOJO();

						p.setPatientId(pObj.getInt("patientId"));
						p.setAddress1(pObj.getString("address1"));
						p.setAddress2(pObj.getString("address2"));
						p.setCity(pObj.getString("city"));
						p.setContact(pObj.getInt("contact"));
						p.setDate(pObj.getDate("dob"));
						p.setfName(pObj.getString("fName"));
						p.setlName(pObj.getString("lName"));
						p.setState(pObj.getString("state"));
						p.setUserName(pObj.getString("userName"));
						p.setCountry(pObj.getString("country"));
						p.setZip(pObj.getInt("zip"));
						
						patientNameList.add(pObj.getString("fName") + " "
								+ pObj.getString("lName"));
						
						if(pObj.getParseFile("patientImage") !=  null){
							p.setPicUrl(pObj.getParseFile("patientImage").getUrl());
						}

						aList.add(p);

					}

					adapter = new CustomAdapterSearchPatientByAdmin(
							SearchPatientByAdmin.this,
							R.layout.search_patient_list_admin, aList);
					lv.setAdapter(adapter);

				}

			}
		});

		searchText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text

				String text = searchText.getText().toString()
						.toLowerCase(Locale.getDefault());
				((CustomAdapterSearchPatientByAdmin) adapter).filter(text);

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		getMenuInflater().inflate(R.menu.to_do_admin, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_search_doctor) {
			Intent intent = new Intent(SearchPatientByAdmin.this,
					SearchDoctorByAdmin.class);
			startActivity(intent);
			finish();
			return true;
		} else if (id == R.id.action_update) {
			Intent intent = new Intent(SearchPatientByAdmin.this,
					UpdateMedSympByAdmin.class);
			startActivity(intent);
			finish();
			return true;
		} else if (id == R.id.action_logout) {
			ParseUser.logOut();
			Intent intent = new Intent(SearchPatientByAdmin.this,
					LoginActivity.class);
			startActivity(intent);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void showAlert(final PatientPOJO pObj ) {
		
		//patientId = pObj.getPatientId();
		patientIdRemove = pObj.getPatientId();;
		Log.d("demo", String.valueOf(patientIdRemove));
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				SearchPatientByAdmin.this);

		// set title
		if("Inactive".equals(pObj.getStatus())){
			alertDialogBuilder.setTitle("Do you want to make the Patient Active");
		}else{
			alertDialogBuilder.setTitle("Do you want to make the Patient Inactive");
		}

		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity
								if("Inactive".equals(pObj.getStatus())){
									activePatientData();
									pObj.setStatus("Active");
								}else{
									inactivePatientData();
									pObj.setStatus("Inactive");
								}
							
								adapter.notifyDataSetChanged();
								
							}

						
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
	
	private void activePatientData() {
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PatientDetails");
		query.whereEqualTo("patientId", patientIdRemove);
		try {
			ParseObject object = query.getFirst();
			object.put("status", "Active");
			object.save();
			}catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	
	/*	ParseQuery<ParseObject> query3 = ParseQuery.getQuery("_User");
		query3.whereEqualTo("userId", patientIdRemove);
		try {
			ParseObject object = query3.getFirst();
			object.put("status", "Active");
			object.save();
			
			}catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		
		Toast.makeText(SearchPatientByAdmin.this, "Patient Active", Toast.LENGTH_SHORT).show();

	}

	public void inactivePatientData() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PatientDetails");
		query.whereEqualTo("patientId", patientIdRemove);
		try {
			ParseObject object = query.getFirst();
			object.put("status", "Inactive");
			object.save();
			}catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		/*
		ParseQuery<ParseObject> query1 = ParseQuery.getQuery("AppointmentDetails");
		query1.whereEqualTo("patientId", patientIdRemove);
		try {
			List<ParseObject> objects = query1.find();
			for (ParseObject p : objects) {

					p.delete();
			}
			}catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		/*ParseQuery<ParseObject> query2 = ParseQuery.getQuery("MedicalRecords");
		query2.whereEqualTo("patientId", patientIdRemove);
		try {
			List<ParseObject> objects = query2.find();
			for (ParseObject p : objects) {

					p.delete();
			}
			}catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
/*		ParseQuery<ParseObject> query3 = ParseQuery.getQuery("_User");
		query3.whereEqualTo("userId", patientIdRemove);
		try {
			ParseObject object = query3.getFirst();
			object.put("status", "Inactive");
			object.save();
			
			}catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
*/		
		Toast.makeText(SearchPatientByAdmin.this, "Patient Inactive", Toast.LENGTH_SHORT).show();

	}

}
