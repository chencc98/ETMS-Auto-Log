/**
 * 
 */
package com.ssga.javacodereview.util;

/**
 * @author e464150
 *
 */
public class Constants {
	
	public static String SEARCH_KEY_ID = "id";
	public static String SEARCH_KEY_NAME = "name";
	public static String SEARCH_KEY_SID = "superiorid";
	public static String SEARCH_KEY_AGE = "age";
	public static String SEARCH_KEY_AGEOP = "ageop";
	public static String SEARCH_KEY_AGEOP_EQ = "ageop_eq";
	public static String SEARCH_KEY_AGEOP_GE = "ageop_ge";
	public static String SEARCH_KEY_AGEOP_LE = "ageop_le";

	
	public static String XML_EMPLOYEE_TAG = "employee";
	
	public static String getConnectionExceptionParseErrorMsg(){
		return "Parse error during connection...";
	}
	public static String getConnectionExceptionIOErrorMsg(){
		return "IO error during connection...";
	}
	public static String getConnectionExceptionNoSuchEmployee(String eid){
		return "No such employee with id = " + eid;
	}
	public static String getConnectionExceptionPartUpdateErr(int updated, int total){
		return "Only part of employee are updated "+ updated + "/"+total ;
	}
	public static String getConnectionExceptionExisting(String id, String name){
		return "The employee ["+id + "," + name +"] already exists";
	}
	public static String getConnectionExceptionXMLWrongState(){
		return "XML illgal state error by jdom";
	}
	public static String getConnectionExceptionMultipleEmployee(String eid){
		return "Multiple employee share the same id = " + eid;
	}
	
	
	public static String getViewWelcomeMsg(){
		return "Welcome to use this system!";
	}
	public static String getViewEndingMsg(){
		return "ByeBye";
	}
	public static String getViewTopMenuHead(){
		return "Current system supports below actions:";
	}
	public static String getViewTopMenuPrompt(){
		return "Please type your options, number or word:";
	}
	
	
	
	public static String getControlNullConnection(){
		return "Connection is null, not available";
	}
	
	
}
