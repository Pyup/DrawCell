package com.cmcc.hive.drawcelldistribution;

import java.util.*;
public class CustomerTrace {
	
	private String msisdn;
	private HashMap<String,Location> trace = new HashMap<String,Location>();
	
	public CustomerTrace(){
		
	}
	
	public CustomerTrace(String msisdn){
		this.msisdn = msisdn;
	}
	
	public void putPointInTraceMap(String formateddate,Location loc){
		trace.put(formateddate, loc);
	}

}

interface TraceFac{
	public CustomerTrace createTrace();
}

class CustomerTraceFac implements TraceFac{
	public CustomerTrace createTrace(){
		return new CustomerTrace();
	}
}