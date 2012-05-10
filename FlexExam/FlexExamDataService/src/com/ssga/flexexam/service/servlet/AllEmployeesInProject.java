package com.ssga.flexexam.service.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssga.flexexam.service.dao.DAO;
import com.ssga.flexexam.service.entity.Employee;

/**
 * Servlet implementation class AllEmployeesInProject
 */
public class AllEmployeesInProject extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String out = "<employees>";
		String pid = request.getParameter("projectid");
		DAO dao = new DAO();
		List<Employee> liste = dao.getEmployee4Project(pid);
		for( Employee cl : liste ){
			out += cl.toXMLString() + "\n";
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

}
