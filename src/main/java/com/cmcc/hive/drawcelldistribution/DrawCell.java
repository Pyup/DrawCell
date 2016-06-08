package com.cmcc.hive.drawcelldistribution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.erichseifert.gral.data.DataTable;
import com.cmcc.hive.drawcelldistribution.gralPaintPosition;
import java.util.*;

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
	
/*	private class InnerPaint extends JFrame{
		public static final long serialVersionUID = 2l;
		private cellPanel cp  = null;
		public InnerPaint(){
			cp = new cellPanel();
			this.add(cp);
			this.setSize(800,800);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);
		}
		
	}
	
	private class cellPanel extends JPanel{
		public static final long serialVersionUID = 1l;
		public void paint(Graphics g){
			super.paint(g);
			for(Map.Entry<Double, Double> positionEntry : positonSet){
				g.drawOval(Double2Int(positionEntry.getKey()),Double2Int(positionEntry.getValue()), 10, 10);
				g.setColor(Color.BLUE);
				g.fillOval(Double2Int(positionEntry.getKey()),Double2Int(positionEntry.getValue()),10,10);
			}
		}
		public int Double2Int(Double doubleNum){
			String tmpString = doubleNum.toString();
			String intString = tmpString.substring(0, tmpString.indexOf(".")) + tmpString.substring(tmpString.indexOf(".")+1);
			return Integer.parseInt(intString);
		}
	}
	
	public InnerPaint getInnerPaint(){
		return new InnerPaint();
	}*/
	
	
	public static void main(String[] args){
		
	try{
			Class.forName(driverName);
				Connection conn = DriverManager.getConnection(hiveUrl, hiveUser, hivePass);
				Statement stmt = conn.createStatement();
				String databaseName = "cdr";
				String tableName = "cs_cdr";
				String viewName = "";
				sqlCmd = "SHOW DATABASES";
				rs = stmt.executeQuery(sqlCmd);
				while(rs.next()){
					System.out.println(rs.getString(1));
				}
				sqlCmd = "USE " + databaseName;
				stmt.execute(sqlCmd);
				sqlCmd = "SHOW TABLES";
				rs = stmt.executeQuery(sqlCmd);
				while(rs.next()){
					System.out.println(rs.getString(1));
				}
				viewName = "costumerpositon";
				sqlCmd = "SELECT * FROM " + viewName;
				rs = stmt.executeQuery(sqlCmd);
				customerPosition = new HashMap<Double,Double>();
				while(rs.next()){
//					System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5));
					customerPosition.put(new Double(rs.getDouble(4)),new Double(rs.getDouble(5)));
				}
				positonSet = customerPosition.entrySet();
				DataTable data = new DataTable(Double.class,Double.class);
				for(Map.Entry<Double, Double> positionEntry : positonSet){
					data.add(positionEntry.getKey(),positionEntry.getValue());
//					System.out.println(positionEntry.toString());
				}
				gralPaintPosition gp = new gralPaintPosition(data);
				gp.showInFrame();
													
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

