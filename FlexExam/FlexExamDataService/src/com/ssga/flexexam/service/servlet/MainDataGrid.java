package com.ssga.flexexam.service.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssga.flexexam.service.dao.DAO;
import com.ssga.flexexam.service.entity.Client;
import com.ssga.flexexam.service.entity.Employee;
import com.ssga.flexexam.service.entity.Project;

/**
 * Servlet implementation class MainDataGrid
 */
public class MainDataGrid extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String out = "<data>" + "\n";
		DAO dao = new DAO();
			
		List<Project> listp = dao.getAllProjects();
		for( Project cl : listp ){
			out += "<project>";
			out += "<id>" + cl.getId() + "</id>";
			out += "<name>" + cl.getName() + "</name>";
			out += "<progress>" + cl.getProgress() + "</progress>";
			out += "<status>" + cl.getStatus() + "</status>";
			Client client = dao.getClientById(cl.getClient());
			out += "<client>" + client.getName() + "</client>";
			
			List<Employee> list = dao.getEmployee4Project(cl.getId());
			String employeelist = "";
			for ( Employee em : list){
				employeelist += em.getName() + ",";
			}
			employeelist = employeelist.endsWith(",") ? employeelist.substring(0, employeelist.length()-1)
					: employeelist;
			out += "<employee>" + employeelist + "</employee>";
			out += "</project>" + "\n";
		}
		
				
		out += "</data>";
		response.getWriter().write(out);
		
		dao.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
