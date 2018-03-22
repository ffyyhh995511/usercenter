package org.cloud.usercenter.exception;

import java.io.Serializable;

import org.cloud.usercenter.response.ResponseEnum2;
import org.cloud.usercenter.response.ResponseUtil;

/**
 * @Description:TODO
 * @author:fangyunhe
 * @time:2018年3月21日 上午11:52:55
 * @version 1.0	
 */
public class GException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;
	private int errorCode = ResponseEnum2.STATUS500.getCode();
	private String msg;
	
	public GException() {
		super("GException");
		this.msg = ResponseUtil.getMsgByCode(errorCode);
	}

	public GException(int errorCode) {
		super("GException");
		this.errorCode = errorCode;
		this.msg = ResponseUtil.getMsgByCode(errorCode);
	}
	public GException(Exception e) {
		super(e);
		this.errorCode = ResponseEnum2.STATUS500.getCode();
		this.msg = e.getMessage();
	}
	
	public GException(int errorCode, String msg, Exception e) {
		super(msg, e);
		this.errorCode = errorCode;
		this.msg = msg;
	}
	
	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}
	
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}