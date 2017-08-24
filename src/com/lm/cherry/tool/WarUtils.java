package com.lm.cherry.tool;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 解压war文件的处理类
 */
public class WarUtils {
	/**
	 * 解压war文件到指定的目录
	 * @param warPath
	 * @param destPath
	 */
	public static void unWar(String warPath, String destPath) {
		WarUtils.unWar(new File(warPath), new File(destPath));
	}
	/**
	 * 解压war文件到指定的文件
	 * @param warPath
	 * @param destPath
	 */
	public static void unWar(File jarFile, File destination) {
		JarFile jar = null;
		try {
			if (destination.exists() == false) {
				destination.mkdirs();
			}
			jar = new JarFile(jarFile);
			Enumeration<JarEntry> en = jar.entries();
			JarEntry entry = null;
			InputStream input = null;
			BufferedOutputStream bos = null;
			File file = null;
			while (en.hasMoreElements()) {
				entry = en.nextElement();
				input = jar.getInputStream(entry);
				file = new File(destination, entry.getName());
				if (entry.isDirectory()) {
					file.mkdirs();
					continue;
				} else {
					file.getParentFile().mkdirs();
				}
				bos = new BufferedOutputStream(new FileOutputStream(file));
				byte[] buffer = new byte[8192];
				int length = -1;
				while (true) {
					length = input.read(buffer);
					if (length == -1)
						break;
					bos.write(buffer, 0, length);
				}
				bos.close();
				input.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (jar != null) {
				try {
					jar.close();
				} catch (Exception e) {
				}
			}
		}
	}

}