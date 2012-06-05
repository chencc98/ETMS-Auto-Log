package com.ssga.flexexam.service.flexgateway;

import java.util.ArrayList;
import java.util.List;

import com.ssga.flexexam.service.dao.DAO;
import com.ssga.flexexam.service.entity.AllEmployeeSelector;
import com.ssga.flexexam.service.entity.Client;
import com.ssga.flexexam.service.entity.Employee;
import com.ssga.flexexam.service.entity.MainPanelProject;
import com.ssga.flexexam.service.entity.ProjectStatus;

import com.ssga.flexexam.service.entity.Project;

public class FlexExamServiceGateway {
	public List<MainPanelProject> loadMainEmployee(){
		DAO dao = new DAO();
		List<MainPanelProject> pd = new ArrayList<MainPanelProject>();
		List<Project> listp = dao.getAllProjects();
		for( Project cl : listp ){
			MainPanelProject mpp = new MainPanelProject();
			mpp.id = cl.getId();
			mpp.name = cl.getName();
			mpp.progress = cl.getProgress();
			mpp.status = cl.getStatus();
			
			Client client = dao.getClientById(cl.getClient());
			mpp.client = client.getName();
			
			
			List<Employee> list = dao.getEmployee4Project(cl.getId());
			String employeelist = "";
			for ( Employee em : list){
				employeelist += em.getName() + ",";
			}
			employeelist = employeelist.endsWith(",") ? employeelist.substring(0, employeelist.length()-1)
					: employeelist;
			mpp.employee = employeelist;
			
			pd.add(mpp);
		}
		
				
		
		
		dao.close();
		System.out.println("this function loadMainEmployee is complete will return "+pd.size());
		//return (MainPanelProject[]) pd.toArray();
		return pd;
	}
	
	public List<Employee> loadEmployeeInProjectDetail(String pid){
		DAO dao = new DAO();
		List<Employee> liste = dao.getEmployee4Project(pid);
		dao.close();
		return liste;
	}
	
	public String editProject(String pid, String progress, String list){
		DAO dao = new DAO();
		dao.updateProject(pid, progress, list);
		dao.close();
		return "SUCCESS";
	}
	
	public List<AllEmployeeSelector> loadAllEmployeeAsSelector(){
		DAO dao = new DAO();
		List<AllEmployeeSelector> listSelector = new ArrayList<AllEmployeeSelector>();
		List<Employee> liste = dao.getAllEmployees();
		for( Employee cl : liste ){
			AllEmployeeSelector se = new AllEmployeeSelector();
			se.id = cl.getId();
			se.name = cl.getName();
			se.status = cl.getStatus();
			
			List<Project> list = dao.getProjectsForEmployee(cl.getId());
			se.ongoing = getProjectString(ProjectStatus.ONGOING, list);
			se.complete = getProjectString(ProjectStatus.COMPLETE, list);
			listSelector.add(se);
		}
		
		
		dao.close();
		return listSelector;
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
