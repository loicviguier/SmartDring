package com.ihm.smartdring;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

public class ProfilesList {

	// =============================
	// Attributes
	// =============================

	private static final String FILENAME = "profiles";

	private Context fileContext;
	private List<Profile> profiles;

	// =============================
	// Constructor
	// =============================

	/**
	 * Construct a new ProfilesList instance with the current application context
	 * @param fileContext
	 */
	public ProfilesList(Context fileContext) {
		super();
		this.fileContext = fileContext;
		this.profiles = new ArrayList<Profile>();
	}

	// =============================
	// Methods
	// =============================

	/**
	 * Method used to save the profiles list
	 */
	public void saveProfilesList(){
		FileOutputStream fos;
		ObjectOutputStream oos;

		try {
			fos = fileContext.getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);

			oos.writeObject(this.profiles);

			oos.flush(); 
			oos.close();

		} catch(Exception ex) {
			Log.v("Profile List Serialization Save Error : ", ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Method used to load the profile list
	 */
	@SuppressWarnings("unchecked")
	public void loadProfilesList() {
		FileInputStream fis;
		ObjectInputStream ois;

		try {
			fis = fileContext.getApplicationContext().openFileInput(FILENAME);
			ois = new ObjectInputStream(fis);

			this.profiles = (ArrayList<Profile>) ois.readObject();

			ois.close();

		} catch(Exception ex) {
			this.profiles = new ArrayList<Profile>();
			Log.v("Profile List Serialization Read Error : ",ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	// =============================
	// Getter / Setter
	// =============================
	
	/**
	 * @return the profiles
	 */
	public List<Profile> getProfiles() {
		return profiles;
	}

	/**
	 * @param profiles the profiles to set
	 */
	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
}
