package com.cmcc.hive.drawcelldistribution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.erichseifert.gral.data.DataTable;
import com.cmcc.hive.drawcelldistribution.gralPaintPosition;
import com.cmcc.hive.drawcelldistribution.CustomerTrace;
import com.cmcc.hive.drawcelldistribution.points;
import com.cmcc.hive.drawcelldistribution.ConstantParameters;
import java.util.*;
import java.math.BigDecimal;

public class DrawCell{
	
	public DrawCell(){
		
	}
	
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";
	private static String hiveUrl = "jdbc:hive2://localhost:10000/default";
	private static String hiveUser = "wongkongtao";
	private static String hivePass = "199711";
	private static String sqlCmd = "";
	private static ResultSet rs = null;
	private static Map<Double,Double> customerPosition = null;
	private static Set<Map.Entry<Double, Double>> positonSet = null;
	private static Connection conn = null;
	private static Statement stmt = null;
	private static String databaseName = null;
	private static String tableName = null;
	private static String viewName = null;
	private static DataTable dataCellPosition = null;
	private static HashMap<Location,points> pointsKeyByLocation = null;
	private static HashMap<String,points> pointsByMsisdn = null;
	
	
	public void DrawDataTable(DataTable dt){
		gralPaintPosition gp = new gralPaintPosition(dt);
		gp.showInFrame();
	}
	
	public void ConnectHiveBase() throws ClassNotFoundException,SQLException{
		Class.forName(driverName);
		conn = DriverManager.getConnection(hiveUrl, hiveUser, hivePass);
		stmt = conn.createStatement();
		databaseName = "cdr";
		tableName = "cs_cdr";
		viewName = "";
		sqlCmd = "SHOW DATABASES";
		rs = stmt.executeQuery(sqlCmd);
		while(rs.next()){
			System.out.println(rs.getString(1));
		}
		sqlCmd = "USE " + databaseName;
		stmt.execute(sqlCmd);
	}
	
	public void RetrieveResultSet() throws ClassNotFoundException,SQLException{
		sqlCmd = "SHOW TABLES";
		rs = stmt.executeQuery(sqlCmd);
		while(rs.next()){
			System.out.println(rs.getString(1));
		}
		viewName = "costumerpositon";
		sqlCmd = "SELECT * FROM " + viewName ;
		rs = stmt.executeQuery(sqlCmd);
		customerPosition = new HashMap<Double,Double>();
		Double tmplongitude;
		Double tmplatitude;
		while(rs.next()){
			tmplongitude = new Double(rs.getDouble(4));
			tmplatitude = new Double(rs.getDouble(5));
			customerPosition.put(tmplongitude,tmplatitude);
		}
		positonSet = customerPosition.entrySet();
		dataCellPosition = new DataTable(2,Double.class);
		for(Map.Entry<Double, Double> positionEntry : positonSet){
			dataCellPosition.add(positionEntry.getKey(),positionEntry.getValue());
		}
	}
	
	public void RetrieveResultSet(int inteast, int intwest, int intsouth, int intnorth) throws ClassNotFoundException,SQLException{
		
		BigDecimal gridleft = new BigDecimal(inteast);
		BigDecimal gridright = new BigDecimal(intwest);
		BigDecimal griddown = new BigDecimal(intsouth);
		BigDecimal gridup = new BigDecimal(intnorth);
		
		BigDecimal east = ConstantParameters.minlongitude.add(ConstantParameters.longitudestep.multiply(gridleft));
		BigDecimal west = ConstantParameters.minlongitude.add(ConstantParameters.longitudestep.multiply(gridright));
		BigDecimal south = ConstantParameters.minlatitude.add(ConstantParameters.latitudestep.multiply(griddown));
		BigDecimal north = ConstantParameters.minlatitude.add(ConstantParameters.latitudestep.multiply(gridup));
		viewName = "costumerpositon"; 
		sqlCmd = "SELECT * FROM " + viewName + " WHERE " + east.toString() + " < longitude < " + west.toString() + " AND " + south.toString() + " < latitude < " + north + " DISTRIBUTE BY msisdn " + "SORT BY formateddate ASC LIMIT 100";
		rs = stmt.executeQuery(sqlCmd);
		pointsKeyByLocation = new HashMap<Location,points>();
		pointsByMsisdn = new HashMap<String,points>();
		String tmpmsisdn;
		String tmpformateddate;
		String tmpcgi;
		Double tmplongitude;
		Double tmplatitude;
		Location tmploc;
		points tmppoi;
		Fac factory1 = new ConcreteLocationFac();
		while(rs.next()){
			tmpmsisdn = rs.getString(1);
			tmpformateddate = rs.getString(2);
			tmpcgi = rs.getString(3);
			tmplongitude = new Double(rs.getDouble(4));
			tmplatitude = new Double(rs.getDouble(5));
			tmploc = factory1.createLoc(tmplongitude, tmplatitude);
			tmppoi = factory1.createPoi(tmpmsisdn, tmpformateddate, tmploc);
			pointsKeyByLocation.put(tmploc,tmppoi);
			pointsByMsisdn.put(tmpmsisdn, tmppoi);
		}
	}
	
