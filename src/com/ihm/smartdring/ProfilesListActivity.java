package com.ihm.smartdring;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProfilesListActivity extends Activity {
	private ListView profilesListView = null;
	private Intent profileSettingActivity = null;
	
	private void initialize() {
		this.profilesListView = (ListView) findViewById(R.id.listViewProfiles);
		this.profileSettingActivity = new Intent(this, ProfileSetupActivity.class);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profiles_list_activity);
		
		this.initialize();
		
		List<String> exemple = new ArrayList<String>();
		exemple.add("Home");
		exemple.add("Work");
		exemple.add("Sport");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, exemple);
		this.profilesListView.setAdapter(adapter);
		
		profilesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				// Implement action on item click here
			}
			
		});
		
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
