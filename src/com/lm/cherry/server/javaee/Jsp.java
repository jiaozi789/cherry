package com.lm.cherry.server.javaee;
/**
 * jsp文件信息
 * @author Administrator
 *
 */
public class Jsp {
	private String jspPath;
	private String rootPath;
	public Jsp(String jspPath, String rootPath) {
		super();
		this.jspPath = jspPath;
		this.rootPath = rootPath;
	}
	public String getJspPath() {
		return jspPath;
	}
	public void setJspPath(String jspPath) {
		this.jspPath = jspPath;
	}
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	
	
}
