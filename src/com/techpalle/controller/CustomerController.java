package com.techpalle.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techpalle.dao.CustomerDao;
import com.techpalle.model.Customer;

@WebServlet("/")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String path=request.getServletPath();
		
		switch (path)
		{
		case "/deleteForm":
			deleteCustomer(request, response);
			break;
		case "/EC":
			editCustomer(request, response);
			break;
		case "/editForm":
			getEditForm(request, response);
			break;
		case "/ADD":
			addCustomer(request, response);
			break;
		case "/IC":
			insertCustomer(request, response);
			break;
		case "/CL":
			validateAdmin(request, response);
			break;
		
			
		default:
			getStartUpPage(request, response);
			break;
		}
	}


	private void validateAdmin(HttpServletRequest request, HttpServletResponse response) 
	{
		String name=request.getParameter("tbun");
		String password=request.getParameter("tbpw");
		boolean b=CustomerDao.adminValidate(name, password);
		if(b==true)
		{
		    getCustomerListPage(request, response);
		}
		else
			getStartUpPage(request, response);
	}

	private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) 
	{
		// read the id from url
		int i=Integer.parseInt(request.getParameter("id"));
		
		//call teh dao method to delet the roe in the database
		CustomerDao.deleteCustomer(i);
		//redirect user to customer list page
		getCustomerListPage(request, response);
		
	}

	private void editCustomer(HttpServletRequest request, HttpServletResponse response)
	{
		int i=Integer.parseInt(request.getParameter("id"));
		String n=request.getParameter("tbname");
		String e=request.getParameter("tbemail");
		long m=Long.parseLong(request.getParameter("tbmobile"));
		Customer c=new Customer(i,n, e, m);
		CustomerDao.updateCustomer(c);
		
		getCustomerListPage(request, response);
	}

	private void getEditForm(HttpServletRequest request, HttpServletResponse response) 
	{
        //Fetch the id from url:
		int i=Integer.parseInt(request.getParameter("id"));
		Customer c=CustomerDao.getOneCustomer(i);
		
		//getCustomerListPage(request, response);
		try 
		{
			RequestDispatcher rd=request.getRequestDispatcher("customer-form.jsp");
			request.setAttribute("customer", c);
			rd.forward(request, response);
		} 
		catch (ServletException | IOException e1) 
		{
			e1.printStackTrace();
		}
	}

	private void addCustomer(HttpServletRequest request, HttpServletResponse response) 
	{
		try 
		{
			RequestDispatcher rd=request.getRequestDispatcher("customer-form.jsp");
			rd.forward(request, response);
		} 
		catch (ServletException | IOException e1) 
		{
			e1.printStackTrace();
		}
		
	}

	private void insertCustomer(HttpServletRequest request, HttpServletResponse response)
	{
		
		String n=request.getParameter("tbname");
		String e=request.getParameter("tbemail");
		long m=Long.parseLong(request.getParameter("tbmobile"));
		Customer c=new Customer(n, e, m);
		CustomerDao.insertCustomer(c);
		
		getCustomerListPage(request, response);
	}

	private void getStartUpPage(HttpServletRequest request, HttpServletResponse response) 
	{
        
		try
		{
			RequestDispatcher rd=request.getRequestDispatcher("Admin-login.jsp");
			rd.forward(request, response);
		}
		catch (ServletException | IOException e) 
		{
			e.printStackTrace();
		}
		
		
	}
	private void getCustomerListPage(HttpServletRequest request, HttpServletResponse response)
	{
		 try
	      {
          ArrayList<Customer> alCustomer=CustomerDao.getAllCustomers();
          
       	  RequestDispatcher rd= request.getRequestDispatcher("customer-list.jsp");
       	  request.setAttribute("al",alCustomer);
			rd.forward(request, response);
		  } 
         catch (ServletException | IOException e1) 
         {
			e1.printStackTrace();
		  }
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
