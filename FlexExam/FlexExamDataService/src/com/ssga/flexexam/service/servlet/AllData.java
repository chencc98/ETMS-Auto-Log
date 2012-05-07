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
import com.ssga.flexexam.service.entity.ProjectEmployeeMap;

/**
 * Servlet implementation class AllData
 */
public class AllData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String out = "<data>" + "\n";
		DAO dao = new DAO();
		List<Client> listc = dao.getAllClients();
		for( Client cl : listc ){
			out += cl.toXMLString() + "\n";
		}
		
		List<Employee> liste = dao.getAllEmployees();
		for( Employee cl : liste ){
			out += cl.toXMLString() + "\n";
		}
		
		List<Project> listp = dao.getAllProjects();
		for( Project cl : listp ){
			out += cl.toXMLString() + "\n";
		}
		
		List<ProjectEmployeeMap> listm = dao.getAllPEMap();
		for( ProjectEmployeeMap cl : listm ){
			out += cl.toXMLString() + "\n";
		}
		
		out += "</data>";
		response.getWriter().write(out);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
