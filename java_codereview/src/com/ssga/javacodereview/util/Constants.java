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
	
	public static String COMMAND_NOCHANGE = "NoChange";
	
	public static String getMsgNoSuchEmployee(String eid){
		return "No such employee with id = " + eid;
	}
	public static String getMsgDeleteEmployeeError(String eid){
		return "error when delete employee id=" + eid;
	}
	public static String getMsgPartUpdateErr(int updated, int total){
		return "Only part of employee are updated "+ updated + "/"+total ;
	}
	public static String getMsgEmployeeExisting(String id, String name){
		return "The employee ["+id + "," + name +"] already exists";
	}
	
	public static String getMsgMultipleEmployeeError(String eid){
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
	public static String getViewShowErrPre(){
		return "[Error]: ";
	}
	public static String getViewExitMenu(){
		return "Exit";
	}
	public static String getMsgCommandSuccess(){
		return "Command Success";
	}
	public static String getViewUnknown(){
		return "Unknown";
	}
	public static String getViewEnterNextLevel(String level){
		return "Enter the mode " + level;
	}
	public static String getViewBackMenu(){
		return "Back";
	}
	public static String getViewHelpMenu(){
		return "Help";
	}
	
	
	
	public static String getMsgNullConnection(){
		return "Connection is null, not available";
	}
	public static String getMsgUnSupport( String fun){
		return "This function "+ fun +" is not supported currently.";
	}
	public static String getOperatorSearchHeadMsg(){ 
		return "1). search by id or type 'all' to list all" + "\n" 
				+ "2). type help to show help " + "\n"
				+ "3). type Back to up to home";
	}
	public static String getOperatorUpdateHeadMsg(){ 
		return "1.1). type '<eid>,[name],[superid],[age]' to update one employee" + "\n" 
				+ "1.2). type 'id=<eid>,name=[name],superid=[superid],age=[age] to update" + "\n"
				+ "2). type help to show help " + "\n"
				+ "3). type Back to up to home";
	}
	public static String getOperatorDeleteHeadMsg(){ 
		return "1). delete employee by id " + "\n" 
				+ "2). type help to show help " + "\n"
				+ "3). type Back to up to home";
	}
	public static String getOperatorInsertHeadMsg(){ 
		return "1.1). type '<eid>,[name],[superid],[age]' to insert one employee" + "\n" 
				+ "1.2). type 'id=<eid>,name=[name],superid=[superid],age=[age] to insert" + "\n"
				+ "2). type help to show help " + "\n"
				+ "3). type Back to up to home";
	}
	public static String getOperatorSearchHelpMsg(){ 
		return "1). search by id or type 'all' to list all" + "\n" 
				+ "2). type help to show help " + "\n"
				+ "3). type Back to up to home";
	}
	public static String getOperatorUpdateHelpMsg(){ 
		return "1.1). type '<eid>,[name],[superid],[age]' to update one employee" + "\n" 
				+ "1.2). type 'id=<eid>,name=[name],superid=[superid],age=[age] to update" + "\n"
				+ "2). type help to show help " + "\n"
				+ "3). type Back to up to home";
	}
	public static String getOperatorDeleteHelpMsg(){ 
		return "1). delete employee by id " + "\n" 
				+ "2). type help to show help " + "\n"
				+ "3). type Back to up to home";
	}
	public static String getOperatorInsertHelpMsg(){ 
		return "1.1). type '<eid>,[name],[superid],[age]' to insert one employee" + "\n" 
				+ "1.2). type 'id=<eid>,name=[name],superid=[superid],age=[age] to insert" + "\n"
				+ "2). type help to show help " + "\n"
				+ "3). type Back to up to home";
	}
	public static String getOperatorCommandCheckError(String command){
		return "this command " + command + "has syntax error. please check";
	}
	
	
	
}
