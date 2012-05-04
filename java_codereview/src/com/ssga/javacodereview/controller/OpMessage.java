/**
 * 
 */
package com.ssga.javacodereview.controller;

import com.ssga.javacodereview.model.entity.Employee;

/**
 * @author asus
 *
 */
public class OpMessage {
	
	private String type = "";
	private Employee emp = null;
	private String extra = "";
	
	
	public OpMessage(String type , Employee em ){
		this.type = type;
		this.emp = em;
	}
	
	
	public String getType(){
		return this.type;
	}
	public void setType( String tp ){
		this.type = tp;
	}
	
	public Employee getEmployee(){
		return this.emp;
	}
	public void setEmployee( Employee em){
		this.emp = em;
	}
	public String getExtra (){
		return this.extra;
	}
	public void setExtra(String ex ){
		this.extra = ex;
	}

}
