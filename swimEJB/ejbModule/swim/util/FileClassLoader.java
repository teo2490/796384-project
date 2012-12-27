package swim.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Class loader custom per il caricamento da file jar
 * @author Giusto - Mazuran
 */
public class FileClassLoader extends URLClassLoader {
	
	public FileClassLoader() {
		super(new URL[] {});
	}
	
	/**
	 * Aggiunge un file jar al classpath
	 * @param file file jar da aggiungere
	 */
	public void addFile(File file) {
		String urlPath = file.toURI().toString();
		try {
			this.addURL(new URL(urlPath));
		} catch (MalformedURLException e) {}
	}
	
}
