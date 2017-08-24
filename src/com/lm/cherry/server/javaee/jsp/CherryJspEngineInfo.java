package com.lm.cherry.server.javaee.jsp;

import javax.servlet.jsp.JspEngineInfo;
/**
 * jsp的版本信息
 * 日期:2015-5-30
 * 作者:LM
 */
public class CherryJspEngineInfo extends JspEngineInfo {

	@Override
	public String getSpecificationVersion() {
		return "2.5";
	}

}
