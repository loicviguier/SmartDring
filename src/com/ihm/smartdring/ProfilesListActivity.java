package com.ihm.smartdring;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ProfilesListActivity extends Activity {
	//private Button addProfile = null;
	private Intent profileSettingActivity = null;
	
	private void initialize() {
		//this.addProfile = (Button)findViewById(R.id.menu_add_profile);
		this.profileSettingActivity = new Intent(this, ProfileSetupActivity.class);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profiles_list_activity);
		
		this.initialize();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profiles_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()) {
			case R.id.menu_add_profile:
				startActivity(this.profileSettingActivity);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
}
