package com.example.mrs;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.adapter.CustomAdapterDoctorPatientSearch;
import com.example.pojo.PatientPOJO;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;




	public class DoctorPatientSearchActivity extends Activity  {
		

		ArrayList<PatientPOJO> aList;
		ArrayList<String> patientNameList;
		EditText searchText;
		ArrayAdapter<PatientPOJO> adapter;
		ListView lv;
		String fromWhere;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_doctor_patient_search);
			
			lv = (ListView) findViewById(R.id.listView1);
			searchText = (EditText)findViewById(R.id.editText1);
			
			aList = new ArrayList<PatientPOJO>();
			patientNameList = new ArrayList<String>();
			
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("PatientDetails");
						
			query.findInBackground(new FindCallback<ParseObject>() {
				
				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					if(e == null && objects != null){
						
						for(ParseObject pObj : objects){
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
							p.setZip(pObj.getInt("zip"));
							
							patientNameList.add(pObj.getString("fName") + " " + pObj.getString("lName"));
							if(pObj.getParseFile("patientImage") !=  null){
								p.setPicUrl(pObj.getParseFile("patientImage").getUrl());
							}

							
							aList.add(p);
													
						}
					
						adapter = new CustomAdapterDoctorPatientSearch(DoctorPatientSearchActivity.this, R.layout.search_patient_list, aList);
						lv.setAdapter(adapter);
						
					}
					
				}
			});       
				     	
			searchText.addTextChangedListener(new TextWatcher() {
			     
			    @Override
			    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
			        // When user changed the Text
			    	
			    	String text = searchText.getText().toString().toLowerCase(Locale.getDefault());
					((CustomAdapterDoctorPatientSearch) adapter).filter(text);
			    	
			    	/*int textlength = cs.length();
			           ArrayList<PatientPOJO> tempArrayList = new ArrayList<PatientPOJO>();
			           for(PatientPOJO c: aList){
			              if (textlength <= c.getfName().length()) {
			                 if (c.getfName().toLowerCase().contains(cs.toString().toLowerCase())) {
			                    tempArrayList.add(c);
			                 }
			              }
			           }
			           adapter = new CustomAdapterSearchPatient(SearchActivity.this, R.layout.search_patient_list, tempArrayList);
			           lv.setAdapter(adapter);*/
			    	
			    	
			    //    SearchActivity.this.adapter.getFilter().filter(cs);   
			    }
			     
			    @Override
			    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			            int arg3) {
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

			getMenuInflater().inflate(R.menu.to_do, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			
			int id = item.getItemId();
			if (id == R.id.action_add_patient) {

				 Intent intent = new Intent(DoctorPatientSearchActivity.this, AddPatientActivity.class);
				 intent.putExtra("photoUrl", "abc");
				 startActivity(intent);
				 
				return true;
			}else if (id == R.id.action_logout) {
				ParseUser.logOut();
				 Intent intent = new Intent(DoctorPatientSearchActivity.this, LoginActivity.class);
				 startActivity(intent);
				 finish();
				return true;
			}
			return super.onOptionsItemSelected(item);
		}

	
	
	
	
	
	
	
	
	
	
	
	

}
