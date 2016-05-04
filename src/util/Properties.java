package util;

public class Properties {
	private static java.util.Properties properties;
	
	private Properties() {
		properties = new java.util.Properties();
	}
	
	public static java.util.Properties getProperties() {
		if (properties == null) {
			new Properties();
		}
		return properties;
	}
}