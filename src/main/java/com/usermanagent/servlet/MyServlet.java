package com.usermanagent.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.usermanagement.dao.UserDao;
import com.usermanagement.model.User;

@WebServlet("/")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String path = request.getServletPath();
		
		switch (path) 
		{
		case "/insert":
			insertUserData(request, response);
			break;
		case "/newForm":
			getNewFormPage(request, response);
			break;
		case "/edit":
			getEditForm(request, response);
			break;
		case "/update":
			updateUserData(request, response);
			break;
		case "/delete":
			deleteUserData(request, response);
			break;
		default:
			getStartUpPage(request, response);
			break;
		}
	}

	
	private void deleteUserData(HttpServletRequest request, HttpServletResponse response) 
	{
		// Read the id from URL
		int id = Integer.parseInt( request.getParameter("id") );
		
		// Call JDBC code from DAO
		UserDao.deleteUser(id);
		
		// Redirect user to List page with updated value:
		getStartUpPage(request, response);
	}


	private void updateUserData(HttpServletRequest request, HttpServletResponse response) 
	{
		// Read the data given in browser:
		int i = Integer.parseInt(request.getParameter("id"));
		String n = request.getParameter("name");
		String e = request.getParameter("email");
		String c = request.getParameter("country");
		
		// Store the above data in bean object:
		User u = new User(i, n, e, c);
		
		// Call the dao method and pass the above object
		UserDao.updateUser(u);
		
		// Redirect user to list page with updated value:
		try {
			response.sendRedirect("list");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


	private void getEditForm(HttpServletRequest request, HttpServletResponse response) 
	{
		try 
		{
			// Read the data[id] from url:
			int i = Integer.parseInt( request.getParameter("id") );
			
			User user = UserDao.getUserById(i);
			
			request.setAttribute("u", user);
			
			// Redirect user to user-form page
			RequestDispatcher rd  = request.getRequestDispatcher("user-form.jsp");
			rd.forward(request, response);
		} 
		catch (ServletException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	private void insertUserData(HttpServletRequest request, HttpServletResponse response) 
	{
		// Read the data from browser
		String n = request.getParameter("name");
		String e = request.getParameter("email");
		String c = request.getParameter("country");
		
		// Store the above data in bean object
		User user = new User(n, e, c);
		
		// call the dao [insertUser] method by passing above bean object
		UserDao.insertUser(user);
		
		// redirect user to list page
		try {
			response.sendRedirect("list");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}


	private void getNewFormPage(HttpServletRequest request, HttpServletResponse response) {
		// Redirect user to [user-form.jsp] page 
		try 
		{
			RequestDispatcher rd  = request.getRequestDispatcher("user-form.jsp");
			rd.forward(request, response);
		} 
		catch (ServletException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void getStartUpPage(HttpServletRequest request, HttpServletResponse response) 
	{
		// Redirect user to startup[user-list.jsp] page 
		try 
		{
			// call the dao methods which return all the user data:
			ArrayList<User> alUser = UserDao.displayAllUsers();
			
			// send the above collection to jsp page:
			request.setAttribute("users", alUser);
			
			RequestDispatcher rd  = request.getRequestDispatcher("user-list.jsp");
			rd.forward(request, response);
		} 
		catch (ServletException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
