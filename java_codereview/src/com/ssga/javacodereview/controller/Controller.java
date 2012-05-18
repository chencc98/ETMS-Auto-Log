/**
 * 
 */
package com.ssga.javacodereview.controller;

import com.ssga.javacodereview.model.IMyConnection;
import com.ssga.javacodereview.util.Constants;
import com.ssga.javacodereview.util.ControllerRuntimeException;
import com.ssga.javacodereview.util.MyConnectionException;
import com.ssga.javacodereview.util.OperatorException;

/**
 * @author e464150 work as controller to connect data model and view
 */
public class Controller {

    private IMyConnection conn = null;
    private String errMsg = "";

    // the function list will be list here, mapping to the class under belw
    // package
    private String[] functionlist = { "Search", "Insert", "Update", "Delete" };
    // the supported function class package
    private String CLASS_PACKAGE_FUNCTION = "com.ssga.javacodereview.controller";

    public Controller(IMyConnection c) {
        this.conn = c;
    }

    public String getErrorMsg() {
        return this.errMsg;
    }

    /**
     * before do any function/action, we need to connect first
     */
    public void connect() {
        try {
            if (this.conn == null) {
                this.errMsg = Constants.getMsgNullConnection();
                throw new ControllerRuntimeException(this.errMsg);
            }
            conn.connect();

        } catch (MyConnectionException mce) {
            this.errMsg = mce.getMessage();
            throw new ControllerRuntimeException(this.errMsg, mce);
        }
    }

    /**
     * after use, please invoke disconnect
     */
    public void disconnect() {
        try {
            if (conn != null) {
                conn.disconnect();
                conn = null;

            }

        } catch (MyConnectionException mce) {
            this.errMsg = mce.getMessage();
            throw new ControllerRuntimeException(this.errMsg, mce);

        }
    }

    /**
     * 
     * @return the String array f function list
     */
    public String[] supportlist() {
        return this.functionlist;
    }

    /**
     * return the head/prompt msg of the function, so that view could display it
     * 
     * @param level
     *            this is the function name, such Search
     * @return prompt message
     */
    public String getHeadMsgByLevel(String level) {
        try {
            // load the class and run it
            IOperator op = (IOperator) Class.forName(
                    CLASS_PACKAGE_FUNCTION + "." + level).newInstance();
            return op.getHeadMsg();
        } catch (Exception e) {
            return Constants.getMsgUnSupport(level) + e.getMessage();
        }
    }

    /**
     * similar as {@link getHeadMsgByLevel}, to display help msg
     * 
     * @param level
     *            this is the function name, such Search
     * @return help message
     */
    public String getHelpMsgByLevel(String level) {
        try {
            IOperator op = (IOperator) Class.forName(
                    CLASS_PACKAGE_FUNCTION + "." + level).newInstance();
            return op.getHelpTips();
        } catch (Exception e) {
            return Constants.getMsgUnSupport(level) + e.getMessage();
        }
    }

    /**
     * the specified class will instanced and run the mainly function
     * 
     * @param function
     *            function name or supported class name
     * @param command
     *            detail input from view
     * @return process result message
     */
    public String process(String function, String command) {
        try {
            IOperator op = (IOperator) Class.forName(
                    CLASS_PACKAGE_FUNCTION + "." + function).newInstance();
            op.setConnection(conn); // set the data connection
            return op.operate(command);
        } catch (OperatorException oe) {
            return oe.getMessage();
        } catch (Exception e) {
            return Constants.getMsgUnSupport(function) + e.getMessage();
        }
    }

}
