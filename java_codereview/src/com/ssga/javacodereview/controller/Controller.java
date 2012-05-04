/**
 * 
 */
package com.ssga.javacodereview.controller;

import com.ssga.javacodereview.model.IMyConnection;
import com.ssga.javacodereview.util.Constants;
import com.ssga.javacodereview.util.ControllerRuntimeException;
import com.ssga.javacodereview.util.MyConnectionException;

/**
 * @author e464150
 *
 */
public class Controller {
	
	private  IMyConnection conn = null;
	private String errMsg = "";
	
	private String [] functionlist = {"Search", "Insert","Update","Delete"};
	
	
	public Controller ( IMyConnection c ){
		this.conn = c;
	}
	
	public String getErrorMsg(){
		return this.errMsg;
	}
	
	public void connect(){
		try{
			if( this.conn == null){
				this.errMsg = Constants.getControlNullConnection();
				throw new ControllerRuntimeException(this.errMsg);
			}
			conn.connect();
			
		}catch(MyConnectionException mce){
			this.errMsg = mce.getMessage();
			throw new ControllerRuntimeException(this.errMsg, mce);
		}
	}
	public void disconnect(){
		try{
			if( conn != null ){
				 conn.disconnect();
				conn = null;
				
			}
			
		}catch( MyConnectionException mce){
			this.errMsg = mce.getMessage();
			throw new ControllerRuntimeException(this.errMsg, mce);
			
		}
	}

	public String [] supportlist(){
		return this.functionlist;
	}


}
