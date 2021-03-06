package client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TestClient {

	private static Context jndi;
	
	public static InitialContext getInitialContext(String url) throws javax.naming.NamingException {
		Properties props = new Properties();
		props.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
		props.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
		props.put("java.naming.provider.url", url);

		return new InitialContext(props);
	}
	
	public static void main(String[] args) {
		try {
			jndi = TestClient.getInitialContext("localhost:1099");
			System.out.println(jndi);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
