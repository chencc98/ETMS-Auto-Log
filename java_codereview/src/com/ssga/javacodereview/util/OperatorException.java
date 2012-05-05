/**
 * 
 */
package com.ssga.javacodereview.util;

/**
 * @author asus
 *
 */
public class OperatorException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -300994264976464595L;

	public OperatorException(String string) {
		super(string);
	}
	
	public OperatorException(String msg, Throwable cause){
		super(msg, cause);
	}

}
