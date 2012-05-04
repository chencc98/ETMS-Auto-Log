/**
 * 
 */
package com.ssga.javacodereview.util;

/**
 * @author e464150
 *
 */
public class ControllerRuntimeException extends RuntimeException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2680660722402735304L;

	public ControllerRuntimeException(String msg){
		super(msg);
	}
	
	public ControllerRuntimeException(String msg, Throwable cause){
		super(msg, cause);
	}

}
