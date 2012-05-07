/**
 * copyright here
 */
package com.ssga.javacodereview.model.entity;

/**
 * @author e464150
 *
 */
public class Employee {
	private String id;
	private String name;
	private String superid;
	private int age;
	
	
	public Employee( String eid, String ename, String sid, int age){
		this.id = eid;
		this.name = ename;
		this.superid = sid;
		this.age = age;
	}
	
	public Employee(){
		this.id = "";
		this.name = "";
		this.superid = "";
		this.age = -1;
	}
	
	public String getId(){
		return this.id;
	}
	public void setId(String eid){
		this.id = eid;
	}
	
	public String getName(){
		return this.name;
	}
	public void setName(String ename){
		this.name = ename;
	}
	
	public String getSuperid(){
		return this.superid;
	}
	public void setSuperid(String sid ){
		this.superid = sid;
	}
	
	public int getAge(){
		return this.age;
	}
	public void setAge(int age){
		this.age = age;
	}
	
	public String toString(){
		return this.name + ", ID:" + this.id + ", Age:" + this.age;
	}
	
	public boolean equals(Employee m){
		if( this.getId().equals(m.getId()) && this.getName().equals(m.getName())
				&& this.getSuperid().equals(m.getSuperid()) 
				&& this.getAge() == m.getAge() ){
			return true;
		}else{
			return false;
		}
	}

}
