/**
 * 
 */
package com.ssga.javacodereview.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.ssga.javacodereview.controller.Controller;
import com.ssga.javacodereview.util.Constants;
import com.ssga.javacodereview.util.ViewRuntimeException;

/**
 * @author e464150
 *
 */
public class ConsoleView implements IMyView {
	
	private String LEVEL_TOP = "Top";
	
	
	private  Controller ctrl = null;
	
	private String [] functions ;
	private String currentLevel = "Top";     //Exit or Back
	private boolean running = true;

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.view.IMyView#end()
	 */
	public void end() {
		// 
		
		this.showMsg(Constants.getViewEndingMsg());

	}

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.view.IMyView#start()
	 */
	public void start() {
		this.displayWelcome();

	}

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.view.IMyView#work(com.ssga.javacodereview.controller.Controller)
	 */
	public void work(Controller ctrl) {
		// #1 connect to source
		this.ctrl = ctrl;
		 this.ctrl.connect() ;
		 
		 
		 //enter the  loop
		this.functions = this.ctrl.supportlist();
		//boolean running = true;
		while( this.running ){
			this.showMsg(this.getHeadByLevel(currentLevel));
			
			String line = this.getInput();
			String result = this.workonAsLevel(currentLevel, line);
			
			
			this.showMsg(result);
			
			
			
		
		}
		
		
		// at last disconnect
		if( this.ctrl != null){
			this.ctrl.disconnect();
			this.ctrl = null;
		}

	}
	
	private void displayWelcome(){
		this.showMsg(Constants.getViewWelcomeMsg());
	}
	
	private void showMsg(String msg){
		System.out.println(msg);
	}
//	private void showErrMsg(String msg){
//		System.err.println(Constants.getViewShowErrPre() + msg );
//	}
	
	private String getHeadByLevel(String level){
		if( this.LEVEL_TOP.equals(level)){
			String menus = "";
			int i;
			for (  i = 0 ;i < functions.length ; i ++ ){
				menus = menus + i + ":" + functions[i] + "   ";
			}
			menus = menus + i + ":" + Constants.getViewExitMenu();
			
			return Constants.getViewTopMenuHead() + System.getProperty("line.separator", "\n")
					+ menus + System.getProperty("line.separator", "\n")
					+ Constants.getViewTopMenuPrompt();
		}else{   //then should be the function we support
			return this.ctrl.getHeadMsgByLevel(level);
		}
		
	}
	
	
	private String getInput(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} catch (IOException e) {
			throw new ViewRuntimeException(e.getMessage(), e);
		}
	}
	
	
	private String workonAsLevel( String level, String keyaction){
		if( this.LEVEL_TOP.equals(level)){
			if( Constants.getViewExitMenu().equalsIgnoreCase(keyaction) 
					|| String.valueOf(this.functions.length).equalsIgnoreCase(keyaction)){
				this.running = false;
				return Constants.getViewCommandSuccess();
			}else if(isCommandInFunctions(keyaction)){
				this.currentLevel = getLevelFromCommand(keyaction);
				return Constants.getViewEnterNextLevel(currentLevel);
				
			}else{
				return Constants.getControlUnSupport(keyaction);
			}
		}else{//below is the valid level/functions
			if( Constants.getViewBackMenu().equalsIgnoreCase(keyaction)){
				this.currentLevel = this.LEVEL_TOP;
				return Constants.getViewCommandSuccess();
			}else if( Constants.getViewHelpMenu().equalsIgnoreCase(keyaction)){
				return this.ctrl.getHelpMsgByLevel(level);
			}
		}
		
		return "";
		
	}
	
	private boolean isCommandInFunctions(String command){
		try{
			int index = Integer.parseInt(command);
			if( index >= 0 && index < this.functions.length){
				return true;
			}else{
				return false;
			}
		}catch(NumberFormatException nfe){
			boolean comp = false;
			for ( int i = 0 ; i< this.functions.length; i++){
				if( this.functions[i].equalsIgnoreCase(command) ){
					comp = true;;
					break;
				}
			}
			return comp;
		}
	}
	
	private String getLevelFromCommand(String command){  //support this command is valid
		try{
			int index = Integer.parseInt(command);
			return this.functions[index];
		}catch(NumberFormatException nfe){
			
			for ( int i = 0 ; i< this.functions.length; i++){
				if( this.functions[i].equalsIgnoreCase(command) ){
					return this.functions[i];
				}
			}
			return Constants.getViewUnknown();
		}
	}

}
