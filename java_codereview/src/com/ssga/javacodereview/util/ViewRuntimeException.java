/**
 * 
 */
package com.ssga.javacodereview.util;

/**
 * @author asus
 *
 */
public class ViewRuntimeException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7799444481245587412L;

	public ViewRuntimeException(String msg){
		super(msg);
	}
	
	public ViewRuntimeException(String msg, Throwable cause){
		super(msg, cause);
	}

}
