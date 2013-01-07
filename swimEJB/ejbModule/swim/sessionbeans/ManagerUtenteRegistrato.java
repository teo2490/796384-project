package swim.sessionbeans;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import swim.entitybeans.UtenteRegistrato;

@Stateless
@Remote(ManagerUtenteRegistratoRemote.class)
public class ManagerUtenteRegistrato implements ManagerUtenteRegistratoRemote {
	
	@PersistenceContext( unitName = "swim" )
	private EntityManager em;
	
	private UtenteRegistrato utente;
	private List<UtenteRegistrato> utenti;
	
	//CAMBIATO u.U_ID in u.email
	public UtenteRegistrato verificaLogin(String email, String password){
		Query q = em.createQuery("SELECT u FROM UtenteRegistrato u WHERE u.email = :email AND u.password = :password");
		q.setParameter("email", email);
		q.setParameter("password", password);
		utenti = (List<UtenteRegistrato>) q.getResultList();
		if(utenti.isEmpty()){
			return null;
		} else {
			return utenti.get(0);
		}
	}
	
	//Lasciamo solo il controllo su @ o anche gli altri due con limitazione sugli indirizzi?
	public boolean controlloEmail(String email){
		if(email.contains("@") && (email.contains(".it") || email.contains(".com"))){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean esisteMail(String email){
		Query q = em.createQuery("SELECT email FROM UtenteRegistrato");
		List<String> allEmail = (List<String>)q.getResultList();
		if(allEmail.contains(email)){
			return true;
		} else {
			return false;
		}
	}
	
	public void aggiungiUtente(String email, String password, String nome, String cognome){
		utente = new UtenteRegistrato();
		utente.setEmail(email);
		utente.setPsw(password);
		utente.setNome(nome);
		utente.setCognome(cognome);
	}
	
	public void cambioPsw(String newPsw, String oldPsw) throws SwimBeanException{
		if(oldPsw.equals(utente.getPsw())){
			utente.setPsw(newPsw);
		} else {
			throw new SwimBeanException("Inserisci la password corretta!");
		}
	}
	
	//Non ho bisogno di condizioni. Se non esiste l'URL non si mette nulla
	public void cambioImm(String url){
		utente.setUrl(url);
	}
	
	//Non penso vada bene...
	public void logout() {
		utente = null;
	}

}
