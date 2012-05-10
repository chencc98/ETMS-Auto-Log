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
import com.ssga.flexexam.service.entity.EmployeeStatus;
import com.ssga.flexexam.service.entity.Project;
import com.ssga.flexexam.service.entity.ProjectEmployeeMap;
import com.ssga.flexexam.service.entity.ProjectStatus;

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
	
	public void updateProject(String pid, String progress, String employees){
		String projectstatus = determineProjectStatus(progress, employees);
		String sqlproject = "update projects set progress =" + progress
			+ ",status='" + projectstatus + "' where id='" + pid + "'";
		String currentlist = getEmployeeIDlistInProject(pid);
		List<String> mapsql = getMapSQL(currentlist, employees, pid);
		
		try{
			Statement st = conn.createStatement();
			st.execute(sqlproject);
			for(String sqlmap : mapsql ){
				st.execute(sqlmap);
			}
			
			List<String>  allemployees = combineList(currentlist, employees);
			List<String>  employeesql = getEmployeeStatusSQL(allemployees);
			for( String empsql : employeesql){
				st.execute(empsql);
			}
			st.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		
	}
	
	private List<String> getEmployeeStatusSQL(List<String> list){
		List<String> sqls = new ArrayList<String>();
		for(String emid : list) {
			String status = getEmployeeStatus(emid);
			String newstatus = getEmployeeNewStatus(emid);
			if( !status.equals(newstatus)){
				sqls.add("update employees set status='" + newstatus + "' where id='"
						+ emid + "'");
			}
		}
		return sqls;
	}
	
	public String getEmployeeStatus(String emid){
		String sql = "select * from employees where id='" + emid + "'";
		String status = EmployeeStatus.UNDERLOAD;
		try{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if( rs.next()){
				status =  rs.getString("status");
				System.out.println("debug: emid="+emid+",status="+status);
			}
			rs.close();
			st.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return status;
	}
	public String getEmployeeNewStatus(String emid){
		String sql = "select * from projects, employees,project_employee_map "
			+ "where [projects].[id] = project and [employees].[id] = employee "
			+ " and employee ='" + emid + "' and [projects].[status]='"
			+ ProjectStatus.ONGOING + "'";
		int count = 0;
		try{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while( rs.next()){
				count++;
			}
			rs.close();
			st.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		String status = "";
		if( count == 0 ){
			status = EmployeeStatus.UNDERLOAD;
		}else if( count >=1 && count <5){
			status = EmployeeStatus.NORMAL;
		}else{
			status = EmployeeStatus.OVERLOAD;
		}
		return status;
		
	}
	
	private List<String> combineList(String list1, String list2){
		List<String>  re = new ArrayList<String>();
		if( list1.equals("") ){
			list1 = list2;
		}else {
			list1 = list2.equals("") ? list1 : list1+","+list2;
		}
		if( list1.equals("")){
			return re;
		}else{
			String [] sp = list1.split(",");
			int len = sp.length;
			for(int i =0 ;i < len; i++){
				re.add(sp[i]);
			}
			return re;
		}
	}
	
	private List<String> getMapSQL(String current, String newmap, String pid){
		List<String> sqls = new ArrayList<String>();
		if( newmap.equals("") && current.equals("")){
			return sqls;
		}else if ( newmap.equals("") && !current.equals("")){
			String [] list = current.split(",");
			int len = list.length;
			for( int i = 0; i< len; i++){
				sqls.add(getMapDeleteSQL(pid, list[i]));
			}
			return sqls;
		}else if( !newmap.equals("") && current.equals("")){
			String [] list = newmap.split(",");
			int len = list.length;
			for( int i = 0; i< len; i++){
				sqls.add(getMapInsertSQL(pid, list[i]));
			}
			return sqls;
		}else{
			String [] list = newmap.split(",");
			int len = list.length;
			String [] cur = current.split(",");
			int curlen = cur.length;
			List<String> currentlist = new ArrayList<String>();
			for( int i = 0; i< curlen; i++){
				currentlist.add(cur[i]);
			}
			
			for( int i=0 ; i< len ; i++){
				if( currentlist.contains(list[i])){
					currentlist.remove(list[i]);
				}else{
					sqls.add(getMapInsertSQL(pid, list[i]));
				}
			}
			
			for( String willdel : currentlist){
				sqls.add(getMapDeleteSQL(pid, willdel));
			}
		}
		return sqls;
	}
	private String getMapDeleteSQL( String pid, String emid){
		String sql = "delete from project_employee_map where project='"
			+ pid + "' and employee='" + emid + "'";
		return sql;
	}
	private String getMapInsertSQL(String pid, String emid){
		String sql = "insert into project_employee_map values('" + pid + "','"
			+ emid + "')";
		return sql;
	}
	private String determineProjectStatus(String progress, String employeelist){
		int pro = Integer.parseInt(progress);
		if( pro == 100){
			return ProjectStatus.COMPLETE;
		}else{
			if( employeelist.equals("")){
				return ProjectStatus.SUSPENDED;
			}else{
				return ProjectStatus.ONGOING;
			}
		}
	}
	
	public String getEmployeeIDlistInProject(String pid){
		String sql = "select * from project_employee_map "
			+ " where project = '" + pid + "'";
		List<String> list = new ArrayList<String>();
		try{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while( rs.next() ){
				String id = rs.getString("employee");
				
				list.add(id);
			}
			rs.close();
			st.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		if( list.size() == 0){
			return "";
		}else{
			return List2String(list);
		}
	}
	
	private String List2String(List<String> array){
		
		String str= "";
		for( String s : array){
			str += s + ",";
		}
		return str.endsWith(",") ? str.substring(0, str.length()-1) : str;
	}
	
	
	public void close(){
		try{
			this.conn.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public List<Project> getProjectsForEmployee( String emid){
		String sql = "select * from projects, project_employee_map where "
			+ "id = project and employee='" + emid + "'";
		List<Project> list = new ArrayList<Project>();
		try{
			Statement st = conn.createStatement();
			ResultSet rs =st.executeQuery(sql);
			while( rs.next()){
				Project p = new Project();
				p.setId(rs.getString("id"));
				p.setName(rs.getString("name"));
				p.setClient(rs.getString("client"));
				p.setProgress(rs.getInt("progress"));
				p.setStatus(rs.getString("status"));
				list.add(p);
			}
			rs.close();
			st.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return list;
	}

}
