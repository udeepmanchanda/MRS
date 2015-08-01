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

import com.example.adapter.CustomAdapterSearchDoctor;
import com.example.pojo.DoctorPOJO;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class SearchDoctorByManager extends Activity  {
	

	ArrayList<DoctorPOJO> aList;
	ArrayList<String> doctorNameList;
	EditText searchText;
	ArrayAdapter<DoctorPOJO> adapter;
	ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_doctor_by_manager);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		lv = (ListView) findViewById(R.id.listView1);
		searchText = (EditText)findViewById(R.id.editText1);
		
		aList = new ArrayList<DoctorPOJO>();
		doctorNameList = new ArrayList<String>();
		
	
		
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("DoctorDetails");
					
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if(e == null && objects != null){
					
					for(ParseObject pObj : objects){
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
						
						doctorNameList.add(pObj.getString("fName") + " " + pObj.getString("lName"));
						
						aList.add(d);
												
					}
				
					adapter = new CustomAdapterSearchDoctor(SearchDoctorByManager.this, R.layout.search_doctor_list, aList);
					lv.setAdapter(adapter);
					
				}
				
			}
		});       
			     	
		searchText.addTextChangedListener(new TextWatcher() {
		     
		    @Override
		    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
		        // When user changed the Text
		    	
		    	String text = searchText.getText().toString().toLowerCase(Locale.getDefault());
				((CustomAdapterSearchDoctor) adapter).filter(text);
		    	
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

		getMenuInflater().inflate(R.menu.patient_show_medications, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_logout) {
			ParseUser.logOut();
			 Intent intent = new Intent(SearchDoctorByManager.this, LoginActivity.class);
			 startActivity(intent);
			 finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
