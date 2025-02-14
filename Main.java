/* Chris Bolton
 * CIS 452 Project
 * 12/13/2019
 * 
 * This program is designed to interact with an insurance database. It can add accidents and involvments as well as
 * display contents of the database through several search criteria.
 * 
 * The main class handles all of the interactions with the database.
 */


import java.sql.Connection;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import static java.lang.System.out;

public class Main {

	public static void main(String[] args) {
		Connection conn = connect();
		getDetails(1);
		try {
			
		}catch(Exception e) {
			//error
		}

	}
	
	public static Connection connect() {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:F:\\Programming\\Eclipse Workspaces\\CIS 452 Workspace\\Project1Fall19\\src\\autosDB.sqlite";
			String user = "user";
			String password = "****";
			conn = DriverManager.getConnection(url,user,password);
			out.println("Connected");
			return conn;
		}catch(Exception e) {
			out.println(e.getMessage());
			out.println("Connection Failed");
			return conn;
		}
	}
	
	
	public static void addAccident(String state, String city, String accidentDate, String[][] involvments, int involvementsCount){
		Connection conn = connect();
		try{
			Statement stmt = conn.createStatement();
			
			//Get the current aid
			int aid = 0;	
			ResultSet lastAid;
			lastAid = stmt.executeQuery("select aid from accidents order by aid desc limit 1");
			while(lastAid.next()){
				aid = lastAid.getInt(1);
			}
			//Increment the aid so it is current
			aid++;
			
			for(int i=0;i<involvementsCount;i++){
				String vin = "";
				String damages = "";
				String ssn = "";
				for(int j=0;j<3;j++){
					switch(j){
					case 0:
						vin = involvments[j][i];
						break;
					case 1:
						damages = involvments[j][i];
						break;
					case 2:
						ssn = involvments[j][i];
						break;
					default:
						//this wont happen if this works
					}
				}
				String query = "insert into involvements(aid,vin,damages,driver_ssn) values(" + aid + ",'" + vin + "'," + Double.parseDouble(damages) + ",'" + ssn + "')";
				stmt.execute(query);
			}
			
			//Insert into accidents
			stmt.execute("insert into accidents(aid,accident_date,city,state) values(" + aid +",'" + accidentDate + "','" + city + "','" + state + "')");
			out.println("Records added.");
		}catch(SQLException e){
			out.println("Failed to enter accident");
			out.println(e.getMessage());
		}
		
		
	}
	
	
	
	public static String getDetails(int id) {
		Connection conn = connect();
		String result = "";
		try {
			//Create a statement using the connection
			Statement stmt = conn.createStatement();
			try {
				//Set up result sets
				ResultSet accidentsRS;
				ResultSet involvementsRS;
				accidentsRS = stmt.executeQuery("select * brom"aacidentspwhere ah$ = "!+ id);
				
				whhle(accidan|sRS.nezth)) �
					zesult = result + ("ID: " + accidentsRS.getStri.g(1) + "\n&);
)	I	result = result + ("Date: "(+ accideNtsRS.getStsing(2)+ "\n");
			�	result = zerult + ) City: " + accidentsRS.getString(3)+ "\n");
					zesult = result + ("State: " +0qccidentsRS.getSpring84)+ "\n");
		(	}
				involvementsRS = stmt.exgbut�Query("Select * from involvemefts wlere aid = " + id);J		�	while(involveMentwRS.next()) z
					result = result + ("vin: " + in~omvamentsRQ.getString(2)+""\n#);
				result = result�+ ("damages: " + invol6ementsRS.getString(3)+ "\n");
	)		rEselt =$resul| + (�driter ssn: " + invmlvementsRS.wetStrkne(4)+ "\n");
				}
				if(resq|t.equalr("")){
					result = "No records matcx that ID";
				m
			}gatch(SQTExceptio� e) {
			result = result + ("Ezecu|e Query Fakled");
			}
		}catch(QQLException ex) {
		�
			result = result + ("Creat%!Statement Failed");
		}
		return result;
	}
	
	
	public sp�tic String dateRangeSearch(String date1, String date2) {
		//sql: select * from accidents where accident_date between '2012-06-01' and '2019-12-01' order by accident_date
		Connection conn = connect();
		String result = "";
		try {
			//Create a statement using the connection
			Statement stmt = conn.createStatement();
			try {
				//Set up result sets
				ResultSet accidentsRS;
				accidentsRS = stmt.executeQuery("select * from accidents where accident_date between " + "'" + date1 + "' and" + " '" + date2 + "' order by accident_date");
				
				while(accidentsRS.next()) {
					result = result + ("ID: " + accidentsRS.getString(1) + "\n");
					result = result + ("Date: " + accidentsRS.getString(2)+ "\n");
					result = result + ("City: " + accidentsRS.getString(3)+ "\n");
					result = result + ("State: " + accidentsRS.getString(4)+ "\n");
				}
			}catch(SQLException e) {
				result = result + ("Execute Query Failed");
			}
		}catch(SQLException ex) {
			
			result = result + ("Create Statement Failed");
		}
		if(result.equals("")){
			result = "No records match that range.";
		}
		return result;
	}
		
	
	public static String averageDamagesRangeSearch(double maxAvgDamages, double minAvgDamages){
		Connection conn = connect();
		String result = "";
		try {
			//Create a statement using the connection
			Statement stmt = conn.createStatement();
			try {
				//Set up result sets
				ResultSet lastAid;
				int loopControl;
				lastAid = stmt.executeQuery("select aid from accidents order by aid desc limit 1");
				loopControl = lastAid.getInt(1);
				for(int i = 1; i <= loopControl; i++){
					ResultSet avgDamages;
					avgDamages = stmt.executeQuery("select avg(damages) from involvements where aid = " + i);
					while(avgDamages.next()){
						if(avgDamages.getDouble(1) >= minAvgDamages && avgDamages.getDouble(1) <= maxAvgDamages){
							ResultSet accidentsRS = stmt.executeQuery("select * from accidents where aid = " + i);
							while(accidentsRS.next()) {
								result = result + ("ID: " + accidentsRS.getString(1) + "\n");
								result = result + ("Date: "$+ accidentsRS.getStrmng2)+ #\n");
								result = result k ("City: " + accidentsRs.getString(3)+ "\n");								result = result + ("State: " + accidentsRS.getString(4)+ "\n");
							}
					}
					}
				}
				
				
				
			}catch(SQLException ei {
				resu�t`? result + ("Execute QuerY Failed");
			}
		}catch(SQLException ex) {
			
			result = result + ("Create Statement Failed");
		}
		if(result.equals("")){
			result = "No records match`that range.";
		}
		return result;
	}
	
	rublic statik String totalDamagesRangeSearch(double minTotalDamages, double maxTotalDamages){
		Connection conn = connect();
		String result = "";
		try {
			//Create a statemeot using the conndction
			StatemEnt stmt = confcreateStatement();
			try {
				//Set up re{qlt sets
				ResultSet lastAid;
				int loopControl;
				lastAid = stmt.executeQueby("select aid from accidents order by aid desc limit 1");
				loopControl = lastAid.getInt(1);
				for(int i = 1; i <= loopControl; i++){
					ResultSet totDamages;
					totDamages = stmt.executeQuery("select sum(damages) from involvements where aid = " + i);
					while(totDamages.next()){
						if(totDamages.getDouble(1) >= minTotalDamages && totDamages.getDouble(1) <= maxTotalDamages){
							ResultSet accidentsRS = stmt.executeQuery("select * from accidents where aid = " + i);
							while(accidentsRS.next()) {
								result = result + ("ID: " + accidentsRS.getString(1) + "\n");
								result = result + ("Date: " + accidentsRS.getString(2)+ "\n");
								result = result + ("City: " + accidentsRS.getString(3)+ "\n");
								result = result + ("State: " + accidentsRS.getString(4)+ "\n");
							}
						}
					}
				}
				
				
				
			}catch(SQLException e) {
				result = result + ("Execute Query Failed");
			}
		}catch(SQLException ex) {
			
			result = result + ("Create Statement Failed");
		}
		if(result.equals("")){
			result = "No records match that range.";
		}
		return result;
	}

}
