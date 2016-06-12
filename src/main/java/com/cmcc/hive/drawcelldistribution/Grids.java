package com.cmcc.hive.drawcelldistribution;

import java.util.*;
import com.cmcc.hive.drawcelldistribution.points;

public class Grids {
	
	private HashMap<Location,points> pointsInGridKeyLocation = new HashMap();
	private HashMap<String,points> pointsInGridKeyPeople = new HashMap();
	
	private String longitudeEast;
	private String longitudeWest;
	private String lactitudeSouth;
	private String lactitudeNorth;
	
	public Grids(){
		
	}
	
	public Grids(String longtitudeEast, String longitudeWest, String lactitudeSouth, String lactitudeNorth){
		this.longitudeEast = longtitudeEast;
		this.longitudeWest = longitudeWest;
		this.lactitudeSouth = lactitudeSouth;
	    this.lactitudeNorth = lactitudeNorth;
	}

}
