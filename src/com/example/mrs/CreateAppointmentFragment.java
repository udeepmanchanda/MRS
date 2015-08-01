package com.example.mrs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.pojo.PatientPOJO;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.SaveCallback;

public class CreateAppointmentFragment extends Fragment implements
		OnClickListener {

	TextView patientTv;
	EditText doctorEt, dateEt, timeEt;
	Button saveBtn;
	int doctorId;
	int patientId;
	String appointmentDate;
	String appointmentTime;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_create_appointment,
				container, false);

		saveBtn = (Button) rootView.findViewById(R.id.saveBtn);
		patientTv = (TextView) rootView.findViewById(R.id.patientNameTv);
		doctorEt = (EditText) rootView.findViewById(R.id.doctorTv);
		dateEt = (EditText) rootView.findViewById(R.id.dateTv);
		timeEt = (EditText) rootView.findViewById(R.id.timeTv);
		doctorEt.setOnClickListener(this);
		dateEt.setOnClickListener(this);
		timeEt.setOnClickListener(this);
		doctorEt.setKeyListener(null);
		dateEt.setKeyListener(null);
		timeEt.setKeyListener(null);
		saveBtn.setOnClickListener(this);

		PatientPOJO patientObj = (PatientPOJO) getActivity().getIntent()
				.getSerializableExtra("PatientObj");
		patientId = patientObj.getPatientId();

		patientTv.setText("Patient : " + patientObj.getfName() + " "
				+ patientObj.getlName());

		return rootView;
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.doctorTv:
			
			final ArrayList<String> doctorList =  new ArrayList<String>();
			
			 ParseQueryAdapter.QueryFactory<ParseObject> factory =
				     new ParseQueryAdapter.QueryFactory<ParseObject>() {
				       public ParseQuery create() {
				    	   ParseQuery<ParseObject> query = ParseQuery.getQuery("DoctorDetails");
						 
				         return query;
				       }
				     };
			
				   
			
			final ParseQueryAdapter<ParseObject> adapter;
			 adapter = new ParseQueryAdapter<ParseObject>(v.getContext(),factory, android.R.layout.simple_list_item_1);
			 
			 adapter.setTextKey("userName");
			 
			 //adapter.setTextKey("lastName");
			 
			 // Perhaps set a callback to be fired upon successful loading of a new set of ParseObjects.
			 ListView userListView = new ListView(v.getContext());
			 userListView.setAdapter(adapter);
			
		  	  
		
		AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
		builder.setTitle("Choose Doctors");

		userListView.setAdapter(adapter);

		builder.setView(userListView);
		final AlertDialog dialog = builder.create();
		

		
		userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				final ParseObject parseObject = adapter.getItem(position);
				
				doctorEt.setText(parseObject.getString("userName"));
		       doctorId = parseObject.getInt("doctorId");
		       
			dialog.dismiss();
			}
		});

		dialog.show();		

			
			break;

		case R.id.dateTv:
			
			
			
			 new DatePickerDialog(v.getContext(), date, myCalendar
	                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
	                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			
			break;
			
		case R.id.timeTv:
			
			
			new TimePickerDialog(v.getContext(), time, myCalendar.get(Calendar.HOUR), 
					myCalendar.get(Calendar.MINUTE), true).show();
			   
			
			break;
			
		case R.id.saveBtn:
			
			if (doctorEt.getText().toString().trim().isEmpty() || dateEt.getText().toString().trim().isEmpty() ||timeEt.getText().toString().trim().isEmpty()){
				Toast.makeText(getActivity(), "Add the complete details to create an appointment.", Toast.LENGTH_SHORT).show();
				return;
			}
			long appointmentId = 0L;
			ParseQuery<ParseObject> query = ParseQuery.getQuery("AppointmentDetails");
			query.orderByDescending("appointmentId");
			try {
			appointmentId = query.getFirst().getInt("appointmentId") + 1;
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
				ParseObject appDetailsObj = new ParseObject("AppointmentDetails");
				appDetailsObj.put("patientId", patientId);
				appDetailsObj.put("doctorId", doctorId);
				appDetailsObj.put("appointmentId", appointmentId);
				//appDetailsObj.put("appointmentDate", dateEt.getText());
			//	String dtStart = "2010-10-15T09:27:37Z";  
				SimpleDateFormat  format = new SimpleDateFormat("MM/dd/yyyy hh:mm");  
				try {  
				    Date date = format.parse(appointmentDate+ " " + appointmentTime);  
				    Calendar cal = Calendar.getInstance();
				    cal.setTime(date);
				    cal.add(Calendar.HOUR, -4);// because server is back in California
				    Date fourHourBack = cal.getTime();
				    appDetailsObj.put("appointmentDate", date);
				} catch (Exception e) {  
				    // TODO Auto-generated catch block  
				    e.printStackTrace();  
				} 
			 
			   appDetailsObj.saveInBackground(new SaveCallback() {
				
				@Override
				public void done(ParseException e) {
					if(e == null){
						Toast.makeText(getActivity(), "Appointment created!", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
					}
					
				}
			});
			
			break;
		}
		
		
		
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

			dateEt.setText(sdf.format(myCalendar.getTime()));

			appointmentDate = sdf.format(myCalendar.getTime());

		}

	};

	TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			String am_pm = "";
			myCalendar.set(Calendar.HOUR, hourOfDay);
			myCalendar.set(Calendar.MINUTE, minute);

			appointmentTime = hourOfDay + ":" + minute;
			/*
			 * if (hourOfDay == 0) { hourOfDay += 12; am_pm = "AM"; } else if
			 * (hourOfDay == 12) { am_pm = "PM"; } else if (hourOfDay > 12) {
			 * hourOfDay -= 12; am_pm = "PM"; } else { am_pm = "AM"; }
			 */

			if (minute < 10) {
				timeEt.setText(new StringBuilder().append(hourOfDay)
						.append(":0").append(minute));
			} else {
				timeEt.setText(new StringBuilder().append(hourOfDay)
						.append(":").append(minute));
			}

			// et3.setText(myCalendar.getTime().toString());

		}
	};

}