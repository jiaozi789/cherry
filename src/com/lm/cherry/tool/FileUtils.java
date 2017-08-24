package com.lm.cherry.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class FileUtils {
	/**
	 * д���ļ����ݵ��ļ���
	 * ����:2015-5-29
	 * ����:LM
	 * @param file
	 * @param data
	 * @param encoding
	 * @throws IOException
	 */
	public static void writeStringToFile(File file, String data, String encoding)
			throws IOException {
		OutputStream out = null;
		try {
			out = openOutputStream(file);
			byte[] bts=encoding!=null?new String(data).getBytes(encoding):new String(data).getBytes();
			out.write(bts);
		} finally {
			out.close();
		}
	}
	/**
	 * ��ȡ�ļ�������
	 * ����:2015-5-29
	 * ����:LM
	 * @param jspObj
	 * @return
	 * @throws Exception
	 */
	public static String getContent(File jspObj) throws Exception{
		FileInputStream fi=new FileInputStream(jspObj);
		BufferedReader br=new BufferedReader(new InputStreamReader(fi));
		StringBuffer sb=new StringBuffer();
		String line=null;
		while((line=br.readLine())!=null){
			sb.append(line);
		}
		br.close();
		fi.close();
		return sb.toString();
	}
	/**
	 * ���ļ��������
	 * ����:2015-5-29
	 * ����:LM
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static FileOutputStream openOutputStream(File file)
			throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file
						+ "' exists but is a directory");
			}
			if (!(file.canWrite()))
				throw new IOException("File '" + file
						+ "' cannot be written to");
		} else {
			File parent = file.getParentFile();
			if ((parent != null) && (!(parent.exists()))
					&& (!(parent.mkdirs()))) {
				throw new IOException("File '" + file
						+ "' could not be created");
			}
		}
		return new FileOutputStream(file);
	}
}
