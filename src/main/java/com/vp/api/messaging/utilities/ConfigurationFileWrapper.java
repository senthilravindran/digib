package com.vp.api.messaging.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class ConfigurationFileWrapper {

	private static Properties prop = new Properties();
	private static Properties temp_prop = new Properties();

	static {

		System.out.println("First - looking for property file config.properties in classpath");
		InputStream inputStream = ConfigurationFileWrapper.class.getClassLoader().getResourceAsStream("config.properties");

		if (inputStream != null) {
			try {
				prop.load(inputStream);
				System.out.println("Found config.properties in classpath");

			} catch (IOException e) {
				System.out.println("Property file config.properties could not get loaded - please check format");
			}
		} else {
			System.out.println("Property file config.properties not found in the classpath...");
		}

		FileInputStream file = null;
		String path = "./config.properties";

		System.out.println("Looking for property file: " + path);

		try {
			file = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			System.out.println("Was not able to find file ./config.properties");
		}

		if (file != null) {
			try {
				temp_prop.load(file);
				System.out.println("Found file ./config.properties file... merging...");
				Set<Object> ks = temp_prop.keySet();
				for (Object k : ks) {
					System.out.println("Overriding key: " + (String)k + " = " + (String)temp_prop.get(k));
					prop.setProperty((String)k, (String)temp_prop.get(k));
				}

			} catch (Exception e) {
				System.out.println("Property file config.properties could not get loaded - please check format");
			}
			System.out.println("All done...");
		}

	}

	public static boolean getBooleanValue(String key) {
		String value = prop.getProperty(key);
		if (value.equalsIgnoreCase("true")) {
			return true;

		} else if (value.equalsIgnoreCase("false")) {
			return false;
		} else {
			return false;
		}
	}

	public static String getPropValue(String key) throws IOException {

		return prop.getProperty(key);
	}

	public static long getLongPropValue(String key) throws IOException {
		return Long.parseLong(prop.getProperty(key));
	}

	public static int getIntPropValue(String key) throws IOException {
		return Integer.parseInt(prop.getProperty(key));
	}

	public static long getLongPropValueDefault(String key, long defaultvalue) {
		if (contains(key)) {
			try {
				return getLongPropValue(key);
			} catch (IOException e) {
				e.printStackTrace();
				return defaultvalue;
			}
		} else {
			return defaultvalue;
		}

	}

	public static int getIntPropValueDefault(String key, int defaultvalue) {
		if (contains(key)) {
			try {
				return getIntPropValue(key);
			} catch (IOException e) {
				e.printStackTrace();
				return defaultvalue;
			}
		} else {
			return defaultvalue;
		}

	}

	public static boolean getBooleanPropValueDefault(String key, boolean defaultvalue) {
		if (contains(key)) {
			try {
				return getBooleanValue(key);
			} catch (Exception e) {
				e.printStackTrace();
				return defaultvalue;
			}
		} else {
			return defaultvalue;
		}

	}

	public static String getPropValue(String key, String defaultvalue) {
		if (contains(key)) {
			try {
				return getPropValue(key);
			} catch (IOException e) {
				e.printStackTrace();
				return defaultvalue;
			}
		} else {
			return defaultvalue;
		}

	}

	public static Set<Object> getPropKeys() {
		return prop.keySet();
	}

	public static boolean contains(String key) {
		if (prop.containsKey(key)) {

			String tmp = prop.getProperty(key);
			if (tmp.trim().isEmpty()) {
				return false;
			} else {
				return true;
			}
		}
		return false;

	}

	public static void main(String[] argv) throws IOException {
		File currentDirectory = new File(new File(".").getAbsolutePath());
		System.out.println(currentDirectory.getCanonicalPath());
		System.out.println(currentDirectory.getAbsolutePath());

		System.out.println(getPropKeys());
		if (contains("subscriber-tertiary-broker-port")) {
			System.out.println(getPropValue("subscriber-tertiary-broker-port"));
		}
		System.out.println(getPropKeys());
		if (contains("foo")) {
			System.out.println(getPropValue("foo"));
		}

	}

}
