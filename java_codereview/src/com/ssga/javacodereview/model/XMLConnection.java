/**
 * 
 */
package com.ssga.javacodereview.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.ssga.javacodereview.model.entity.Employee;
import com.ssga.javacodereview.util.MyConnectionException;
import com.ssga.javacodereview.util.Constants;

/**
 * @author e464150
 *
 */
public class XMLConnection implements IMyConnection {
	private String xmlfile = ""; 
	private Document xmldoc;
	
	
	public XMLConnection(String file) throws MyConnectionException{
		xmlfile = file;
		SAXBuilder builder=new SAXBuilder();
		try{
			xmldoc = builder.build(new File(xmlfile));
		}catch(JDOMException je){
			throw new MyConnectionException(Constants.getConnectionExceptionParseErrorMsg(), je);
		}catch(IOException ie){
			throw new MyConnectionException(Constants.getConnectionExceptionIOErrorMsg(), ie);
		}
	}

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.model.IMyConnection#delete(java.lang.String)
	 */
	public void delete(String eid, boolean totop) throws MyConnectionException {
		Employee em = searchById(eid);
		if( em == null ){
			throw new MyConnectionException(Constants.getConnectionExceptionNoSuchEmployee(eid));
		}
		
		String childnewsup = "";
		if( ! totop ){
			childnewsup = em.getSuperid();
		}
		
		Properties p = new Properties();
		p.put(Constants.SEARCH_KEY_SID, em.getId());
		
		List<Employee> list = this.search(p);
		for( Employee sub : list ){
			sub.setSuperid(childnewsup);
		}
		if( list.size() > 0 ){
			int n = this.updateList(list);
			if(  n == list.size()){
				//do nothing, update is good
			}else{
				throw new MyConnectionException(Constants.getConnectionExceptionPartUpdateErr(n, list.size()));
			}
		}
		
		
	}

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.model.IMyConnection#insert(com.ssga.javacodereview.model.entity.Employee)
	 */
	public void insert(Employee em) throws MyConnectionException {
		Employee s = searchById( em.getId());
		if( s != null ){
			throw new MyConnectionException( Constants.getConnectionExceptionExisting(s.getId(), s.getName()));
		}
		
		if( !em.getSuperid().equals("")){
			s = searchById( em.getSuperid() );
			if( s == null ){
				throw new MyConnectionException ( Constants.getConnectionExceptionNoSuchEmployee(em.getSuperid()));
			}
		}
		
		try{
			Element root = xmldoc.getRootElement();    //IllegalStateException
			Element child = Employee2XMLElement(em);
			root.addContent(child);
			
			save();
		}catch(IllegalStateException ie){
			throw new MyConnectionException(Constants.getConnectionExceptionXMLWrongState(), ie);
		} 
		
		
	}

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.model.IMyConnection#search(java.util.Properties)
	 */
	public List<Employee> search(Properties p) throws MyConnectionException {
		List<Employee> list = new ArrayList<Employee> ();
		String searchid = p.containsKey(Constants.SEARCH_KEY_ID) 
							? p.getProperty(Constants.SEARCH_KEY_ID)
							: "";
		String searchname = p.containsKey(Constants.SEARCH_KEY_NAME)
							? p.getProperty(Constants.SEARCH_KEY_NAME)
							: "";
		String searchsid = p.containsKey(Constants.SEARCH_KEY_SID)
							? p.getProperty(Constants.SEARCH_KEY_SID)
							: "";
		String searchage = p.containsKey(Constants.SEARCH_KEY_AGE)
							? p.getProperty(Constants.SEARCH_KEY_AGE)
							: "";
		String searchageop = p.containsKey(Constants.SEARCH_KEY_AGEOP)
							? p.getProperty(Constants.SEARCH_KEY_AGEOP)
							: "";
		
		try{
			Element root = xmldoc.getRootElement();    //IllegalStateException
			List<Element> liste = root.getChildren();
			Iterator<Element> it = liste.iterator();
			while( it.hasNext() ){
				Element item = (Element) it.next();
				Employee em = XMLElement2Employee( item );
				if( _match( em, searchid, searchname, searchsid, searchage, searchageop)){
					list.add(em);
				}
			}
		}catch(IllegalStateException ie){
			throw new MyConnectionException(Constants.getConnectionExceptionXMLWrongState(), ie);
		}
		return list;
	}
	
	public Employee searchById( String id) throws MyConnectionException{
		Properties p = new Properties();
		p.setProperty(Constants.SEARCH_KEY_ID, id);
		List<Employee> list = search(p);
		if( list.size() == 0 ){
			return null;
		}else if ( list.size() > 1 ){
			throw new MyConnectionException(Constants.getConnectionExceptionMultipleEmployee(id));
		}else{
			return list.get(0);
		}
	}
	
