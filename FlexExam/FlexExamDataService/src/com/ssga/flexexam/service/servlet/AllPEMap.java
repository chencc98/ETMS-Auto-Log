package com.ssga.flexexam.service.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssga.flexexam.service.dao.DAO;
import com.ssga.flexexam.service.entity.ProjectEmployeeMap;

/**
 * Servlet implementation class AllPEMap
 */
public class AllPEMap extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String out = "<pemaps>";
		DAO dao = new DAO();
		
		List<ProjectEmployeeMap> listm = dao.getAllPEMap();
		for( ProjectEmployeeMap cl : listm ){
			out += cl.toXMLString() + "\n";
		}
		
		out += "</pemaps>";
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
