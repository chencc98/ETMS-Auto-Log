/**
 * 
 */
package com.ssga.javacodereview.view;

import com.ssga.javacodereview.controller.Controller;

/**
 * @author e464150
 *
 */
public interface IMyView {
	
	public void start();
	public void work( Controller ctrl);
	public void end();
}
