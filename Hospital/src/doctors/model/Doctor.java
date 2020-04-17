package doctors.model;

import java.sql.*;

import dbconnector.DBConnect;

public class Doctor {
	
	public String insertDoctor(String docID, String hospitalName, String docName, int age, String spec, String arrive, String leave)
	{
		String output = "";
		
		try
		{
			DBConnect db = new DBConnect();
			Connection con = null;
			con = db.connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for inserting."; 
			}
			
			String query1 = "select hospitalName from hospital";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query1);
			
			if(rs.next()){
				hospitalName = rs.getString("hospitalName");
			}
			
			// create a prepared statement
			String query = " insert into doctors(`DoctorID`, `HospitalName`, `DoctorName`, `Age`, `Specialization`, `ArriveTime`, `LeaveTime`)"+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
				
				// binding values
			preparedStmt.setString(1, docID);
			preparedStmt.setString(2, hospitalName);
			preparedStmt.setString(3, docName);
				
				
			//if(age > 25 && age < 70) 
			//{
				preparedStmt.setInt(4, age);
			//}
			//else
			//{
				//System.out.println("Age is not valid");
			//}
				
			preparedStmt.setString(5, spec);
			preparedStmt.setString(6, arrive);
			preparedStmt.setString(7, leave);
			
			
	
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		}
		catch (Exception e)
		{
			output = "Error while inserting the doctor.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	
	
	public String readDoctors()
	{
		String output = "";
		
		try
		{
			DBConnect db = new DBConnect();
			Connection con = null;
			con = db.connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for reading."; 
			}
			
			// Prepare the html table to be displayed
			output = "<table border=\"1\">"
					+ "<tr><th>Doctor ID</th>"
					+ "<th>Hospital Name</th>"
					+ "<th>Doctor Name</th>"
					+ "<th>Age</th>"
					+ "<th>Specialization</th>"
					+ "<th>ArriveTime</th>"
					+ "<th>LeaveTime</th>"
					+ "<th>Update</th>"
					+ "<th>Remove</th></tr>";
			
			String query = "select * from doctors";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			
			while (rs.next())
			{
				String docID = rs.getString("DoctorID");
				String hospitalName = rs.getString("HospitalName");
				String docName = rs.getString("DoctorName");
				String age = Integer.toString(rs.getInt("Age"));
				String spec = rs.getString("Specialization");
				String arrive = rs.getString("ArriveTime");
				String leave = rs.getString("LeaveTime");
				
				// Add into the html table
				output += "<tr><td>" + docID + "</td>";
				output += "<td>" + hospitalName + "</td>";
				output += "<td>" + docName + "</td>";
				output += "<td>" + age + "</td>";
				output += "<td>" + spec + "</td>";
				output += "<td>" + arrive + "</td>";
				output += "<td>" + leave + "</td>";
				
				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>"
				+ "<td><form method=\"post\" action=\"items.jsp\">"
				+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
				+ "<input name=\"ID\" type=\"hidden\" value=\"" + docID + "\">" 
				+ "</form></td></tr>";
			}
			
			con.close();
			
			// Complete the html table
			output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the doctor.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	
	
	public String updateDoctor(String docID, String hospitalName, String docName, int age, String spec, String arrive, String leave)
	{
		String output = "";
		
		try
		{
			DBConnect db = new DBConnect();
			Connection con = null;
			con = db.connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for updating.";
			}
			
			String query1 = "select HospitalName from hospital";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query1);
			
			if(rs.next()){
				hospitalName = rs.getString("HospitalName");
			}
			
			// create a prepared statement
			String query = "UPDATE doctors SET HospitalName = ?, DoctorName = ? , Age = ?, Specialization = ?, ArriveTime = ?, LeaveTime = ? WHERE DoctorID = ?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, hospitalName);
			preparedStmt.setString(2, docName);
			preparedStmt.setInt(3, age);
			preparedStmt.setString(4, spec);
			preparedStmt.setString(5, arrive);
			preparedStmt.setString(6, leave);
			preparedStmt.setString(7, docID);
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			output = "Updated successfully";
		}
		catch (Exception e)
		{
			output = "Error while updating the doctor.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	
	
	public String deleteDoctor(String docID)
	{
		String output = "";
		try
		{
			DBConnect db = new DBConnect();
			Connection con = null;
			con = db.connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for deleting."; 
			}
			
			// create a prepared statement
			String query = "delete from doctors where DoctorID = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, docID);
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			output = "Deleted successfully";
		}
		catch (Exception e)
		{
			output = "Error while deleting the doctor.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
}
