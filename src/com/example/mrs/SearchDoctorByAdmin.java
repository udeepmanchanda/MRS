package com.example.mrs;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.adapter.CustomAdapterSearchDoctorByAdmin;
import com.example.pojo.DoctorPOJO;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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

public class SearchDoctorByAdmin extends Activity {

	ArrayList<DoctorPOJO> aList;
	ArrayList<String> doctorNameList;
	EditText searchText;
	ArrayAdapter<DoctorPOJO> adapter;
	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_doctor_by_admin);

		lv = (ListView) findViewById(R.id.listView1);
		searchText = (EditText) findViewById(R.id.editText1);

		aList = new ArrayList<DoctorPOJO>();
		doctorNameList = new ArrayList<String>();

		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
				"DoctorDetails");

		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null && objects != null) {

					for (ParseObject pObj : objects) {
						DoctorPOJO d = new DoctorPOJO();

						d.setDoctorId(pObj.getInt("doctorId"));
						d.setUserName(pObj.getString("userName"));
						d.setfName(pObj.getString("fName"));
						d.setlName(pObj.getString("lName"));
						d.setSpeciality(pObj.getString("speciality"));
						d.setHospitalName(pObj.getString("hospitalName"));
						d.setRoomNo(pObj.getString("roomNo"));
						d.setCity(pObj.getString("city"));
						d.setState(pObj.getString("state"));
						d.setContact(pObj.getInt("contact"));

						d.setCountry(pObj.getString("country"));
						d.setZip(pObj.getInt("zip"));

						doctorNameList.add(pObj.getString("fName") + " "
								+ pObj.getString("lName"));

						aList.add(d);

					}

					adapter = new CustomAdapterSearchDoctorByAdmin(
							SearchDoctorByAdmin.this,
							R.layout.search_doctor_list, aList);
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
				((CustomAdapterSearchDoctorByAdmin) adapter).filter(text);

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

		getMenuInflater().inflate(R.menu.patient_show_medications, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_logout) {
			ParseUser.logOut();
			Intent intent = new Intent(SearchDoctorByAdmin.this,
					LoginActivity.class);
			startActivity(intent);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void showAlertForDoctor(int doctorId) {
		Log.d("demo", String.valueOf(doctorId));

		final int doctorIdRemove = doctorId;

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				SearchDoctorByAdmin.this);

		// set title
		alertDialogBuilder.setTitle("Do you want to delete the Doctor");

		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity

								deleteDoctorData(doctorIdRemove);

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

	public void deleteDoctorData(int doctorIdRemove) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("DoctorDetails");
		query.whereEqualTo("doctorId", doctorIdRemove);
		try {
			List<ParseObject> objects = query.find();
			for (ParseObject p : objects) {

				p.delete();
			}
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		ParseQuery<ParseObject> query1 = ParseQuery
				.getQuery("AppointmentDetails");
		query1.whereEqualTo("doctorId", doctorIdRemove);
		try {
			List<ParseObject> objects = query1.find();
			for (ParseObject p : objects) {

				p.delete();
			}
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		ParseQuery<ParseObject> query2 = ParseQuery.getQuery("MedicalRecords");
		query2.whereEqualTo("doctorId", doctorIdRemove);
		try {
			List<ParseObject> objects = query2.find();
			for (ParseObject p : objects) {

				p.delete();
			}
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		ParseQuery<ParseObject> query3 = ParseQuery.getQuery("_User");
		query3.whereEqualTo("userId", doctorIdRemove);
		try {
			List<ParseObject> objects = query3.find();
			for (ParseObject p : objects) {

				p.delete();
			}
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		Toast.makeText(SearchDoctorByAdmin.this, "Doctor deleted",
				Toast.LENGTH_SHORT).show();

	}

}
