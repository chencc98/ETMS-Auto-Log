/**
 * 
 */
package org.chencc98.etmslog.entity;

/**
 * @author chencarl
 *
 */
public class UserProperty {
	
	private String username = System.getProperty("ETMS_USERNAME", "480466");
	private String passwod = System.getProperty("ETMS_PASSWORD", "project88");
	private String fullname = System.getProperty("ETMS_FULLNAME", "≥¬…Ÿ¡·");
	public String getUsername(){
		return username;
	}
	public String getFullname(){
		return fullname;
	}
	public String getPassword(){
		return passwod;
	}
	public String getDefaultEvents(){
		String str = "91|WL|1|I_31|DIF|1|I_31|DIF|1|I_31|DIF|1|I_31|DIF|1|I_31|DIF|1|I_91|WL|1|I";
		str = str + "_91|WL|2|I_31|DIF|2|I_31|DIF|2|I_31|DIF|2|I_31|DIF|2|I_31|DIF|2|I_91|WL|2|I";
		return str;
	}
}
