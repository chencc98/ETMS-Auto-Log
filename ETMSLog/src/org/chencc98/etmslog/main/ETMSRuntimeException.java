/**
 * 
 */
package org.chencc98.etmslog.main;

/**
 * @author asus
 *
 */
public class ETMSRuntimeException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6015694031670039863L;

	public ETMSRuntimeException(String msg){
		super(msg);
	}
	
	public ETMSRuntimeException(String msg, Throwable cause){
		super(msg, cause);
	}

}
