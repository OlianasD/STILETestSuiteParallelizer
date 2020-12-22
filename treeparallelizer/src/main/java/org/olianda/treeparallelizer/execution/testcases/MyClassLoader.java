package org.olianda.treeparallelizer.execution.testcases;
import java.net.URL;
import java.net.URLClassLoader;

public class MyClassLoader extends URLClassLoader {

	public MyClassLoader(URL[] urls) {
		super(urls);
	}
	
	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
		return super.findClass(name);

	}

}