	private boolean _match(Employee em, String searchid, String searchname, String searchsid,
		String searchage, String searchageop ){
		if( searchid == null || searchid.equals("") || searchid.equals("*") 
				|| em.getId().equals(searchid) ){
			//do nothing
		}else {
			return false;
		}
		
		if( searchname == null || searchname.equals("") || searchname.equals("*")
				|| em.getName().indexOf(searchname) != -1 ){
			//do nothing
		}else{
			return false;
		}
		
		if( searchsid == null || searchsid.equals("") || searchsid.equals("*") 
				|| em.getSuperid().equals(searchsid) ){
			//do nothing
		}else {
			return false;
		}
		
		if( searchage == null || searchage.equals("")){
			//do nothing
		}else if( searchageop.equals(Constants.SEARCH_KEY_AGEOP_EQ) 
				&& Integer.parseInt(searchage) == em.getAge()){
			//do nothing
		}else if( searchageop.equals(Constants.SEARCH_KEY_AGEOP_GE)
				&& Integer.parseInt(searchage) <= em.getAge()){
			//do nothing
		}else if( searchageop.equals(Constants.SEARCH_KEY_AGEOP_LE)
				&& Integer.parseInt(searchage) >= em.getAge()){
			//do nothing
		}else{
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.ssga.javacodereview.model.IMyConnection#update(com.ssga.javacodereview.model.entity.Employee)
	 */
	public void update(Employee em) throws MyConnectionException {
		try{
			Element root = xmldoc.getRootElement();    //IllegalStateException
			List<Element> liste = root.getChildren();
			Iterator<Element> it = liste.iterator();
			boolean matched = false;
			while( it.hasNext() ){
				Element item = (Element) it.next();
				if( item.getChildText(Constants.SEARCH_KEY_ID).equals(em.getId())){
					matched = true;
					this.updateXMLEmployee(em, item);
					save();
					break;
				}
			}
			
			if( !matched ){
				throw new MyConnectionException(Constants.getConnectionExceptionNoSuchEmployee(em.getId()) );
			}
		}catch(IllegalStateException ie){
			throw new MyConnectionException(Constants.getConnectionExceptionXMLWrongState(), ie);
		}
		
	}

	public int updateList(List<Employee> list )throws MyConnectionException{
		int matched = 0;
		try{
			Element root = xmldoc.getRootElement();    //IllegalStateException
			List<Element> liste = root.getChildren();
			Iterator<Element> it = liste.iterator();
			
			while( it.hasNext() ){
				Element item = (Element) it.next();
				for( Employee em : list){
					if( item.getChildText(Constants.SEARCH_KEY_ID).equals(em.getId())){
						matched ++;
						this.updateXMLEmployee(em, item);
						break;
					}
				}
			}
			
			if( matched == 0 ){
				String ids = "";
				for( Employee tmp : list ){
					ids = ids + tmp.getId() + ",";
				}
				ids = "[" + (ids.endsWith(",")? ids.substring(0, ids.length() -1) : ids ) + "]";
				throw new MyConnectionException(Constants.getConnectionExceptionNoSuchEmployee(ids));
			}else{
				save();
				return matched;
			}
			
		}catch(IllegalStateException ie){
			throw new MyConnectionException(Constants.getConnectionExceptionXMLWrongState(), ie);
		}
		
	}
	
	private void save() throws MyConnectionException {
		try{
			FileWriter fw = new FileWriter( xmlfile);
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			xmlOut.output(xmldoc, fw);
		}catch (IOException e) {
			throw new MyConnectionException(Constants.getConnectionExceptionIOErrorMsg(), e);
		}
	}
	
	
	private Employee XMLElement2Employee( Element el ){
		Element item = el;
		Employee em = new Employee( item.getChildText(Constants.SEARCH_KEY_ID),
				item.getChildText(Constants.SEARCH_KEY_NAME),
				item.getChildText(Constants.SEARCH_KEY_SID),
				Integer.parseInt(item.getChildText(Constants.SEARCH_KEY_AGE)));
		return em;
	}
	
	private Element Employee2XMLElement( Employee em ){
		Element child = new Element(Constants.XML_EMPLOYEE_TAG);
		Element xmlid = new Element( Constants.SEARCH_KEY_ID).setText(em.getId());
		Element xmlname = new Element(Constants.SEARCH_KEY_NAME).setText(em.getName());
		Element xmlsid = new Element(Constants.SEARCH_KEY_SID).setText(em.getSuperid());
		Element xmlage = new Element(Constants.SEARCH_KEY_AGE).setText(""+em.getAge());
		
		child.addContent(xmlid);
		child.addContent(xmlname);
		child.addContent(xmlsid);
		child.addContent(xmlage);
		return child;
	}
	
	private void updateXMLEmployee( Employee em, Element el){
		el.getChild(Constants.SEARCH_KEY_NAME).setText(em.getName());
		el.getChild(Constants.SEARCH_KEY_SID).setText(em.getSuperid());
		el.getChild(Constants.SEARCH_KEY_AGE).setText(""+em.getAge());
	}

	public void connect() throws MyConnectionException {
		// do nothing
		
	}

	public void disconnect() throws MyConnectionException {
		// do nothing
		
	}

}
