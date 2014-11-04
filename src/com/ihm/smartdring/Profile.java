package com.ihm.smartdring;

import java.util.ArrayList;

public class Profile {
	
	// =============================
	// Attributes
	// =============================
	
	private String name;
	private int volume;
	private Boolean ambiantSound;
	private Boolean walkingAction;
	private Boolean flipToSilence;
	
	// =============================
	// Constructor
	// =============================
	
	/**
	 * Create a new object Profile
	 * @param name
	 */
	public Profile(String name) {
		super();
		this.name = name;
		this.volume = 50;
		this.ambiantSound = false;
		this.walkingAction = false;
		this.flipToSilence = false;
	}
	
	// =============================
	// Getter and Setter
	// =============================
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the volume
	 */
	public int getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(int volume) {
		this.volume = volume;
	}

	/**
	 * @return the ambiantSound
	 */
	public Boolean getAmbiantSound() {
		return ambiantSound;
	}

	/**
	 * @param ambiantSound the ambiantSound to set
	 */
	public void setAmbiantSound(Boolean ambiantSound) {
		this.ambiantSound = ambiantSound;
	}

	/**
	 * @return the walkingAction
	 */
	public Boolean getWalkingAction() {
		return walkingAction;
	}

	/**
	 * @param walkingAction the walkingAction to set
	 */
	public void setWalkingAction(Boolean walkingAction) {
		this.walkingAction = walkingAction;
	}

	/**
	 * @return the flipToSilence
	 */
	public Boolean getFlipToSilence() {
		return flipToSilence;
	}

	/**
	 * @param flipToSilence the flipToSilence to set
	 */
	public void setFlipToSilence(Boolean flipToSilence) {
		this.flipToSilence = flipToSilence;
	}
	
}
