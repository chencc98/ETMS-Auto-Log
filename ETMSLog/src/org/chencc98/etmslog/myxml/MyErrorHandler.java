/**
 * 
 */
package org.chencc98.etmslog.myxml;

import org.jdom2.input.sax.BuilderErrorHandler;
import org.xml.sax.SAXParseException;

/**
 * @author asus
 *
 */
public class MyErrorHandler extends BuilderErrorHandler {
	
	public void fatalError(org.xml.sax.SAXParseException exception) throws SAXParseException{
		if( exception.getMessage().contains("link")){
			//do nothing
		}else{
			throw exception;
		}
	}

}
