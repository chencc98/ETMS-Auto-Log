package com.ssga.flexexam.service.flexgateway;

import java.util.ArrayList;
import java.util.List;

import com.ssga.flexexam.service.dao.DAO;
import com.ssga.flexexam.service.entity.Client;
import com.ssga.flexexam.service.entity.Employee;
import com.ssga.flexexam.service.entity.MainPanelProject;

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

}
