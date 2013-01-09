package swim.test;

import javax.ejb.Remote;

@Remote
public interface ManagerInizializzazioneDatabaseRemote {
	
	public static final int NUMERO_UTENTI_PREDEFINITI = 5;
	public static final String[] PASSWORD_UTENTI = {"mrp", "avp", "agp", "rpp", "msp"};
	public static final String[] EMAIL_UTENTI = {"mr@email.it", "av@email.it", "ag@email.com", "rp@email.it", "ms@email.it"};
	public static final String[] NOME_UTENTI = {"Mario", "Anna", "Andrea", "Riccardo", "Matteo"};
	public static final String[] COGNOME_UTENTI = {"Rossi", "Verdi", "Gialli", "Pinciroli", "Simoni"};
	
	/*public static final int NUMERO_ABILITA_PREDEFINITE = 4;
	public static final int[] ID_ABILITA = {1, 2, 3, 4};
	public static final String[] NOME_ABILITA = {"Cucinare", "Verniciare", "Java", "C/C++"};
	public static final String[] DESCR_ABILITA = {"Sa cucinare!", "Sa verniciare!", "Sa programare in java", "Sa programmare in C/C++"};*/
	
	//public void pulisci();
	public void creaUtentiPredefiniti();

}
