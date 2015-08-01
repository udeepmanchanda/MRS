package com.example.mrs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class PatientDiseaseDetails extends Activity {
	
	ArrayList<String> symptomsList;
	ArrayList<String> diseaseList;
	ListView lv;
	ArrayAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_disease_details);
		lv = (ListView) findViewById(R.id.listView1);
		
		symptomsList = (ArrayList<String>) getIntent().getExtras().getSerializable("Symptoms");
		diseaseList = new ArrayList<String>();
		
		new GetDataAsync().execute(symptomsList);
		
		   lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent i = new Intent(PatientDiseaseDetails.this,PatientShowMedications.class);
					i.putExtra("diseaseName", diseaseList.get(position));
					startActivity(i);
					
					
				}
			});
		
	}

	
	
	class GetDataAsync extends AsyncTask<ArrayList<String>, Void, Void>{
		ProgressDialog pd;
		

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(PatientDiseaseDetails.this);
			pd.setTitle("Loading Data...");
			pd.show();
		}


		@Override
		protected Void doInBackground(ArrayList<String>... params) {
			ParseQuery<ParseObject> query = ParseQuery.getQuery("DiseaseSymptomDetails");
			query.whereContainedIn("symptomId", symptomsList);
			query.findInBackground(new FindCallback<ParseObject>() {
				
				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					if(e == null){
						
						for (ParseObject parseObject : objects) {
							
							ParseQuery<ParseObject> query = ParseQuery.getQuery("DiseaseDetails");
							query.whereEqualTo("diseaseId", parseObject.getInt("diseaseId"));
							query.findInBackground(new FindCallback<ParseObject>() {
								
								@Override
								public void done(List<ParseObject> objects, ParseException e) {
									if(e == null){
										
										for (ParseObject parseObject2 : objects) {
											diseaseList.add(parseObject2.getString("diseaseName"));
										}
										Set<String> uniqueDisease = new HashSet<String>(diseaseList);
										diseaseList.clear();
										for(String s:uniqueDisease){
											diseaseList.add(s);
										}
										adapter = new ArrayAdapter<String>(PatientDiseaseDetails.this, android.R.layout.simple_list_item_1, diseaseList);
										lv.setAdapter(adapter);
									}else{
										Toast.makeText(PatientDiseaseDetails.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
									}
									
								}
							});
							
						}
						
					}else{
						Toast.makeText(PatientDiseaseDetails.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
					}
					
					
				}
			});
		
			return null;
		}


		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
		}

		
		
	}
}
