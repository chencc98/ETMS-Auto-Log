/**
 * 
 */
package com.ssga.javacodereview.util;

/**
 * @author e464150
 *
 */
public class MyConnectionException extends Exception {

	public MyConnectionException(String string) {
		super(string);
	}
	
	public MyConnectionException(String msg, Throwable cause){
		super(msg, cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8162574475704962826L;

}
