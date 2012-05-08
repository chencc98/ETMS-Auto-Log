package com.ssga.javacodereview.model.entity;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestEmployee {

	@Test
	public void testEmployeeStringStringStringInt() {
		Employee em = new Employee("1234","john","1111",30);
		assertTrue(em.getId().equals("1234") && em.getName().equals("john")
				&& em.getSuperid().equals("1111") && em.getAge() == 30);
	}

	@Test
	public void testToString() {
		Employee em = new Employee("1234","john","1111",30);
		assertTrue(em.toString().equals("john, ID:1234, Age:30"));
	}

	@Test
	public void testEqualsEmployee() {
		Employee em = new Employee("1234","john","1111",30);
		Employee em1 = new Employee("1234","john","1111",30);
		assertTrue( em.equals(em1));
	}
	
	@Test
	public void testEqualsEmployee2(){
		Employee em = new Employee("1234","john","1111",30);
		Employee em1 = new Employee("1234","john","1111",35);
		assertTrue( !em.equals(em1));
	
	}

}
