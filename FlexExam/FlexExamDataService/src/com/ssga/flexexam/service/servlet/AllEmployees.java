package com.ssga.flexexam.service.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssga.flexexam.service.dao.DAO;
import com.ssga.flexexam.service.entity.Employee;
import com.ssga.flexexam.service.entity.Project;
import com.ssga.flexexam.service.entity.ProjectStatus;

/**
 * Servlet implementation class AllEmployees
 */
public class AllEmployees extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String out = "<employees>";
		DAO dao = new DAO();
		List<Employee> liste = dao.getAllEmployees();
		for( Employee cl : liste ){
			out += "<employee>" + "<id>" + cl.getId() + "</id>";
			out += "<name>" + cl.getName() + "</name>";
			out += "<status>" + cl.getStatus() + "</status>";
			List<Project> list = dao.getProjectsForEmployee(cl.getId());
			out += "<ongoing>" + getProjectString(ProjectStatus.ONGOING, list) + "</ongoing>";
			out += "<complete>" + getProjectString(ProjectStatus.COMPLETE, list)
				+ "</complete></employee> \n";
		}
		
		out += "</employees>";
		dao.close();
		response.getWriter().write(out);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String getProjectString(String type, List<Project> list){
		String re = "";
		for( Project p : list){
			if( p.getStatus().equals(type)){
				re += p.getName() + ",";
			}
		}
		return re.endsWith(",") ? re.substring(0, re.length()-1) : re ;
	}

}
