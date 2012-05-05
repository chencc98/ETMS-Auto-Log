/**
 * 
 */
package com.ssga.javacodereview.controller;

import com.ssga.javacodereview.util.OperatorException;

/**
 * @author asus
 *
 */
public interface IOperator {
	
	public String getHeadMsg();
	public String getHelpTips();
	public String operate() throws OperatorException; 

}
