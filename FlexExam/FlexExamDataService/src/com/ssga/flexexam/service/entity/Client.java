/**
 * 
 */
package com.ssga.flexexam.service.entity;

/**
 * @author e464150
 *
 */
public class Client {
	
	private String name;
	private String id;
	
	public Client(){
		this.name = "";
		this.id = "";
	}
	public Client(String id, String name){
		this.id = id;
		this.name = name;
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
	
	public String toXMLString(){
		return "<client>" + "<id>" + this.id + "</id>" 
			+ "<name>" + this.name + "</name>" + "</client>";
	}

}
