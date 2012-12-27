package swim.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

/**
 * Classe di supporto per la gestione dei nomi
 * @author Giusto - Mazuran
 */
public class NameUtil {

	/**
	 * Effettua l'encode del nome di un file, rimuovendo caratteri pericolosi
	 * @param s nome del file
	 * @return nome del file codificato
	 */
	public static String encode(String s) {
		try {
			s = URLEncoder.encode(s, "UTF-8").replaceAll("\\*", "%2A");
			if(s.equals(".")) {
				s = "%2E";
			} else if(s.equals("..")) {
				s = "%2E%2E";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * Controlla che la stringa passata sia un nome di classe sintatticamente
	 * valido, package incluso
	 * @param className nome da testare
	 * @return <code>true</code> se valido, <code>false</code> altrimenti
	 */
	public static boolean testClassName(String className) {
		return Pattern.matches(
				"^([a-zA-Z_$][a-zA-Z0-9_$]*\\.)*[a-zA-Z_$][a-zA-Z0-9_$]*$", className);
	}
	
}
