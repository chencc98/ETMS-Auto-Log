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
    public String getHeadMsg() {
        return Constants.getOperatorInsertHeadMsg();
    }

    /* (non-Javadoc)
     * @see com.ssga.javacodereview.controller.IOperator#getHelpTips()
     */
    public String getHelpTips() {
        return Constants.getOperatorInsertHelpMsg();
    }

    /* (non-Javadoc)
     * @see com.ssga.javacodereview.controller.IOperator#operate()
     */
    public String operate(String command) throws OperatorException {
        // check the command syntax
        Employee employee = OperatorCommandCheck.checkCommand(command);
        if( employee == null ){
            throw new OperatorException(Constants.getOperatorCommandCheckError(command));
        }
        if( employee.getId().equals(Constants.SEARCH_ALL_MASK) 
                || !employee.getId().matches("[0-9]+") 
                || employee.getName().equals("") 
                || employee.getName().equals(Constants.SEARCH_ALL_MASK)
                || employee.getSuperid().equals(Constants.SEARCH_ALL_MASK)
                || employee.getAge() == Constants.AGE_NOT_SET  
                )
        {
            throw new OperatorException(Constants.getOperatorCommandCheckError(command));
        }
        
        try{
            // invoke the connection to insert the employee
            this.con.insert(employee);
        }catch (MyConnectionException e) {
            throw new OperatorException( e.getMessage(), e);
        }
        
        return Constants.getMsgCommandSuccess();
    }
    
    public void setConnection( IMyConnection conn){
        this.con = conn;
    }

}
