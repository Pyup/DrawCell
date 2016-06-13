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
	
	public void setMsisdn(String msisdn){
		this.msisdn = msisdn;
	}
	
	public void putPointInTraceMap(String formateddate,Location loc){
		trace.put(formateddate, loc);
	}
	
	public HashMap<String,Location> getTimeTrace(){
		return trace;
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

class Trace{
	private static HashMap<String,CustomerTrace> customerTraceMap = new HashMap<String,CustomerTrace>();
	public static void addTrace(String msisdn,CustomerTrace tr){
		customerTraceMap.put(msisdn, tr);
	}
	
	public static HashMap<String,CustomerTrace> getCustomerTraceMap(){
		return customerTraceMap;
	}
}