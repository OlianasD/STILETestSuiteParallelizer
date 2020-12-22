package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class Main {
	
	public static URL[] createClassPath(String[] classes) {
		URL[] urls = new URL[classes.length];
		try {
			for(int i =0; i<urls.length; i++) {
				urls[i] = new URL(classes[i]);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return urls;
	}
	
    public static void main(String[] args){
    	String app = "addressbook";
    	String testCase = "AddressBookAddAddressBookTest";
    	String port = "3000";
    	HashMap<String, String[]> classPaths = new HashMap<>();
		classPaths.put("mantisbt", new String[] {"file:///home/anonymous/workspace/FSE19-submission-material/testsuite-mantisbt/target/classes/", "file:///home/anonymous/.p2/pool/plugins/org.testng_7.1.0.r202001120626.jar", "file:///home/anonymous/.p2/pool/plugins/com.beust.jcommander_1.72.0.jar", "file:///home/anonymous/.p2/pool/plugins/org.apache-extras.beanshell.bsh_2.0.0.b6.jar", "file:///home/anonymous/.p2/pool/plugins/org.yaml.snakeyaml_1.21.0.jar", "file:///home/anonymous/.m2/repository/io/github/bonigarcia/webdrivermanager/3.0.0/webdrivermanager-3.0.0.jar", "file:///home/anonymous/.m2/repository/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar", "file:///home/anonymous/.m2/repository/commons-io/commons-io/2.6/commons-io-2.6.jar", "file:///home/anonymous/.m2/repository/com/google/code/gson/gson/2.8.5/gson-2.8.5.jar", "file:///home/anonymous/.m2/repository/org/apache/commons/commons-lang3/3.7/commons-lang3-3.7.jar", "file:///home/anonymous/.m2/repository/org/apache/httpcomponents/httpclient/4.5.5/httpclient-4.5.5.jar", "file:///home/anonymous/.m2/repository/org/rauschig/jarchivelib/0.8.0/jarchivelib-0.8.0.jar", "file:///home/anonymous/.m2/repository/org/apache/commons/commons-compress/1.14/commons-compress-1.14.jar", "file:///home/anonymous/.m2/repository/org/jsoup/jsoup/1.11.3/jsoup-1.11.3.jar", "file:///home/anonymous/.m2/repository/junit/junit/4.12/junit-4.12.jar", "file:///home/anonymous/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-java/3.4.0/selenium-java-3.4.0.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-api/3.4.0/selenium-api-3.4.0.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-chrome-driver/3.4.0/selenium-chrome-driver-3.4.0.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-edge-driver/3.4.0/selenium-edge-driver-3.4.0.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-firefox-driver/3.4.0/selenium-firefox-driver-3.4.0.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-ie-driver/3.4.0/selenium-ie-driver-3.4.0.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-opera-driver/3.4.0/selenium-opera-driver-3.4.0.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-remote-driver/3.4.0/selenium-remote-driver-3.4.0.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-safari-driver/3.4.0/selenium-safari-driver-3.4.0.jar", "file:///home/anonymous/.m2/repository/cglib/cglib-nodep/3.2.4/cglib-nodep-3.2.4.jar", "file:///home/anonymous/.m2/repository/org/apache/commons/commons-exec/1.3/commons-exec-1.3.jar", "file:///home/anonymous/.m2/repository/commons-codec/commons-codec/1.10/commons-codec-1.10.jar", "file:///home/anonymous/.m2/repository/commons-logging/commons-logging/1.2/commons-logging-1.2.jar", "file:///home/anonymous/.m2/repository/org/w3c/css/sac/1.3/sac-1.3.jar", "file:///home/anonymous/.m2/repository/net/sourceforge/cssparser/cssparser/0.9.22/cssparser-0.9.22.jar", "file:///home/anonymous/.m2/repository/com/google/guava/guava/21.0/guava-21.0.jar", "file:///home/anonymous/.m2/repository/net/sourceforge/htmlunit/htmlunit/2.26/htmlunit-2.26.jar", "file:///home/anonymous/.m2/repository/net/sourceforge/htmlunit/htmlunit-core-js/2.26/htmlunit-core-js-2.26.jar", "file:///home/anonymous/.m2/repository/net/sourceforge/htmlunit/neko-htmlunit/2.25/neko-htmlunit-2.25.jar", "file:///home/anonymous/.m2/repository/org/apache/httpcomponents/httpcore/4.4.6/httpcore-4.4.6.jar", "file:///home/anonymous/.m2/repository/org/apache/httpcomponents/httpmime/4.5.3/httpmime-4.5.3.jar", "file:///home/anonymous/.m2/repository/org/eclipse/jetty/jetty-io/9.4.1.v20170120/jetty-io-9.4.1.v20170120.jar", "file:///home/anonymous/.m2/repository/org/eclipse/jetty/jetty-util/9.4.1.v20170120/jetty-util-9.4.1.v20170120.jar", "file:///home/anonymous/.m2/repository/net/java/dev/jna/jna/4.1.0/jna-4.1.0.jar", "file:///home/anonymous/.m2/repository/net/java/dev/jna/jna-platform/4.1.0/jna-platform-4.1.0.jar", "file:///home/anonymous/.m2/repository/com/codeborne/phantomjsdriver/1.4.0/phantomjsdriver-1.4.0.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/htmlunit-driver/2.26/htmlunit-driver-2.26.jar", "file:///home/anonymous/.m2/repository/javax/servlet/javax.servlet-api/3.1.0/javax.servlet-api-3.1.0.jar", "file:///home/anonymous/.m2/repository/org/eclipse/jetty/websocket/websocket-api/9.4.3.v20170317/websocket-api-9.4.3.v20170317.jar", "file:///home/anonymous/.m2/repository/org/eclipse/jetty/websocket/websocket-client/9.4.3.v20170317/websocket-client-9.4.3.v20170317.jar", "file:///home/anonymous/.m2/repository/org/eclipse/jetty/jetty-client/9.4.3.v20170317/jetty-client-9.4.3.v20170317.jar", "file:///home/anonymous/.m2/repository/org/eclipse/jetty/jetty-http/9.4.3.v20170317/jetty-http-9.4.3.v20170317.jar", "file:///home/anonymous/.m2/repository/org/eclipse/jetty/websocket/websocket-common/9.4.3.v20170317/websocket-common-9.4.3.v20170317.jar", "file:///home/anonymous/.m2/repository/xalan/serializer/2.7.2/serializer-2.7.2.jar", "file:///home/anonymous/.m2/repository/xalan/xalan/2.7.2/xalan-2.7.2.jar", "file:///home/anonymous/.m2/repository/xerces/xercesImpl/2.11.0/xercesImpl-2.11.0.jar", "file:///home/anonymous/.m2/repository/xml-apis/xml-apis/1.4.01/xml-apis-1.4.01.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-support/3.4.0/selenium-support-3.4.0.jar", "file:///home/anonymous/.m2/repository/mysql/mysql-connector-java/6.0.5/mysql-connector-java-6.0.5.jar", "file:///home/anonymous/.m2/repository/com/google/inject/guice/4.2.2/guice-4.2.2-no_aop.jar"});
		classPaths.put("addressbook", new String[] {"file:///home/anonymous/workspace/FSE19-submission-material/testsuite-addressbook-testng/target/classes/", "file:///home/anonymous/.p2/pool/plugins/org.testng_7.1.0.r202001120626.jar", "file:///home/anonymous/.p2/pool/plugins/com.beust.jcommander_1.72.0.jar", "file:///home/anonymous/.p2/pool/plugins/org.apache-extras.beanshell.bsh_2.0.0.b6.jar", "file:///home/anonymous/.p2/pool/plugins/org.yaml.snakeyaml_1.21.0.jar", "file:///home/anonymous/.m2/repository/io/github/bonigarcia/webdrivermanager/3.0.0/webdrivermanager-3.0.0.jar", "file:///home/anonymous/.m2/repository/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar", "file:///home/anonymous/.m2/repository/commons-io/commons-io/2.6/commons-io-2.6.jar", "file:///home/anonymous/.m2/repository/com/google/code/gson/gson/2.8.5/gson-2.8.5.jar", "file:///home/anonymous/.m2/repository/org/apache/commons/commons-lang3/3.7/commons-lang3-3.7.jar", "file:///home/anonymous/.m2/repository/org/apache/httpcomponents/httpclient/4.5.5/httpclient-4.5.5.jar", "file:///home/anonymous/.m2/repository/org/apache/httpcomponents/httpcore/4.4.9/httpcore-4.4.9.jar", "file:///home/anonymous/.m2/repository/commons-logging/commons-logging/1.2/commons-logging-1.2.jar", "file:///home/anonymous/.m2/repository/commons-codec/commons-codec/1.10/commons-codec-1.10.jar", "file:///home/anonymous/.m2/repository/org/rauschig/jarchivelib/0.8.0/jarchivelib-0.8.0.jar", "file:///home/anonymous/.m2/repository/org/apache/commons/commons-compress/1.14/commons-compress-1.14.jar", "file:///home/anonymous/.m2/repository/org/jsoup/jsoup/1.11.3/jsoup-1.11.3.jar", "file:///home/anonymous/.m2/repository/junit/junit/4.12/junit-4.12.jar", "file:///home/anonymous/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-java/3.141.59/selenium-java-3.141.59.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-api/3.141.59/selenium-api-3.141.59.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-chrome-driver/3.141.59/selenium-chrome-driver-3.141.59.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-edge-driver/3.141.59/selenium-edge-driver-3.141.59.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-firefox-driver/3.141.59/selenium-firefox-driver-3.141.59.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-ie-driver/3.141.59/selenium-ie-driver-3.141.59.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-opera-driver/3.141.59/selenium-opera-driver-3.141.59.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-remote-driver/3.141.59/selenium-remote-driver-3.141.59.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-safari-driver/3.141.59/selenium-safari-driver-3.141.59.jar", "file:///home/anonymous/.m2/repository/org/seleniumhq/selenium/selenium-support/3.141.59/selenium-support-3.141.59.jar", "file:///home/anonymous/.m2/repository/net/bytebuddy/byte-buddy/1.8.15/byte-buddy-1.8.15.jar", "file:///home/anonymous/.m2/repository/org/apache/commons/commons-exec/1.3/commons-exec-1.3.jar", "file:///home/anonymous/.m2/repository/com/google/guava/guava/23.0/guava-23.0.jar", "file:///home/anonymous/.m2/repository/com/google/code/findbugs/jsr305/1.3.9/jsr305-1.3.9.jar", "file:///home/anonymous/.m2/repository/org/checkerframework/checker-compat-qual/2.0.0/checker-compat-qual-2.0.0.jar", "file:///home/anonymous/.m2/repository/com/google/errorprone/error_prone_annotations/2.1.3/error_prone_annotations-2.1.3.jar", "file:///home/anonymous/.m2/repository/com/google/j2objc/j2objc-annotations/1.1/j2objc-annotations-1.1.jar", "file:///home/anonymous/.m2/repository/org/codehaus/mojo/animal-sniffer-annotations/1.14/animal-sniffer-annotations-1.14.jar", "file:///home/anonymous/.m2/repository/com/squareup/okhttp3/okhttp/3.11.0/okhttp-3.11.0.jar", "file:///home/anonymous/.m2/repository/com/squareup/okio/okio/1.14.0/okio-1.14.0.jar", "file:///home/anonymous/.m2/repository/mysql/mysql-connector-java/6.0.5/mysql-connector-java-6.0.5.jar", "file:///home/anonymous/.m2/repository/com/google/inject/guice/4.2.2/guice-4.2.2.jar", "file:///home/anonymous/.m2/repository/javax/inject/javax.inject/1/javax.inject-1.jar", "file:///home/anonymous/.m2/repository/aopalliance/aopalliance/1.0/aopalliance-1.0.jar"});
		TestNG testng = new TestNG();
		TestListenerAdapter tla = new TestListenerAdapter();
		testng.setUseDefaultListeners(false);
		MyClassLoader classLoader = new MyClassLoader(createClassPath(classPaths.get(app)));
		Class<?> cls = null;
		try {
			cls = classLoader.findClass("tests."+testCase);
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found for test "+testCase);
			System.exit(3);
		}
		
		prepareTestCase(testCase, port, testng, tla, classLoader, cls);
		testng.run();
		List<ITestResult> passed = tla.getPassedTests();
		List<ITestResult> failed = tla.getFailedTests();
		List<ITestResult> skipped = tla.getSkippedTests();
		if(!passed.isEmpty()) {
			System.out.println("Passed");
			System.exit(0);
		}
		else if(!failed.isEmpty()) {
			System.out.println("Failed");
			String line = "PORT: "+port+"\n";
			line += failed.get(0).getThrowable().toString()+":"+failed.get(0).getThrowable().getMessage();
			writeLineToFile(testCase, line);
			System.exit(1);
		}
		else if(!skipped.isEmpty()) {
			System.out.println("Skipped");
			String line = "PORT: "+port+"\n";
			line += skipped.get(0).getSkipCausedBy().toString();
			line += skipped.get(0).getThrowable().toString()+":"+skipped.get(0).getThrowable().getMessage();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			skipped.get(0).getThrowable().printStackTrace(pw);
			line += "\n"+sw.toString();
			writeLineToFile(testCase, line);
			System.exit(2);
		}
		
    }
    
    public static void writeLineToFile(String testCase, String line) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("/home/anonymous/"+testCase+".txt"));
			writer.write(line);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void prepareTestCase(String testCase, String port, TestNG testng, TestListenerAdapter tla,
			MyClassLoader classLoader, Class<?> cls) {
		XmlSuite suite = new XmlSuite();
		XmlTest test = new XmlTest();
		XmlClass clz = new XmlClass();
		testng.addClassLoader(classLoader);
		Map<String, String> params = new HashMap<>();
		params.put("port", String.valueOf(port));
		clz.setClass(cls);
		clz.setParameters(params);
		List<XmlClass> clzList = new ArrayList<>();
		clzList.add(clz);
		test.setClasses(clzList);
		test.setName(testCase);
		test.setParameters(params);
		suite.setName(testCase+"_SUITE_"+port);
		test.setSuite(suite);
		suite.addTest(test);
		suite.setParameters(params);
		testng.setCommandLineSuite(suite);
		testng.addListener(tla);
	}
}
