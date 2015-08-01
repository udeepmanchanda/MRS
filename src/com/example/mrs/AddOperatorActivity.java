package com.example.mrs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class AddOperatorActivity extends Activity {

	EditText etUserName, etFirstName, etLastName;
	long userOperatorID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_operator);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		Button submitBtn = (Button) findViewById(R.id.button1);
		Button resetBtn = (Button) findViewById(R.id.button2);
		etUserName = (EditText) findViewById(R.id.editText1);
		etFirstName = (EditText) findViewById(R.id.editText2);
		etLastName = (EditText) findViewById(R.id.editText3);	
		
		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (etFirstName.getText().toString().isEmpty()
						|| etLastName.getText().toString().isEmpty()
						|| etUserName.getText().toString().isEmpty()) {

					Toast.makeText(AddOperatorActivity.this,
							"No Fields can be left blank", Toast.LENGTH_SHORT)
							.show();
				} else {

					long userId = 0L;
					ParseQuery<ParseObject> query1 = ParseQuery
							.getQuery("_User");
					query1.orderByDescending("userId");
					try {
						userId = query1.getFirst().getInt("userId") + 1;
					} catch (ParseException e1) {

						e1.printStackTrace();
					}
					userOperatorID = userId;
					ParseUser userObj = new ParseUser();
					userObj.setUsername(etUserName.getText().toString());
					userObj.setPassword(etUserName.getText().toString());
					userObj.put("Role", "Operator");
					userObj.put("userId", userId);
					userObj.signUpInBackground(new SignUpCallback() {

						@Override
						public void done(ParseException e) {
							if (e == null) {

								long operatorId = userOperatorID;
								
								ParseObject appDetailsObj = new ParseObject(
										"OperatorDetails");
								appDetailsObj.put("operatorId", operatorId);
								appDetailsObj.put("userName", etUserName
										.getText().toString());
								appDetailsObj.put("fName", etFirstName
										.getText().toString());
								appDetailsObj.put("lName", etLastName.getText()
										.toString());
								   
								appDetailsObj
										.saveInBackground(new SaveCallback() {

											@Override
											public void done(ParseException e) {
												if (e == null) {
													Toast.makeText(
															AddOperatorActivity.this,
															"Operator added successfully!",
															Toast.LENGTH_SHORT)
															.show();
													Intent i = new Intent(
															AddOperatorActivity.this,
															SearchByManager.class);
													startActivity(i);
													finish();
												} else {
													Toast.makeText(
															AddOperatorActivity.this,
															e.getLocalizedMessage(),
															Toast.LENGTH_SHORT)
															.show();
												}

											}
										});

							} else {
								e.printStackTrace();
							}

						}
					});

				}
			}
		});

		resetBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				etUserName.setText("");
				etFirstName.setText("");
				etLastName.setText("");
			}
		});
	}
}
