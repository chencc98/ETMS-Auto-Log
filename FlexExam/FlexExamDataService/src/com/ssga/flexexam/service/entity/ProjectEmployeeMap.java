/**
 * 
 */
package com.ssga.flexexam.service.entity;

/**
 * @author e464150
 *
 */
public class ProjectEmployeeMap {
	
	private String project;
	private String employee;
	
	public ProjectEmployeeMap(){
		this.project = "";
		this.employee = "";
	}
	public ProjectEmployeeMap(String p, String e){
		this.project = p;
		this.employee = e;
	}
	
	public String getProject(){
		return this.project;
	}
	public void setProject(String p ){
		this.project = p;
	}
	
	public String getEmployee(){
		return this.employee;
	}
	public void setEmployee(String e){
		this.employee = e;
	}
	
	public String toXMLString(){
		return "<map>" + "<pid>" + this.project + "</pid>" 
			+ "<eid>" + this.employee + "</eid>" + "</map>";
	}

}
