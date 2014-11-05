package com.ihm.smartdring;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class ProfilesList {
	private static String FILENAME = "profiles";
	
	private List<Profile> profiles;

	public ProfilesList() {
		// TODO Auto-generated constructor stub
		profiles = new ArrayList<Profile>();
	}

	public void saveProfilesList(){
		try {
			//TODO
			FileOutputStream fos = null;
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.profiles);
			oos.flush(); 
			oos.close();
		} catch(Exception ex) {
			Log.v("Profile List Serialization Save Error : ", ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void loadProfilesList() {
		try {
			//TODO
			FileInputStream fis = null;
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object output = ois.readObject();
			this.profiles = (ArrayList<Profile>) output;
			ois.close();
		} catch(Exception ex) {
			Log.v("Profile List Serialization Read Error : ",ex.getMessage());
			ex.printStackTrace();
		}
	}
}
