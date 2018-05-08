package com.lm.cherry.server;

import com.lm.cherry.protocal.http.Http11Protocal;
/**
 * 请求响应中 响应的接口
 *  作者:LM
 * 创建日期:2015-1-30下午04:11:19
 * 项目名称:cherry
 * 包名称:com.lm.cherry.server
 * 文件名:Response.java
 *
 */
public interface Response {
	/**
	 * 处理响应  传入当前请求协议信息
	 * @param httpProtocal
	 * @throws Exception
	 */
	public void processResponse(Http11Protocal httpProtocal)throws Exception;
}
