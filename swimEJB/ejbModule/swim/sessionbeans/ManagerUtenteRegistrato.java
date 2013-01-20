package swim.sessionbeans;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import swim.entitybeans.Abilita;
import swim.entitybeans.UtenteRegistrato;

@Stateless
@Remote(ManagerUtenteRegistratoRemote.class)
public class ManagerUtenteRegistrato implements ManagerUtenteRegistratoRemote {

	@PersistenceContext(unitName = "swim")
	private EntityManager em;

	//Lista usata quando si devono selezionare degli utenti da un gruppo.
	private List<UtenteRegistrato> utenti;

	/**
	 * Il metodo verifica che email e password passate siano associate nel database.
	 * 
	 * @param email	Email con cui l'utente fa il login
	 * @param password	Password con cui l'utente fa il login
	 */
	public UtenteRegistrato verificaLogin(String email, String password) {
		Query q = em.createQuery("SELECT u FROM UtenteRegistrato u WHERE u.email = :email AND u.password = :password");
		q.setParameter("email", email);
		q.setParameter("password", password);
		utenti = (List<UtenteRegistrato>) q.getResultList();
		if (utenti.isEmpty()) {
			return null;
		} else {
			return utenti.get(0);
		}
	}

	/**
	 * Il metodo permette di recuperare un utente a partire dalla sua email (identificativo).
	 * 
	 * @param email	Elemento discriminatore su cui compiere la ricerca
	 */
	public UtenteRegistrato ricercaUtente(String email) {
		Query q = em.createQuery("SELECT u FROM UtenteRegistrato u WHERE u.email = :email");
		q.setParameter("email", email);
		utenti = (List<UtenteRegistrato>) q.getResultList();
		if (utenti.isEmpty()) {
			return null;
		} else {
			return utenti.get(0);
		}
	}

	/**
	 * Il metodo controlla che l'email inserita in fase di registrazione abbia un formato valido.
	 * Ovviamente è possibile modificare gli elementi conseiderati "validi" quando e se cambierà il contesto.
	 * 
	 * @param email	Email da controllare
	 */
	public boolean controlloEmail(String email) {
		//Viene controllato che l'email contega i caratteri "@", ".it" e ".com"
		if (email.contains("@") && (email.contains(".it") || email.contains(".com"))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Il metodo controlla che l'email sia già presente nel database, restituendo vero in caso affermativo o falso altrimenti.
	 * 
	 * @param email	Email su cui eseguire il controllo
	 */
	public boolean esisteMail(String email) {
		Query q = em.createQuery("SELECT email FROM UtenteRegistrato");
		List<String> allEmail = (List<String>) q.getResultList();
		if (allEmail.contains(email)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Il metodo aggiunge un nuovo utente all'interno del database.
	 * 
	 * @param email	Email del nuovo utente
	 * @param password	Password del nuovo utente
	 * @param nome	Nome del nuovo utente
	 * @param cognome	Cognome del nuovo utente
	 */
	public void aggiungiUtente(String email, String password, String nome, String cognome) {
		UtenteRegistrato utente = new UtenteRegistrato();
		utente.setEmail(email);
		utente.setPsw(password);
		utente.setNome(nome);
		utente.setCognome(cognome);
		em.persist(utente);
	}

	/**
	 * Il metodo permette di cambiare la propria password.
	 * Viene prima fatto un controllo sulla vecchia password, poi viene eventualmente modificata se il controllo e positivo.
	 * Se il controllo risulta essere negativo viene lanciata un'eccezione.
	 * 
	 * @param email	Email dell'utente che vuole cambiare password
	 * @param newPsw	Nuova password da sostituire a quella vecchia
	 * @param oldPsw	vecchia password su cui fare il controllo prima della sostituzione
	 */
	public void cambioPsw(String email, String newPsw, String oldPsw)
			throws SwimBeanException {
		UtenteRegistrato ut = ricercaUtente(email);
		if (oldPsw.equals(ut.getPsw())) {
			ut.setPsw(newPsw);
		} else {
			throw new SwimBeanException("Inserisci la password corretta!");
		}
	}
	
	/**
	 * Il metodo controllo che l'utente possieda l'abilità cercata.
	 * Restituisce vero in caso positivo, falso altrimenti.
	 * 
	 * @param u	Utente su cui si vuole fare la ricerca
	 * @param a	Abilità che si cerca tra quelle possedute dall'utente 
	 */
	public boolean utentePossiedeAbilita(UtenteRegistrato u, Abilita a){
		Query q = em.createQuery("SELECT u.abilita FROM UtenteRegistrato u WHERE u = :utente");
		List<Abilita> elenco = (List<Abilita>) q.setParameter("utente", u).getResultList();
		//Il ciclo for fa passare tutte le abilità recuperate con la query precedente
		for(int i=0; i<elenco.size(); i++){
			//Se l'id di una delle abilità possedute dall'utente corrisponde con l'id dell'abilità passata come parametro restituisco vero e interrompo l'esecuzione 
			if(elenco.get(i).getId() == a.getId()){
				return true;
			}
		}
		//Terminato il ciclo for, se non ho mai restituito vero, allora ritorno falso.
		return false;
	}
	
	
	//Parte commentata perché riguardante l'immagine del profilo (possibile aggiungere in seguito)
	/*public void cambioImm(String email, String url) {
		UtenteRegistrato ut = ricercaUtente(email);
		if (url != null) {
			ut.setUrl(url);
		}
	}*/

}
