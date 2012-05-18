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
    public String getHeadMsg() {
        return Constants.getOperatorUpdateHeadMsg();
    }

    /* (non-Javadoc)
     * @see com.ssga.javacodereview.controller.IOperator#getHelpTips()
     */
    public String getHelpTips() {
        return Constants.getOperatorUpdateHelpMsg();
    }

    /* (non-Javadoc)
     * @see com.ssga.javacodereview.controller.IOperator#operate()
     */
    public String operate(String command) throws OperatorException {
        Employee employee = OperatorCommandCheck.checkCommand(command);
        if( employee == null ){
            throw new OperatorException(Constants.getOperatorCommandCheckError(command));
        }else if( employee.getId().equalsIgnoreCase(Constants.SEARCH_ALL_MASK)
                || employee.getName().equals(Constants.SEARCH_ALL_MASK)
                || employee.getSuperid().equals(Constants.SEARCH_ALL_MASK))
        {
            throw new OperatorException(Constants.getOperatorCommandCheckError(command));
        }
        
        try{
            Properties p = new Properties();
            p.setProperty(Constants.SEARCH_KEY_ID, employee.getId());
            List<Employee> list = this.con.search(p);
            if( list.size() == 0 ){
                throw new OperatorException( Constants.getMsgNoSuchEmployee(employee.getId()));
            }else{
                Employee u = list.get(0);
                if( employee.getName().equals("") || employee.getName().equalsIgnoreCase(Constants.COMMAND_NOCHANGE)){
                	employee.setName(u.getName());
                }
                
                if( employee.getSuperid().equalsIgnoreCase(Constants.COMMAND_NOCHANGE)){
                	employee.setSuperid(u.getSuperid());
                }
                
                if( employee.getAge() == -1 ){
                	employee.setAge(u.getAge());
                }
                
                this.con.update(employee);
                
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
