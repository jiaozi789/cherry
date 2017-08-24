package com.lm.cherry.server.error;
/**
 * 异常类
  *时间：2017-2-8 上午09:15:14
  *作者： LM
  *联系方式：973465719@qq.com
 *
 */
public class Exception {
	/**
	 * 消息
	 */
	private String msg;
	/**
	 * 状态码
	 */
	private String status;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
