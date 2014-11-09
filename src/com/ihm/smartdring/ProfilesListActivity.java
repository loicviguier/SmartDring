package com.ihm.smartdring;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class ProfilesListActivity extends Activity {
	private Intent profileSetupActivity = null;
	
	
	private void showAlertDialogAddProfile(String title) {
		LayoutInflater inflater = LayoutInflater.from(this);
		View profileAddAlertDialogView = inflater.inflate(R.layout.profile_add_alert_dialog, null);
		AlertDialog.Builder profileAlertDialogBuilder = new AlertDialog.Builder(this);
		
		
		profileAlertDialogBuilder.setView(profileAddAlertDialogView);
		profileAlertDialogBuilder.setTitle(title);
		
		profileAlertDialogBuilder.setPositiveButton("Validate", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				startActivity(profileSetupActivity);
				// Ajouter new item profile et faire passer a la vue suivante 
			}

		});

		profileAlertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}

		});
		
		profileAlertDialogBuilder.show();
	}
	
	
	private void showPopupMenuProfilesItem(View view, int position) {
		PopupMenu popupMenuProfileItem; 
		
		popupMenuProfileItem = new PopupMenu(this, view);
		popupMenuProfileItem.getMenuInflater().inflate(R.menu.popup_menu_profiles_list_item, 
				popupMenuProfileItem.getMenu());
		
		popupMenuProfileItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				switch(item.getItemId()) {
					case R.id.rename_item_popup_menu_profiles_list:
						showAlertDialogAddProfile("Rename the profile");
						return true;
					case R.id.modify_item_popup_menu_profiles_list:
						startActivity(profileSetupActivity);
						// Faire passer le profile à la vue
						return true;
					case R.id.delete_item_popup_menu_profiles_list:
						// Action supprimer profile
						return true;
					default:
						return true;
				}
				
			}
			
		});
		
		popupMenuProfileItem.show();
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profiles_list_activity);
		
		profileSetupActivity = new Intent(this, ProfileSetupActivity.class);
		
		ListView profilesListView = (ListView) findViewById(R.id.listViewProfiles);
		
		List<String> exemple = new ArrayList<String>();
		exemple.add("Home");
		exemple.add("Work");
		exemple.add("Sport");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, exemple);
		profilesListView.setAdapter(adapter);
		
		profilesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
				showPopupMenuProfilesItem(view, position);
				return true;
			}
			
		});
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.profiles_list, menu);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()) {
			case R.id.menu_add_profile:
				this.showAlertDialogAddProfile("Create a new profile");
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
}
