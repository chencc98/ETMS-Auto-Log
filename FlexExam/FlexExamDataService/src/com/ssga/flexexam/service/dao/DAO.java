/**
 * 
 */
package com.ssga.flexexam.service.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ssga.flexexam.service.entity.Client;
import com.ssga.flexexam.service.entity.Employee;
import com.ssga.flexexam.service.entity.Project;
import com.ssga.flexexam.service.entity.ProjectEmployeeMap;

/**
 * @author e464150
 *
 */
public class DAO {
	
	private String mdbfile = "C:\\DATA\\PERSONAL\\Code\\egit\\ETMS-Auto-Log\\FlexExam\\FlexExamDataService\\test.mdb";
	private Connection conn;
	
	public DAO(){
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String dburl = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="+this.mdbfile;
			conn = DriverManager.getConnection(dburl);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public List<Client> getAllClients(){
		String sql = "select * from clients";
		List<Client> list = new ArrayList<Client>();
		try{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while( rs.next() ){
				Client cl = new Client();
				cl.setId(rs.getString("id"));
				cl.setName(rs.getString("name"));
				list.add(cl);
				
			}
			rs.close();
			st.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return list;
	}
	
	public List<Employee> getAllEmployees(){
		String sql = "select * from employees";
		List<Employee> list = new ArrayList<Employee>();
		try{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while( rs.next() ){
				Employee cl = new Employee();
				cl.setId(rs.getString("id"));
				cl.setName(rs.getString("name"));
				cl.setStatus(rs.getString("status"));
				list.add(cl);
				
			}
			rs.close();
			st.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return list;
	}
	
	public List<Project> getAllProjects(){
		String sql = "select * from projects";
		List<Project> list = new ArrayList<Project>();
		try{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while( rs.next() ){
				Project cl = new Project();
				cl.setId(rs.getString("id"));
				cl.setName(rs.getString("name"));
				cl.setClient(rs.getString("client"));
				cl.setProgress(rs.getInt("progress"));
				cl.setStatus(rs.getString("status"));
				list.add(cl);
				
			}
			rs.close();
			st.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return list;
	}
	
	public List<ProjectEmployeeMap> getAllPEMap(){
		String sql = "select * from project_employee_map";
		List<ProjectEmployeeMap> list = new ArrayList<ProjectEmployeeMap>();
		try{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while( rs.next() ){
				ProjectEmployeeMap cl = new ProjectEmployeeMap();
				cl.setProject(rs.getString("project"));
				cl.setEmployee(rs.getString("employee"));
				
				list.add(cl);
				
			}
			rs.close();
			st.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return list;
	}
	
	public Client getClientById(String cid){
		String sql = "select * from clients where id = '" + cid + "'";
		Client client = null;
		try{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if( rs.next() ){
				 client = new Client();
				client.setId(rs.getString("id"));
				client.setName(rs.getString("name"));
			}
			rs.close();
			st.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return client;
	}
	
	public List<Employee> getEmployee4Project(String pid){
		String sql = "select * from employees, project_employee_map "
			+ " where employee = id "
			+ " and project = '" + pid + "'";
		List<Employee> list = new ArrayList<Employee>();
		try{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while( rs.next() ){
				Employee em = new Employee();
				em.setId(rs.getString("id"));
				em.setName(rs.getString("name"));
				em.setStatus(rs.getString("status"));
				list.add(em);
			}
			rs.close();
			st.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return list;
	}
	
	
	public void close(){
		try{
			this.conn.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

}
