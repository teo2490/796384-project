package swim.test;

import javax.ejb.Remote;

import swim.entitybeans.Amministratore;
import swim.entitybeans.UtenteRegistrato;

@Remote
public interface ManagerInizializzazioneDatabaseRemote {
	
	public static final int NUMERO_UTENTI_PREDEFINITI = 3;
	public static final String[] PASSWORD_UTENTI = {"rp", "ms", "mr"};
	public static final String[] EMAIL_UTENTI = {"rp@mail.it", "ms@mail.it", "mr@mail.it"};
	public static final String[] NOME_UTENTI = {"Riccardo", "Matteo", "Mario"};
	public static final String[] COGNOME_UTENTI = {"Pinciroli", "Simoni", "Rossi"};
	
	public static final int NUMERO_ABILITA_PREDEFINITE = 4;
	public static final String[] NOME_ABILITA = {"Cucinare", "Verniciare", "Java", "C"};
	public static final String[] DESCR_ABILITA = {"Prepara ottimi piatti", "Vernicia ogni parete", "Sa programare in java", "Sa programmare in C"};
	
	public static final int NUMERO_ADMIN_PREDEFINITI = 2;
	public static final String[] EMAIL_ADMIN = {"admin1@swim.it", "admin2@swim.it"};
	public static final String[] PASSWORD_ADMIN = {"admin1", "admin2"};
	
	public static final int NUMERO_AIUTI_PREDEFINITI = 4;
	
	public void pulisciUtenteRegistrato();
	public void pulisciAmministratore();
	public void pulisciAbilita();
	public void pulisciAiuto();
	public void pulisciABILITA_UTENTE();
	public void creaUtentiPredefiniti();
	public void creaAbilitaPredefinite();
	public void creaAdminPredefiniti();
	public void creaAiutiPredefiniti(UtenteRegistrato fornitore, UtenteRegistrato ricevente);

}
