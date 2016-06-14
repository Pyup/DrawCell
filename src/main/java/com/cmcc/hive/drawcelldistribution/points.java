package com.cmcc.hive.drawcelldistribution;

public class points {
	
	private String msisdn;
	private String formateddate;
	private Location loc;
	
	public points(){
		
	}
	public points(String msisdn, String formateddate, Location loc){
		this.msisdn = msisdn;
		this.formateddate = formateddate;
		this.loc = loc;
	}

}

class Location{
	
	private Double longitude;
	private Double latitude;
	
	@Override
	public String toString(){
		return longitude.toString() + " " + latitude.toString();
	}
	
	public Location(){
		
	}
	
	public Location(Double longtitude,Double latitude){
		this.longitude = longtitude;
		this.latitude = latitude;
	}
	
	public Double getLongitude(){
		return longitude;
	}
	
	public Double getLatitude(){
		return latitude;
	}
	
	
}

interface Fac{
	public Location createLoc(Double longitude, Double latitude);
	public points createPoi(String msisdn,String formateddate,Location loc);
}

class ConcreteLocationFac implements Fac{
	public Location createLoc(Double longitude, Double latitude){
		return new Location(longitude,latitude);
	}
	public points createPoi(String msisdn,String formateddate,Location loc){
		return new points(msisdn,formateddate,loc);
	}
}
