package com.example.mrs;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;



public class PatientSearchDisease extends Activity implements OnClickListener {
	
	ArrayList<String> aList;
	LinearLayout addChkLayout;
	 Button findDisease;
	 ArrayList<Integer> symptomIdList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_search_disease);
		
		addChkLayout = (LinearLayout) findViewById(R.id.linearLayout1);
		findDisease = (Button) findViewById(R.id.findDisease);
		findDisease.setOnClickListener(this);
		
		aList = new ArrayList<String>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Symptoms");
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				
			if(e == null){
				for (ParseObject parseObject : objects) {

					CheckBox cb = new CheckBox(PatientSearchDisease.this);
					cb.setText(parseObject.getString("symptomName"));
					cb.setTag(parseObject.getInt("symptomId"));
			//		cb.setBackgroundColor(Color.parseColor("#009384"));
					addChkLayout.addView(cb);
				
				}
				
			}else{
				Toast.makeText(PatientSearchDisease.this,e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	
	}

	

	@Override
	public void onClick(View v) {
		symptomIdList = new ArrayList<Integer>();
		for (int i=0; i<addChkLayout.getChildCount();i++){
			CheckBox cb = (CheckBox) addChkLayout.getChildAt(i);
			if(cb.isChecked()){
				symptomIdList.add((Integer) cb.getTag());
			}
			
		}
		
		Intent i = new Intent(PatientSearchDisease.this,PatientDiseaseDetails.class);
		i.putExtra("Symptoms", symptomIdList);
		startActivity(i);
		
	}
}
