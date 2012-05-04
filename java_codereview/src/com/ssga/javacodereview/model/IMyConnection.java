/**
 * 
 */
package com.ssga.javacodereview.model;

import java.util.List;
import java.util.Properties;

import com.ssga.javacodereview.model.entity.Employee;
import com.ssga.javacodereview.util.MyConnectionException;

/**
 * @author e464150
 *
 */
public interface IMyConnection {
	
	/** 
	 * the properties should have one or more of four keys
	 * id , name, superid, age, age_op
	 */
	public void connect()throws MyConnectionException;
	public void disconnect() throws MyConnectionException;
	public List<Employee> search(Properties p) throws MyConnectionException;
	
	public void insert(Employee em) throws MyConnectionException;
	
	public void delete(String eid, boolean toTop) throws MyConnectionException;
	
	public void update(Employee em) throws MyConnectionException;

}
