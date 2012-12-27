package swim.util;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ContextUtil {
	public static InitialContext getInitialContext() throws NamingException {
		Properties env = new Properties();
		env.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
	 	env.setProperty("java.naming.provider.url", "localhost:1099");
	 	env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
	 	return new InitialContext(env);
	}
}
