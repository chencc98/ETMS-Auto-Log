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
	@Override
	public String getHeadMsg() {
		// TODO Auto-generated method stub
		return Constants.getOperatorSearchHeadMsg();
	}

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.controller.IOperator#getHelpTips()
	 */
	@Override
	public String getHelpTips() {
		return Constants.getOperatorSearchHelpMsg();
	}

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.controller.IOperator#operate()
	 */
	@Override
	public String operate(String command) throws OperatorException {
		Employee em = OperatorCommandCheck.checkCommand(command);
		if( em == null ){
			throw new OperatorException(Constants.getOperatorCommandCheckError(command));
		}else if ( em.getId().equalsIgnoreCase(Constants.COMMAND_NOCHANGE) 
				|| !em.getName().equals("") || !em.getSuperid().equals("") || em.getAge() != -1 ){
			throw new OperatorException(Constants.getOperatorCommandCheckError(command));
		}
		
		try {
			if( em.getId().equalsIgnoreCase("All")){
				return travelEmployee(null, "");
			}else{
				Properties p = new Properties();
				p.setProperty(Constants.SEARCH_KEY_ID, em.getId());
				List<Employee>  list = this.con.search(p);
				
				String returnstr = "";
				Employee s = list.get(0);
				
				returnstr = s.toString() + "\n";
				
				list = getSub(s.getId());
				if( list.size() == 0 ){
					returnstr = returnstr + "\tSub: None \n";
				}else{
					//returnstr = returnstr + "\t";
					Iterator<Employee> it = list.iterator();
					while( it.hasNext() ){
						Employee sub = it.next();
						returnstr = returnstr + "\tSub: " + sub.toString() + "\n" ;
						
					}
				}
				
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
				result += p.toString() + "\n";
				result += travelEmployee( p, "\t" );
			}
		}else{
			List<Employee> sub = getSub(parent.getId());
			for( Employee p : sub ){
				result += prefix + p.toString() + "\n";
				result += travelEmployee( p, prefix + "\t" );
			}
		}
		return result;
	}
	
}
