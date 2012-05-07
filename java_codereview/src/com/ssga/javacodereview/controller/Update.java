/**
 * 
 */
package com.ssga.javacodereview.controller;

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
public class Update implements IOperator {
	
	private IMyConnection con = null;
	
	//private String update_nochange_field = "NoChange";

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.controller.IOperator#getHeadMsg()
	 */
	@Override
	public String getHeadMsg() {
		return Constants.getOperatorUpdateHeadMsg();
	}

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.controller.IOperator#getHelpTips()
	 */
	@Override
	public String getHelpTips() {
		return Constants.getOperatorUpdateHelpMsg();
	}

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.controller.IOperator#operate()
	 */
	@Override
	public String operate(String command) throws OperatorException {
		Employee em = OperatorCommandCheck.checkCommand(command);
		if( em == null ){
			throw new OperatorException(Constants.getOperatorCommandCheckError(command));
		}else if( em.getId().equalsIgnoreCase(Constants.SEARCH_ALL_MASK)
				|| em.getName().equals(Constants.SEARCH_ALL_MASK)
				|| em.getSuperid().equals(Constants.SEARCH_ALL_MASK)){
			throw new OperatorException(Constants.getOperatorCommandCheckError(command));
		}
		
		try{
			Properties p = new Properties();
			p.setProperty(Constants.SEARCH_KEY_ID, em.getId());
			List<Employee> list = this.con.search(p);
			if( list.size() == 0 ){
				throw new OperatorException( Constants.getMsgNoSuchEmployee(em.getId()));
			}else{
				Employee u = list.get(0);
				if( em.getName().equals("") || em.getName().equalsIgnoreCase(Constants.COMMAND_NOCHANGE)){
					em.setName(u.getName());
				}
				
				if( em.getSuperid().equalsIgnoreCase(Constants.COMMAND_NOCHANGE)){
					em.setSuperid(u.getSuperid());
				}
				
				if( em.getAge() == -1 ){
					em.setAge(u.getAge());
				}
				
				this.con.update(em);
				
			}
		}catch (MyConnectionException e) {
			throw new OperatorException( e.getMessage(), e);
		}
		
		return Constants.getMsgCommandSuccess();
	}
	
	public void setConnection( IMyConnection conn){
		this.con = conn;
	}

}
