package com.example.mrs;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class LoginActivity extends Activity implements OnClickListener {

	Button loginBtn,reset;
	EditText userName,pwd,role;
	
	ArrayList<String> roleList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		loginBtn = (Button) findViewById(R.id.button1);
		loginBtn.setOnClickListener(this);
		reset = (Button)findViewById(R.id.button2);
		reset.setOnClickListener(this);
		
		userName = (EditText)findViewById(R.id.editText1);
		pwd = (EditText)findViewById(R.id.editText2);
		role = (EditText)findViewById(R.id.editText3);
		role.setKeyListener(null);
		role.setOnClickListener(this);
		
		roleList = new ArrayList<String>();
		
		 roleList.add("Patient");
		 roleList.add("Operator");
		 roleList.add("Doctor");
		 roleList.add("Manager");
		 roleList.add("Administrator");
		 
		
		if(ParseUser.getCurrentUser() != null){
			String currentUserRole = ParseUser.getCurrentUser().getString("Role");
			Intent I = null;
			if("Doctor".equals(currentUserRole)){
				 I = new Intent(LoginActivity.this, CurrentDoctorAppointments.class);
			}else if("Operator".equals(currentUserRole)){
				 I = new Intent(LoginActivity.this, SearchActivity.class);
			}else if("Patient".equals(currentUserRole)){
				 I = new Intent(LoginActivity.this, PatientViewOwnMedicalHistory.class);
			}else if("Manager".equals(currentUserRole)){
				 I = new Intent(LoginActivity.this, SearchByManager.class);
			}else if("Administrator".equals(currentUserRole)){
				 I = new Intent(LoginActivity.this, SearchPatientByAdmin.class);
			}
			
			startActivity(I);
			finish();
		}
		
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.button1){
		
		if(userName.getText() != null && userName.getText().toString().trim().length() == 0){
			Toast.makeText(LoginActivity.this, "UserName cannot be empty.", Toast.LENGTH_SHORT).show();
		}else if(pwd.getText() != null && pwd.getText().toString().trim().length() == 0){
			Toast.makeText(LoginActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
		}else if(role.getText() != null && role.getText().toString().trim().length() == 0){
			Toast.makeText(LoginActivity.this, "Role cannot be empty.", Toast.LENGTH_SHORT).show();
		}else{
			
			ParseQuery<ParseUser> query = ParseUser.getQuery();
		    query.whereEqualTo("username",userName.getText().toString());
		    query.whereEqualTo("Role", role.getText().toString());
		    query.findInBackground(new FindCallback<ParseUser>() {
				
			@Override
			public void done(List<ParseUser> objects, ParseException e) {

					if(e == null && objects != null && objects.size()>0){
						
						ParseUser.logInInBackground(userName.getText().toString(), pwd.getText().toString(), new LogInCallback() {
							
							@Override
							public void done(ParseUser user, ParseException e) {
								
								if (user != null) {
								      Intent I = null;
									if(role.getText().toString().equals("Operator")){
									 I = new Intent(LoginActivity.this, SearchActivity.class);
									}else if(role.getText().toString().equals("Doctor")){
										 I = new Intent(LoginActivity.this, CurrentDoctorAppointments.class);
										}else if(role.getText().toString().equals("Patient")){
											 I = new Intent(LoginActivity.this, PatientViewOwnMedicalHistory.class);
											}else if(role.getText().toString().equals("Manager")){
												 I = new Intent(LoginActivity.this, SearchByManager.class);
												}else if(role.getText().toString().equals("Administrator")){
													 I = new Intent(LoginActivity.this, SearchPatientByAdmin.class);
													}
									startActivity(I);
									Toast.makeText(LoginActivity.this, "Login was successful", Toast.LENGTH_SHORT).show();
									finish();
								    } else {
								      
								    	Toast.makeText(LoginActivity.this, "Login was not successful", Toast.LENGTH_SHORT).show();
								    }
							}
							});
					} else{
						Toast.makeText(LoginActivity.this, "User Profile not found", Toast.LENGTH_SHORT).show();
					}
					
				}
			});
		  
			
		}
	}
		
		
		if(v.getId() == R.id.button2){
			userName.setText("");
			pwd.setText("");
			role.setText("");
		}
		
		if(v.getId() == R.id.editText3){
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, roleList);
		 ListView roleListView = new ListView(this);
		 roleListView.setAdapter(adapter);
		
		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setTitle("Choose Role");

		 roleListView.setAdapter(adapter);

		 builder.setView(roleListView);
		 final AlertDialog dialog = builder.create();
		 
		 roleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
				role.setText(roleList.get(position));
			       
				dialog.dismiss();
				}
			});

			dialog.show();		

		}
	}
}
