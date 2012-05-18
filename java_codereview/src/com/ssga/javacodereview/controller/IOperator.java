/**
 * 
 */
package com.ssga.javacodereview.controller;

import com.ssga.javacodereview.model.IMyConnection;
import com.ssga.javacodereview.util.OperatorException;

/**
 * @author asus
 *
 */
public interface IOperator {
    
    public String getHeadMsg();
    public String getHelpTips();
    public String operate(String command) throws OperatorException; 
    public void setConnection( IMyConnection con);

}
