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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Switch;

public class ProfilesListActivity extends Activity {
	private Intent profileSetupActivity = null;
	private Switch profilesListSwitchActivate = null;
	private ListView profilesListView = null;
	private ArrayAdapter<String> adapter = null;
	
	private List<String> profilesListName;
	private ProfilesList profiles = null;
	private int selectedItemID = -1;
	
	
	private void showAlertDialogAddProfile() {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View profileAddAlertDialogView = inflater.inflate(R.layout.profiles_list_alert_dialog, null);
		AlertDialog.Builder profileAlertDialogBuilder = new AlertDialog.Builder(this);
		
		profileAlertDialogBuilder.setView(profileAddAlertDialogView);
		profileAlertDialogBuilder.setTitle("Create a new profile");
		
		profileAlertDialogBuilder.setPositiveButton("Validate", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText profilesListAlertDialogEditText = (EditText) profileAddAlertDialogView.findViewById(R.id.editTextProfilesListAlertDialog);
				String profileName = profilesListAlertDialogEditText.getText().toString();
				
				if (! profileName.isEmpty()) {
					profiles.getProfiles().add(new Profile(profileName));
					profiles.saveProfilesList();
					//startActivity(profileSetupActivity); ERREUR QUE JE COMPRENDS PAS!!!!
					//startActivityForResult(profileSetupActivity, 1);
				} else {
					showAlertDialogAddProfile();
				}
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
	
	
	private void showAlertDialogRenameProfile() {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View profileAddAlertDialogView = inflater.inflate(R.layout.profiles_list_alert_dialog, null);
		AlertDialog.Builder profileAlertDialogBuilder = new AlertDialog.Builder(this);
		
		profileAlertDialogBuilder.setView(profileAddAlertDialogView);
		profileAlertDialogBuilder.setTitle("Rename the profile");
		
		profileAlertDialogBuilder.setPositiveButton("Validate", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText profilesListAlertDialogEditText = (EditText) profileAddAlertDialogView.findViewById(R.id.editTextProfilesListAlertDialog);
				String newProfileName = profilesListAlertDialogEditText.getText().toString();
				
				if (! newProfileName.isEmpty()) {
					profiles.getProfiles().get(selectedItemID).setName(newProfileName);
					profiles.saveProfilesList();
					refreshProfilesList();
					selectedItemID = -1;
				} else {
					showAlertDialogRenameProfile();
				}
			}

		});

		profileAlertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				selectedItemID = -1;
				dialog.cancel();
			}

		});
		
		profileAlertDialogBuilder.show();
	}
	
	
	private void showPopupMenuProfilesItem(View view) {
		PopupMenu popupMenuProfileItem; 
		
		popupMenuProfileItem = new PopupMenu(this, view);
		popupMenuProfileItem.getMenuInflater().inflate(R.menu.popup_menu_profiles_list_item, 
				popupMenuProfileItem.getMenu());
		
		popupMenuProfileItem.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				switch(item.getItemId()) {
					case R.id.rename_item_popup_menu_profiles_list:
						showAlertDialogRenameProfile();
						return true;
					case R.id.modify_item_popup_menu_profiles_list: 
						profileSetupActivity.putExtra("itemID", selectedItemID);
						startActivity(profileSetupActivity);
						selectedItemID = -1;
						return true;
					case R.id.delete_item_popup_menu_profiles_list:
						profiles.getProfiles().remove(selectedItemID);
						profiles.saveProfilesList();
						refreshProfilesList();
						selectedItemID = -1;
						return true;
					default:
						return true;
				}
				
			}
			
		});
		
		popupMenuProfileItem.show();
	}
	
	
	private void refreshProfilesList() {
		profilesListName = new ArrayList<String>();
		for(int index = 0; index < profiles.getProfiles().size(); index++) {
			profilesListName.add(profiles.getProfiles().get(index).getName());
		}

		adapter = new ArrayAdapter<String>(ProfilesListActivity.this, android.R.layout.simple_list_item_single_choice, profilesListName);
		profilesListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
			case 1:
				refreshProfilesList();
				break;
			default:
				break;
		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profiles_list_activity);
				
		this.profiles = new ProfilesList(this.getApplicationContext());
		this.profileSetupActivity = new Intent(this, ProfileSetupActivity.class);
		
		this.profilesListSwitchActivate = (Switch) findViewById(R.id.switchActivate);
		this.profilesListView = (ListView) findViewById(R.id.listViewProfiles);
		
		this.profiles.loadProfilesList();
		
		this.profilesListName = new ArrayList<String>();
		for(int index = 0; index < profiles.getProfiles().size(); index++) {
			this.profilesListName.add(profiles.getProfiles().get(index).getName());
		}
		
		this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, this.profilesListName);
		this.profilesListView.setAdapter(this.adapter);
		
		this.profilesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
				selectedItemID = position;
				showPopupMenuProfilesItem(view);
				return true;
			}
			
		});
		
		this.profilesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
				// Changer le profile par defaut
			}
			
		});
		
		this.profilesListSwitchActivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					// The toggle is enabled
				} else {
					// The toggle is disabled
				}

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
				this.showAlertDialogAddProfile();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
}
