/**
 * 
 */
package com.ssga.javacodereview.view;

import com.ssga.javacodereview.controller.Controller;
import com.ssga.javacodereview.util.Constants;

/**
 * @author e464150
 *
 */
public class ConsoleView implements IMyView {
	
	
	
	
	private  Controller ctrl = null;

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
		 ctrl.connect() ;
		 
		 
		 //enter the first loop
		boolean running = true;
		while( running ){
			this.showMsg(Constants.getViewTopMenuHead());
			String [] functions = ctrl.supportlist();
			String menus = "";
			int i;
			for (  i = 0 ;i < functions.length ; i ++ ){
				menus = menus + i + ":" + functions[i] + "   ";
			}
			menus = menus + i + ":" + "Exit";
			this.showMsg(menus);
			
			
		
		}
		
		
		// at last disconnect
		if( ctrl != null){
			ctrl.disconnect();
			ctrl = null;
		}

	}
	
	private void displayWelcome(){
		this.showMsg(Constants.getViewWelcomeMsg());
	}
	
	private void showMsg(String msg){
		System.out.println(msg);
	}
	private void showErrMsg(String msg){
		System.err.println("[Error]: "+msg);
	}
	

}
