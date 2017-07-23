package com.sales.service.model;

/**
 * returns success response
 * 
 * @author ramans
 *
 */
public class MsgSuccess {

	/**
	 * msg
	 */
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 
	 */

	public MsgSuccess() {

	}

	/**
	 * 
	 * @param msg
	 */
	public MsgSuccess(String msg) {
		this.msg = msg;

	}

}