	public void getCustomerTrace(int inteast, int intwest, int intsouth, int intnorth) throws SQLException{
		BigDecimal gridleft = new BigDecimal(inteast);
		BigDecimal gridright = new BigDecimal(intwest);
		BigDecimal griddown = new BigDecimal(intsouth);
		BigDecimal gridup = new BigDecimal(intnorth);
		
		BigDecimal east = ConstantParameters.minlongitude.add(ConstantParameters.longitudestep.multiply(gridleft));
		BigDecimal west = ConstantParameters.minlongitude.add(ConstantParameters.longitudestep.multiply(gridright));
		BigDecimal south = ConstantParameters.minlatitude.add(ConstantParameters.latitudestep.multiply(griddown));
		BigDecimal north = ConstantParameters.minlatitude.add(ConstantParameters.latitudestep.multiply(gridup));
		
//		sqlCmd = "INSERT OVERWRITE TABLE testCustomer SELECT p.msisdn as customer,COUNT(*) as occurrence FROM costumerpositon p WHERE p.msisdn LIKE '86%' AND " + east.toString() + "<p.longitude<" + west.toString() + " AND " + south.toString() + "<p.latitude<" + north.toString() + " GROUP BY p.msisdn ORDER BY occurrence DESC,p.msisdn DESC LIMIT 100";
//		stmt.execute(sqlCmd);
//		sqlCmd  = "SELECT * FROM testCustomer tc JOIN costumerpositon cp ON cp.msisdn = tc.msisdn ORDER BY cp.msisdn,formateddate ASC";
		sqlCmd = "SELECT * FROM testTrace";
		rs = stmt.executeQuery(sqlCmd);
		String tmpmsisdn;
		String compareMsisdn = "used for comparison";
		String tmpformateddate;
		Double tmplongitude;
		Double tmplatitude;
		Location tmploc;
		TraceFac facTrace = new CustomerTraceFac();
		Fac factory1 = new ConcreteLocationFac();
		CustomerTrace tmpTrace = facTrace.createTrace();
		while(rs.next()){
			tmpmsisdn = rs.getString(3);
			if(!compareMsisdn.equals(tmpmsisdn)){
				tmpformateddate = rs.getString(4);
				tmplongitude = new Double(rs.getDouble(6));
				tmplatitude = new Double(rs.getDouble(7));
				tmploc = factory1.createLoc(tmplongitude, tmplatitude);
				tmpTrace = facTrace.createTrace();
				tmpTrace.setMsisdn(tmpmsisdn);
				tmpTrace.putPointInTraceMap(tmpformateddate, tmploc);
				compareMsisdn=tmpmsisdn;
				Trace.addTrace(tmpmsisdn, tmpTrace);
			}
			else{
				tmpformateddate = rs.getString(4);
				tmplongitude = new Double(rs.getDouble(6)); 
				tmplatitude = new Double(rs.getDouble(7));
				tmploc = factory1.createLoc(tmplongitude, tmplatitude);
				tmpTrace.setMsisdn(tmpmsisdn);
				tmpTrace.putPointInTraceMap(tmpformateddate, tmploc);
				compareMsisdn=tmpmsisdn; 
			}
		}
	}
	
	public static void main(String[] args){	
		
	try{
				DrawCell dc = new DrawCell();
				dc.ConnectHiveBase();
//				dc.RetrieveResultSet();
//				dc.DrawDataTable(dataCellPosition);				
//				dc.RetrieveResultSet(49, 50, 49, 50);
				 dc.getCustomerTrace(49,50,49,50);
				 CustomerTrace ct = null;
				 HashMap<String,Location> tmpMap = null;
				 Set<String> msisdnSet = Trace.getCustomerTraceMap().keySet();
				 for (String msisdn : msisdnSet){
					 ct = Trace.getCustomerTraceMap().get(msisdn);
					 tmpMap = ct.getTimeTrace();
					 for(String time : tmpMap.keySet()){
						 System.out.println(msisdn + " " + time + " " + tmpMap.get(time));
					 }
				 }
					PaintTrace pt = new PaintTrace();
					pt.showInFrame();
										
		}catch(ClassNotFoundException e){
			e.printStackTrace();  
            System.exit(1);  
		}catch (SQLException e) {  
            e.printStackTrace();  
            System.exit(1);  
    } 
		System.out.println("Hello Hive");
	}

}

