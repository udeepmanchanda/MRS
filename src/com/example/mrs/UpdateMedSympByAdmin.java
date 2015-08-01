package com.example.mrs;

import java.text.SimpleDateFormat;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateMedSympByAdmin extends Activity {

	EditText etMedication, etSymptom;
	Button btnUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_med_symp_by_admin);

		etMedication = (EditText) findViewById(R.id.et_medication);
		etSymptom = (EditText) findViewById(R.id.et_symptom);
		btnUpdate = (Button) findViewById(R.id.btn_update);

		btnUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (etMedication.getText().toString().trim().isEmpty()
						|| etSymptom.getText().toString().trim().isEmpty()) {
					Toast.makeText(UpdateMedSympByAdmin.this,
							"No field can be left blank", Toast.LENGTH_SHORT)
							.show();
				} else {

				}
				/*
				 * etMedication.getText(); etSymptom.getText();
				 */

				long symptomId = 0L;
				long medicationId = 0L;
				ParseQuery<ParseObject> query1 = ParseQuery
						.getQuery("Symptoms");
				query1.orderByDescending("symptomId");
				try {
					symptomId = query1.getFirst().getInt("symptomId") + 1;
				} catch (ParseException e1) {
					e1.printStackTrace();
				}

				ParseQuery<ParseObject> query2 = ParseQuery
						.getQuery("MedicationDetails");
				query2.orderByDescending("medicationId");
				try {
					medicationId = query2.getFirst().getInt("medicationId") + 1;
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				
				ParseObject symDetailsObj = new ParseObject("Symptoms");
				symDetailsObj.put("symptomId", symptomId);
				symDetailsObj.put("symptomName", etSymptom.getText()
						.toString());
				symDetailsObj.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							Toast.makeText(UpdateMedSympByAdmin.this,
									"Symptoms details added successfully!",
									Toast.LENGTH_SHORT).show();
							Intent i = new Intent(UpdateMedSympByAdmin.this,
									SearchPatientByAdmin.class);
							startActivity(i);
							finish();
						} else {
							Toast.makeText(UpdateMedSympByAdmin.this,
									e.getLocalizedMessage(), Toast.LENGTH_SHORT)
									.show();
						}

					}
				});

				ParseObject appDetailsObj = new ParseObject("MedicationDetails");
				appDetailsObj.put("medicationId", medicationId);
				appDetailsObj.put("medicationName", etMedication.getText()
						.toString());
				appDetailsObj.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							Toast.makeText(UpdateMedSympByAdmin.this,
									"Details added successfully!",
									Toast.LENGTH_SHORT).show();
							Intent i = new Intent(UpdateMedSympByAdmin.this,
									SearchPatientByAdmin.class);
							startActivity(i);
							finish();
						} else {
							Toast.makeText(UpdateMedSympByAdmin.this,
									e.getLocalizedMessage(), Toast.LENGTH_SHORT)
									.show();
						}

					}
				});

			
			}
		});
	}
}
