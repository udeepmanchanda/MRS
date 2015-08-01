package com.example.mrs;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class PatientShowMedications extends Activity {
	String diseaseName;
	int diseaseId;
	ArrayList<String> medicationList;
	ArrayList<Integer> medicationIdList;
	ListView lv;
	ArrayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_show_medications);
		lv = (ListView)findViewById(R.id.listView1);
		
		medicationList = new ArrayList<String>();
		medicationIdList = new ArrayList<Integer>();
		
		diseaseName = getIntent().getExtras().getString("diseaseName");
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("DiseaseDetails");
		query.whereEqualTo("diseaseName", diseaseName);
		try {
			diseaseId = query.getFirst().getInt("diseaseId");
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		ParseQuery<ParseObject> query1 = ParseQuery.getQuery("DiseaseMedicationDetail");
		query1.whereEqualTo("diseaseId", diseaseId);
		query1.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				
				for (ParseObject parseObject : objects) {
					 
					medicationIdList.add(parseObject.getInt("medicationId"));
				}
				
				ParseQuery<ParseObject> query2 = ParseQuery.getQuery("MedicationDetails");
				query2.whereContainedIn("medicationId", medicationIdList);
				query2.findInBackground(new FindCallback<ParseObject>() {
					
					@Override
					public void done(List<ParseObject> objects, ParseException e) {
					
						for (ParseObject parseObject : objects) {
							medicationList.add(parseObject.getString("medicationName"));
						}
						
						adapter = new ArrayAdapter<String>(PatientShowMedications.this, android.R.layout.simple_list_item_1, medicationList);
						lv.setAdapter(adapter);
					}
				});
				
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient_show_medications, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_logout) {
			 Intent intent = new Intent(PatientShowMedications.this, LoginActivity.class);
			 startActivity(intent);
			 finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
