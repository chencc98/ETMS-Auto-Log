/**
 * 
 */
package com.ssga.javacodereview.controller;

import com.ssga.javacodereview.util.OperatorException;
import com.ssga.javacodereview.util.Constants;

/**
 * @author asus
 *
 */
public class Update implements IOperator {

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
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.controller.IOperator#operate()
	 */
	@Override
	public String operate() throws OperatorException {
		// TODO Auto-generated method stub
		return null;
	}

}
