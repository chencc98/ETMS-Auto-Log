/**
 * 
 */
package com.ssga.javacodereview.controller;

import com.ssga.javacodereview.model.entity.Employee;
import com.ssga.javacodereview.util.Constants;

/**
 * @author asus
 * the input from view should have basic synctax. here we will check the syntax
 * <eid>,[name],[sid],[age]:  employee id is required, other three fields are optional.
 * another form, id=<eid>,name=[name],superiorid=[sid],age=[age]
 */
public class OperatorCommandCheck {
    
    public static Employee checkCommand(String command){
        Employee employee = null;
        if( command == null || command.trim().equals("")){
            //do nothing return null
            return employee;
        }else{
            command = command.trim();
            String [] commandparts = command.split(",");
            employee = new Employee();
            boolean check = setEmployeeField( employee, Constants.SEARCH_KEY_ID, commandparts[0]);
            if( !check ){
                return null;
                
            }
            
            if( commandparts.length >= 2 ){
                check = setEmployeeField( employee, Constants.SEARCH_KEY_NAME, commandparts[1]);
                if( !check ){
                    return null;
                }
            }
            
            if( commandparts.length >= 3 ){
                check = setEmployeeField( employee, Constants.SEARCH_KEY_SID, commandparts[2]);
                if( !check ){
                    return null;
                }
            }
            
            if( commandparts.length >= 4 ){
                check = setEmployeeField( employee, Constants.SEARCH_KEY_AGE, commandparts[3]);
                if( !check ){
                    return null;
                }
            }
            
            if( commandparts.length >= 5 ){
                return null;
            }
            
            return employee;
        }
    }
    
    private static boolean setEmployeeField( Employee em, String field, String part){
        boolean r = false;
        if( field.equals(Constants.SEARCH_KEY_ID) ){   //check the id field
            if( part == null || part.trim().equals("") ){
                r =  false;
            }else{
                String [] keyvalue = part.split("=");
                if( keyvalue.length == 1 ){
                    em.setId(part.trim());
                    r = true;
                }else if( keyvalue.length ==2 && keyvalue[0].equals(Constants.SEARCH_KEY_ID)){
                    em.setId(keyvalue[1].trim());
                    r = true;
                }else{
                    r= false;
                }
            }
        }else if( field.equals(Constants.SEARCH_KEY_NAME) ){
            if( part == null ){
                em.setName("");
                r = true;
            }else{
                String [] keyvalue = part.split("=");
                if( keyvalue.length == 1 ){
                    em.setName(part.trim());
                    r = true;
                }else if( keyvalue.length ==2 && keyvalue[0].equals(Constants.SEARCH_KEY_NAME)){
                    em.setName(keyvalue[1].trim());
                    r = true;
                }else{
                    r = false;
                }
            }
        }else if( field.equals(Constants.SEARCH_KEY_SID) ){
            if( part == null ){
                em.setSuperid("");
                r = true;
            }else{
                String [] keyvalue = part.split("=");
                if( keyvalue.length == 1 ){
                    em.setSuperid(part.trim());
                    r = true;
                }else if( keyvalue.length ==2 && keyvalue[0].equals(Constants.SEARCH_KEY_SID)){
                    em.setSuperid(keyvalue[1].trim());
                    r = true;
                }else{
                    r = false;
                }
            }
        }else if( field.equals(Constants.SEARCH_KEY_AGE) ){
            if( part == null || part.trim().equalsIgnoreCase(Constants.COMMAND_NOCHANGE)
                    || part.trim().equals("")){
                em.setAge(Constants.AGE_NOT_SET);
                r = true;
            }else{
                String [] keyvalue = part.split("=");
                if( keyvalue.length == 1 ){
                    em.setAge(convertAge(part.trim()));
                    if( em.getAge() == Constants.AGE_PARSE_ERROR ){
                        r = false;
                    }else{
                        r = true;
                    }
                }else if( keyvalue.length ==2 && keyvalue[0].equals(Constants.SEARCH_KEY_AGE)){
                    em.setAge(convertAge(keyvalue[1].trim()));
                    if( em.getAge() == Constants.AGE_PARSE_ERROR ){
                        r = false;
                    }else{
                        r = true;
                    }
                }else{
                    r = false;
                }
            }
        }else{
            r = false;
        }
        
        return r;
    }
    
    /**
     * if age is not set, its value will be -1, if any parse error, its value will be -2
     */
    private static int convertAge(String age){
        try{
            if( age == null || "".equals(age.trim())){
                return Constants.AGE_NOT_SET;
            }
            int r = Integer.parseInt(age);
            if( r < 0 ){
                return Constants.AGE_PARSE_ERROR;
            }
            return r ;
        }catch(NumberFormatException nfe){
            return Constants.AGE_PARSE_ERROR;
        }
    }

}
