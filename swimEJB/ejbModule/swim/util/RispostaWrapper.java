package swim.util;

import java.io.File;
import java.io.Serializable;
/**
 * Classe wrapper per incapsulare risposte ai tre tipi di test
 * @author Giusto - Mazuran
 */
public class RispostaWrapper implements Serializable {
	
	private static final long serialVersionUID = -5058100266905806417L;
	private int numero;
	private String rispostaAperta;
	private Integer rispostaMultipla;
	private File rispostaJUnit;
	
	/**
	 * Crea una nuova risposta
	 * @param numero numero della riposta
	 */
	public RispostaWrapper(int numero) {
		this.numero = numero;
		rispostaAperta = null;
		rispostaMultipla = null;
		rispostaJUnit = null;
	}
	
	/**
	 * Crea una nuova risposta ad una domanda a risposta aperta
	 * @param numero numero della riposta
	 * @param rispostaAperta risposta alla domanda
	 */
	public RispostaWrapper(int numero, String rispostaAperta) {
		this(numero);
		this.rispostaAperta = rispostaAperta;
	}
	
	/**
	 * Crea una nuova risposta ad una domanda a risposta multipla
	 * @param numero numero della riposta
	 * @param rispostaMultipla risposta alla domanda
	 */
	public RispostaWrapper(int numero, Integer rispostaMultipla) {
		this(numero);
		this.rispostaMultipla = rispostaMultipla;
	}
	
	/**
	 * Crea una nuova risposta ad una domanda junit
	 * @param numero numero della riposta
	 * @param rispostaJUnit file jar di risposta
	 */
	public RispostaWrapper(int numero, File rispostaJUnit) {
		this(numero);
		this.rispostaJUnit = rispostaJUnit;
	}

	/**
	 * Restituisce il numero della risposta
	 * @return numero della risposta 
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * Restituisce la risposta ad una domanda a risposta aperta
	 * @return risposta aperta 
	 */
	public String getRispostaAperta() {
		return rispostaAperta;
	}

	/**
	 * Restituisce la risposta ad una domanda a risposta multipla
	 * @return risposta multipla 
	 */
	public Integer getRispostaMultipla() {
		return rispostaMultipla;
	}

	/**
	 * Restituisce il file jar di risposta ad una domanda junit
	 * @return risposta junit 
	 */
	public File getRispostaJUnit() {
		return rispostaJUnit;
	}

}
