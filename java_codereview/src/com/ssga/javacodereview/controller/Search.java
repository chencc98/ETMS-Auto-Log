/**
 * 
 */
package com.ssga.javacodereview.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.ssga.javacodereview.model.IMyConnection;
import com.ssga.javacodereview.model.entity.Employee;
import com.ssga.javacodereview.util.MyConnectionException;
import com.ssga.javacodereview.util.OperatorException;
import com.ssga.javacodereview.util.Constants;

/**
 * @author asus
 *
 */
public class Search implements IOperator {
	
	private IMyConnection  con= null;

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.controller.IOperator#getHeadMsg()
	 */
	public String getHeadMsg() {
		// TODO Auto-generated method stub
		return Constants.getOperatorSearchHeadMsg();
	}

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.controller.IOperator#getHelpTips()
	 */
	public String getHelpTips() {
		return Constants.getOperatorSearchHelpMsg();
	}

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.controller.IOperator#operate()
	 */
	public String operate(String command) throws OperatorException {
		// check the synctax
		Employee em = OperatorCommandCheck.checkCommand(command);
		if( em == null ){
			throw new OperatorException(Constants.getOperatorCommandCheckError(command));
		}else if ( em.getId().equalsIgnoreCase(Constants.SEARCH_ALL_MASK) 
				|| !em.getName().equals("") || !em.getSuperid().equals("") || em.getAge() != -1 ){
			throw new OperatorException(Constants.getOperatorCommandCheckError(command));
		}
		
		try {
			if( em.getId().equalsIgnoreCase("All")){
				return travelEmployee(null, "");   // if all, we will use this function to loop the data
			}else{
				Properties p = new Properties();
				p.setProperty(Constants.SEARCH_KEY_ID, em.getId());
				List<Employee>  list = this.con.search(p);
				
				// find this employee first, if no exist, return error
				if( list.size() == 0){
					throw new OperatorException(Constants.getMsgNoSuchEmployee(em.getId()));
				}
				
				String returnstr = "";
				Employee s = list.get(0);
				
				returnstr = s.toString() + Constants.getEOL();
				
				// get sub first
				list = getSub(s.getId());
				if( list.size() == 0 ){ 
					returnstr = returnstr + "\tSub: None " + Constants.getEOL();
				}else{
					//returnstr = returnstr + "\t";
					Iterator<Employee> it = list.iterator();
					while( it.hasNext() ){
						Employee sub = it.next();
						returnstr = returnstr + "\tSub: " + sub.toString() + Constants.getEOL() ;
						
					}
				}
				
				//then get the superior
				if( s.getSuperid().equals("")){
					returnstr += "\tSup: None";
				}else{
					Employee sup = getSup( s.getSuperid());
					returnstr += "\tSup: " + sup.toString();
				}
				
				return returnstr;
				
			}
		
		
			
		} catch (MyConnectionException e) {
			throw new OperatorException( e.getMessage(), e);
		}
		
	}
	
	
	public void setConnection( IMyConnection conn){
		this.con = conn;
	}
	
	
	private List<Employee> getSub(String eid) throws MyConnectionException{
		Properties p = new Properties();
		p.setProperty(Constants.SEARCH_KEY_SID, eid);
		return this.con.search(p);
	}
	
	private Employee getSup( String sid) throws MyConnectionException{
		Properties p = new Properties();
		p.setProperty(Constants.SEARCH_KEY_ID, sid);
		return this.con.search(p).get(0);
	}
	

	private String travelEmployee(Employee parent, String prefix) throws MyConnectionException{
		String result = "";
		if( parent == null ){
			List<Employee> sub = getSub("");
			for( Employee p : sub ){
				result += p.toString() + Constants.getEOL();
				result += travelEmployee( p, "\t" );
			}
		}else{
			List<Employee> sub = getSub(parent.getId());
			for( Employee p : sub ){
				result += prefix + p.toString() + Constants.getEOL();
				result += travelEmployee( p, prefix + "\t" );
			}
		}
		return result;
	}
	
}
