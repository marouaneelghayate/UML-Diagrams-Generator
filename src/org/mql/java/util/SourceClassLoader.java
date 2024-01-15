package org.mql.java.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class SourceClassLoader {

	public synchronized static Class<?> loadClass(String path, String className) {
		try {
			File f = new File(path);
			URL[] urls = {f.toURI().toURL()};
			@SuppressWarnings("resource")
			URLClassLoader urlcl = new URLClassLoader(urls);
			Class<?> cls  = urlcl.loadClass(className);
			return cls;
			
		} catch (Exception e) {
		}
		
		return null;
	}

}
