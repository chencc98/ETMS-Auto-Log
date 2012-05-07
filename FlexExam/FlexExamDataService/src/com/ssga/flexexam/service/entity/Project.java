/**
 * 
 */
package com.ssga.flexexam.service.entity;

/**
 * @author e464150
 *
 */
public class Project {
	private String id;
	private String name;
	private String client;
//	private List<Employee> employees;
	private int progress;
	private String status;

	/**
	 * 
	 */
	public Project() {
		this.id = "";
		this.name = "";
		this.client = "";
//		this.employees = new ArrayList<Employee>();
		this.progress = 0;
		this.status = ProjectStatus.SUSPENDED;
	}
	public Project(String id, String name, String client, int p, String status ){
		this.id = id;
		this.name = name;
		this.client = client;
//		this.employees = new ArrayList<Employee>();
		this.progress = p;
		this.status = status;
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
	
	public String getClient(){
		return this.client;
	}
	public void setClient ( String c){
		this.client = c;
	}
	
//	public List<Employee> getEmployees(){
//		return this.employees;
//	}
//	public void addEmployee( Employee em){
//		if( !checkEmployeeExist(em)){
//			this.employees.add(em);
//			setStatus();
//		}
//	}
//	public void removeEmployee(Employee em){
//		if( checkEmployeeExist(em)){
//			_removeEmployee(em);
//			setStatus();
//		}
//	}
	
	public int getProgress(){
		return this.progress;
	}
	public void setProgress(int p){
		
			this.progress  = p;
		
	}
	
	
	public String getStatus(){
		return this.status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public String toXMLString(){
		return "<project>" + "<id>" + this.id + "</id>" 
			+ "<name>" + this.name + "</name>" 
			+ "<client>" + this.client + "</client>"
			+ "<progress>" + this.progress + "</progress>"
			+ "<status>" + this.status + "</status>"+"</project>";
	}


}
