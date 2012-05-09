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
public class Delete implements IOperator {
	private IMyConnection con = null;

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.controller.IOperator#getHeadMsg()
	 */
	public String getHeadMsg() {
		return Constants.getOperatorDeleteHeadMsg();
	}

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.controller.IOperator#getHelpTips()
	 */
	public String getHelpTips() {
		return Constants.getOperatorDeleteHelpMsg();
	}

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.controller.IOperator#operate()
	 */
	public String operate(String command) throws OperatorException {
		// check the command syntax, only support employee id 
		Employee em = OperatorCommandCheck.checkCommand(command);
		if( em == null ){
			throw new OperatorException(Constants.getOperatorCommandCheckError(command));
		}else if( !em.getName().equals("") || !em.getSuperid().equals("") 
				|| em.getAge() != Constants.AGE_NOT_SET){
			throw new OperatorException(Constants.getOperatorCommandCheckError(command));
		}else if( em.getId().equalsIgnoreCase(Constants.SEARCH_ALL_MASK)){
			throw new OperatorException(Constants.getOperatorCommandCheckError(command));
		}
		
		
		
		try{
			// invoke the connection to delete this employee
			this.con.delete(em.getId(), false);
		}catch (MyConnectionException e) {
			throw new OperatorException( e.getMessage(), e);
		}
		
		return Constants.getMsgCommandSuccess();
	}
	
	public void setConnection( IMyConnection conn){
		this.con = conn;
	}

}
