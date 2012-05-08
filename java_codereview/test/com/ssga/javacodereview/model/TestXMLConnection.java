package com.ssga.javacodereview.model;

import static org.junit.Assert.*;


import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ssga.javacodereview.model.entity.Employee;
import com.ssga.javacodereview.util.MyConnectionException;
import com.ssga.javacodereview.util.Constants;

public class TestXMLConnection {
	
	private static String testfile = "junittest.xml";
	private static XMLConnection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PrintWriter write = new PrintWriter(testfile);
		write.println("<employees></employees>");
		write.close();
		conn = new XMLConnection(testfile);
		
		
		//insert the test data
		Employee [] ems = new Employee [10];
		for(int i =0; i< 10; i++){
			ems[i] = new Employee();
			ems[i].setId("1234" + i);
			ems[i].setName("testuser" + i);
			ems[i].setSuperid("");
			ems[i].setAge(20 + i);
			conn.insert(ems[i]);
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File f = new File( testfile);
		f.delete();
	}

	@Test(expected=MyConnectionException.class)
	public void testDelete() throws MyConnectionException {
		conn.delete("54321", false);
	}
	
	
	@Test
	public void testDeleteOK() throws MyConnectionException{
		Employee em = new Employee("10000","dele","12340",40);
		conn.insert(em);
		conn.delete("10000", true);
	}

	@Test
	public void testInsert() throws MyConnectionException {
		Employee em = new Employee("10001","keepcc","12340",40);
		conn.insert(em);
		Employee search = conn.searchById("10001");
		assertNotNull(search);
	}

	@Test
	public void testSearch() throws MyConnectionException {
		Properties p = new Properties();
		p.setProperty(Constants.SEARCH_KEY_ID, "12342");
		List<Employee> list = conn.search(p);
		
		
		assertTrue(list.size() == 1);
	}

	@Test
	public void testSearchById() throws MyConnectionException {
		Employee search = conn.searchById("12343");
		assertNotNull(search);
	}
 
	@Test
	public void testUpdate() throws MyConnectionException {
		Employee em = new Employee("12345","keepcc","12340",40);
		conn.update(em);
		Employee s = conn.searchById("12345");
		assertTrue(s.equals(em));
	}

}
