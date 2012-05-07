/**
 * 
 */
package com.ssga.flexexam.service.entity;

/**
 * @author e464150
 *
 */
public class Employee {
	
	private String id;
	private String name;
	private String status;
//	private List<Project> projects;

	/**
	 * 
	 */
	public Employee() {
		this.id = "";
		this.name = "";
		this.status = EmployeeStatus.UNDERLOAD;
//		this.projects = new ArrayList<Project>();
	}
	public Employee(String id, String name, String status){
		this.id = id;
		this.name = name;
		
		this.status = status;
		
		
//		this.projects = new ArrayList<Project>();
	}
	
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
	
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
//	public List<Project> getProjects(){
//		return this.projects;
//	}
//	public void addProject(Project pid){
//		if( !checkProjectExist( pid )){
//			this.projects.add(pid);
//			setStatus();
//		}
//		
//	}
//	public void removeProject( Project pid){
//		if( checkProjectExist( pid )){
//			_removeProject(pid);
//			setStatus();
//		}
//		
//	}
	
	public String getStatus(){
		return this.status;
	}
	
	
	public void setStatus( String status){
		this.status = status;
	}
	
	public String toXMLString(){
		return "<employee>" + "<id>" + this.id + "</id>" 
			+ "<name>" + this.name + "</name>" 
			+ "<status>" + this.status + "</status>"+"</employee>";
	}

}
