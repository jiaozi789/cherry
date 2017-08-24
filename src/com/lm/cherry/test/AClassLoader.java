package com.lm.cherry.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class AClassLoader extends URLClassLoader {

	public AClassLoader(URL[] paramArrayOfURL,ClassLoader parent) {
		super(paramArrayOfURL,parent);
	}
	public void addClassPath(String dir) throws MalformedURLException{
		this.addURL(new File(dir).toURI().toURL());
	}
}
