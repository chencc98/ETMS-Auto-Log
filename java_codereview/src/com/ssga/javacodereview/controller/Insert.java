/**
 * 
 */
package com.ssga.javacodereview.controller;

import com.ssga.javacodereview.model.IMyConnection;
import com.ssga.javacodereview.model.entity.Employee;
import com.ssga.javacodereview.util.MyConnectionException;
import com.ssga.javacodereview.util.OperatorException;
import com.ssga.javacodereview.util.Constants;

/**
 * @author asus
 *
 */
public class Insert implements IOperator {

	private IMyConnection con = null;
	
	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.controller.IOperator#getHeadMsg()
	 */
	@Override
	public String getHeadMsg() {
		return Constants.getOperatorInsertHeadMsg();
	}

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.controller.IOperator#getHelpTips()
	 */
	@Override
	public String getHelpTips() {
		return Constants.getOperatorInsertHelpMsg();
	}

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.controller.IOperator#operate()
	 */
	@Override
	public String operate(String command) throws OperatorException {
		// check the command syntax
		Employee em = OperatorCommandCheck.checkCommand(command);
		if( em == null ){
			throw new OperatorException(Constants.getOperatorCommandCheckError(command));
		}
		if( em.getId().equals(Constants.SEARCH_ALL_MASK) || !em.getId().matches("[0-9]+") || em.getName().equals("") 
				||  em.getName().equals(Constants.SEARCH_ALL_MASK)
				|| em.getSuperid().equals(Constants.SEARCH_ALL_MASK)
				|| em.getAge() == Constants.AGE_NOT_SET  
				){
			throw new OperatorException(Constants.getOperatorCommandCheckError(command));
		}
		
		try{
			// invoke the connection to insert the employee
			this.con.insert(em);
		}catch (MyConnectionException e) {
			throw new OperatorException( e.getMessage(), e);
		}
		
		return Constants.getMsgCommandSuccess();
	}
	
	public void setConnection( IMyConnection conn){
		this.con = conn;
	}

}
