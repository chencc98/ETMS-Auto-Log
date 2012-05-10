package com.ssga.flexexam.service.servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssga.flexexam.service.dao.DAO;


/**
 * Servlet implementation class EditProject
 */
public class EditProject extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String out = "";
		String pid = request.getParameter("projectid");
		String progress = request.getParameter("progress");
		String employeelist = request.getParameter("employees");
		DAO dao = new DAO();
		dao.updateProject(pid, progress, employeelist);
		
		out += "SUCCESS";
		dao.close();
		response.getWriter().write(out);
		
		System.out.println("debug:" + "pid="+pid + ",pro="+progress+",list="+employeelist);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
