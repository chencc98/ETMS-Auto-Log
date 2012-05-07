/**
 * 
 */
package com.ssga.javacodereview.main;

import com.ssga.javacodereview.controller.Controller;
import com.ssga.javacodereview.model.IMyConnection;
import com.ssga.javacodereview.model.XMLConnection;
import com.ssga.javacodereview.util.MyConnectionException;
import com.ssga.javacodereview.view.ConsoleView;
import com.ssga.javacodereview.view.IMyView;

/**
 * @author e464150
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		
		String file = "test.xml";
		try {
			IMyConnection con = new XMLConnection(file);
			//con.insert(new Employee("83231","ssPetere sds","",30));
			
			Controller ctrl = new Controller(con);
			
			IMyView view = new ConsoleView();
			view.start();
			view.work(ctrl);
			view.end();
		} catch (MyConnectionException e) {
			
			e.printStackTrace();
		}

	}

}
