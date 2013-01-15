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

	//private UtenteRegistrato utente;
	private List<UtenteRegistrato> utenti;

	// CAMBIATO u.U_ID in u.email
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

	// Lasciamo solo il controllo su @ o anche gli altri due con limitazione
	// sugli indirizzi?
	public boolean controlloEmail(String email) {
		if (email.contains("@") && (email.contains(".it") || email.contains(".com"))) {
			return true;
		} else {
			return false;
		}
	}

	public boolean esisteMail(String email) {
		Query q = em.createQuery("SELECT email FROM UtenteRegistrato");
		List<String> allEmail = (List<String>) q.getResultList();
		if (allEmail.contains(email)) {
			return true;
		} else {
			return false;
		}
	}

	public void aggiungiUtente(String email, String password, String nome,String cognome) {
		UtenteRegistrato utente = new UtenteRegistrato();
		utente.setEmail(email);
		utente.setPsw(password);
		utente.setNome(nome);
		utente.setCognome(cognome);
		em.persist(utente);
	}

	public void cambioPsw(String email, String newPsw, String oldPsw)
			throws SwimBeanException {
		UtenteRegistrato ut = ricercaUtente(email);
		if (oldPsw.equals(ut.getPsw())) {
			ut.setPsw(newPsw);
		} else {
			throw new SwimBeanException("Inserisci la password corretta!");
		}
	}

	// Non ho bisogno di condizioni. Se non esiste l'URL non si mette nulla
	public void cambioImm(String email, String url) {
		UtenteRegistrato ut = ricercaUtente(email);
		if (url != null) {
			ut.setUrl(url);
		}
	}
	
	public boolean utentePossiedeAbilita(UtenteRegistrato u, Abilita a){
		Query q = em.createQuery("SELECT u.abilita FROM UtenteRegistrato u WHERE u = :utente");
		List<Abilita> elenco = (List<Abilita>) q.setParameter("utente", u).getResultList();
		for(int i=0; i<elenco.size(); i++){
			if(elenco.get(i).getId() == a.getId()){
				return true;
			}
		}
		return false;
//		System.out.println(u.getAbilita());
//		if(u.getAbilita().contains(a)){
//			return true;
//		} else {
//			System.out.println(a);
//			return false;
//		}
	}

	// Non penso vada bene...
	/*public void logout() {
		utente = null;
	}*/

}
