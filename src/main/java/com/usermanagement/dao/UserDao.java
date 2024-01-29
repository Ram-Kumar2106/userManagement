package com.usermanagement.dao;

import java.sql.*;
import java.util.ArrayList;

import com.usermanagement.model.User;

public class UserDao 
{
	private static final String dbUrl = "jdbc:mysql://localhost:3306/palle"; 
	private static final String dbUsername = "root";
	private static final String dbPassword = "sangu";
	
	private static Connection cn = null;
	private static Statement stm = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	
	private static final String insertQry = "insert into user(name, email, country) values (?,?,?)";
	private static final String updateQry = "update user set name=?, email=?, country=? where id=?";
	private static final String deleteQry = "delete from user where id = ?";
	private static final String readByIdQry = "select * from user where id = ?";
	private static final String readAllQry = "select * from user";
	
	private static Connection myDbConnection() 
	{
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			cn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cn;
	}
	
	public static void deleteUser(int id) 
	{
		try 
		{
			cn = myDbConnection();
			
			ps = cn.prepareStatement(deleteQry);
			ps.setInt(1, id);
			
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(cn != null) {
				try {
					cn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void updateUser(User u) 
	{
		try 
		{
			cn = myDbConnection();
			
			ps = cn.prepareStatement(updateQry);
			ps.setString(1, u.getName());
			ps.setString(2, u.getEmail());
			ps.setString(3, u.getCountry());
			ps.setInt(4, u.getId());
			
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(cn != null) {
				try {
					cn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static User getUserById(int id) 
	{
		User u = null;
		
		try 
		{
			cn = myDbConnection();
			
			ps = cn.prepareStatement(readByIdQry);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int i = rs.getInt("id");
				String n = rs.getString("name");
				String e = rs.getString("email");
				String c = rs.getString("country");
				
				u = new User(i, n, e, c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(cn != null) {
				try {
					cn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return u;
	}
	
	public static ArrayList<User> displayAllUsers()
	{
		ArrayList<User> al = new ArrayList<User>();

		try 
		{
			cn = myDbConnection();
			
			stm = cn.createStatement();
			
			rs = stm.executeQuery(readAllQry);
			
			while(rs.next()) {
				int i = rs.getInt("id");
				String n = rs.getString("name");
				String e = rs.getString("email");
				String c = rs.getString("country");
				
				User u = new User(i, n, e, c);
				
				al.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stm != null) {
				try {
					stm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(cn != null) {
				try {
					cn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return al;
	}
	
	
	public static void insertUser(User u)
	{
		try 
		{
			cn = myDbConnection();
			
			ps = cn.prepareStatement(insertQry);
			ps.setString(1, u.getName());
			ps.setString(2, u.getEmail());
			ps.setString(3, u.getCountry());
			
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(cn != null) {
				try {
					cn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}