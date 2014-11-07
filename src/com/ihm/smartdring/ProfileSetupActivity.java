package com.ihm.smartdring;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class ProfileSetupActivity extends Activity {
	private ListView profileSetupListView = null;
	
	private void initialize() {
		this.profileSetupListView = (ListView) findViewById(R.id.listViewSetupProfile);
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setup_activity);
        
        this.initialize();
        
        Boolean[] exemple = {true, false, false};
        
        ProfileSetupListAdapter adapter = new ProfileSetupListAdapter(this, exemple);
        this.profileSetupListView.setAdapter(adapter);
        
        this.profileSetupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ring_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        
    	switch(item.getItemId()) {
			case R.id.menu_discard_profile:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
    	}
    }
    
}
