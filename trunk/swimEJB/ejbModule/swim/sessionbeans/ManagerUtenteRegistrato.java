package swim.sessionbeans;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import swim.entitybeans.UtenteRegistrato;

@Stateless
@Remote(ManagerUtenteRegistratoRemote.class)
public class ManagerUtenteRegistrato implements ManagerUtenteRegistratoRemote {
	
	@PersistenceContext( unitName = "swim" )
	private EntityManager em;
	
	private UtenteRegistrato utente;
	
	public UtenteRegistrato verificaLogin(String email, String password){
		utente = em.find(UtenteRegistrato.class, email);
		if(utente.getPsw().equals(password)) {
			return utente;
		} else {
			return null;
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
