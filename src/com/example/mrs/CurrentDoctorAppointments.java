package com.example.mrs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.adapter.CustomAdapterDoctorAppointmentList;
import com.example.pojo.VisitDetailsPOJO;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class CurrentDoctorAppointments extends Activity {

	int doctorId;
	ArrayList<VisitDetailsPOJO> visitList;
	String doctorName;
	ArrayAdapter<VisitDetailsPOJO> adapter;
	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current_doctor_appointments);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		ParseUser userObj = ParseUser.getCurrentUser();
		doctorId = (Integer) userObj.get("userId");
		doctorName = (String) userObj.get("username");
		Date date = new Date();

		ParseQuery<ParseObject> query = ParseQuery
				.getQuery("AppointmentDetails");
		query.whereEqualTo("doctorId", doctorId);
		query.addDescendingOrder("appointmentDate");
		query.whereGreaterThanOrEqualTo("appointmentDate", date);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null && objects != null && objects.size() > 0) {
					visitList = new ArrayList<VisitDetailsPOJO>();
					for (ParseObject pObj : objects) {

						VisitDetailsPOJO vdp = new VisitDetailsPOJO();

						vdp.setDoctorName(doctorName);
						vdp.setDoctorId(doctorId);
						vdp.setAppointmentId(pObj.getInt("appointmentId"));
						vdp.setPatientId(pObj.getInt("patientId"));
						vdp.setAppointmentDate(pObj.getDate("appointmentDate"));

						ParseQuery<ParseObject> query = ParseQuery
								.getQuery("PatientDetails");
						query.whereEqualTo("patientId",
								pObj.getInt("patientId"));
						try {
							vdp.setPatientName(query.getFirst().getString(
									"fName"));
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						visitList.add(vdp);

					}
					lv = (ListView) findViewById(R.id.listView1);
					adapter = new CustomAdapterDoctorAppointmentList(
							CurrentDoctorAppointments.this,
							R.layout.doctor_app_list, visitList);
					lv.setAdapter(adapter);

					lv.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long id) {
								Intent i = new Intent(CurrentDoctorAppointments.this, UpdateMedicalRecordsByDoc.class);
								i.putExtra("visitOBJ", visitList.get(position));
								startActivity(i);
							
						}
					});
				}

			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		getMenuInflater().inflate(R.menu.current_doctor_appointments, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.searchPatientMenu) {

			Intent intent = new Intent(CurrentDoctorAppointments.this,
					DoctorPatientSearchActivity.class);

			startActivity(intent);

			return true;
		} else if (id == R.id.action_logout) {
			ParseUser.logOut();
			Intent intent = new Intent(CurrentDoctorAppointments.this,
					LoginActivity.class);
			startActivity(intent);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
