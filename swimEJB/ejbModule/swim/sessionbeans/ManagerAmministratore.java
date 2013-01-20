package swim.sessionbeans;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import swim.entitybeans.Abilita;
import swim.entitybeans.Amministratore;

@Stateless
@Remote(ManagerAmministratoreRemote.class)
public class ManagerAmministratore implements ManagerAmministratoreRemote{
	
	@PersistenceContext( unitName = "swim" )
	private EntityManager em;
	
	//Lista usata quando si devono selezionare degli amministartori da un gruppo.
	private List<Amministratore> amministratori;
	
	/**
	 * Il metodo verifica che email e password passate siano associate nel database.
	 * 
	 * @param email	Email con cui l'amministratore fa il login
	 * @param password	Password con cui l'amministratore fa il login
	 */
	public Amministratore verificaLogin(String email, String password) {
		Query q = em.createQuery("SELECT a FROM Amministratore a WHERE a.email = :email AND a.password = :password");
		q.setParameter("email", email);
		q.setParameter("password", password);
		amministratori = (List<Amministratore>) q.getResultList();
		if (amministratori.isEmpty()) {
			return null;
		} else {
			return amministratori.get(0);
		}
	}
	
	/**
	 * Il metodo permette di recuperare un amministratore a partire dalla sua email (identificativo).
	 * 
	 * @param email	Elemento discriminatore su cui compiere la ricerca
	 */
	public Amministratore cercaAdmin(String email){
		Query q = em.createQuery("SELECT a FROM Amministratore a WHERE a.email = :email");
		q.setParameter("email", email);
		amministratori = (List<Amministratore>) q.getResultList();
		if (amministratori.isEmpty()) {
			return null;
		} else {
			return amministratori.get(0);
		}
	}

	/**
	 * Il metodo permette di recuperare tutte le abilità con campo "conferma" uguale a 0 (e che quindi sono da considerarsi come richieste di abilità).
	 */
	public List<Abilita> getElencoRichieste(){
		Query q = em.createQuery("SELECT a FROM Abilita a WHERE a.conferma = 0");
		List<Abilita> richieste = (List<Abilita>) q.getResultList();
		return richieste;
	}
	
	//Portata anche qui per non dover usare il manager delle abilità nelle servlet di aiuto
	/**
	 * Vedi lo stesso metodo in ManagerAbilita
	 */
	public Abilita ricercaAbilita(String id) {
		Query q = em.createQuery("SELECT a FROM Abilita a WHERE a.id = :id");
		int idd = Integer.parseInt(id);
		q.setParameter("id", idd);
		List<Abilita> ab;
		ab = (List<Abilita>) q.getResultList();
		if (ab.isEmpty()) {
			return null;
		} else {
			return ab.get(0);
		}
	}
	
	/**
	 * Il metodo permette di cambiare il campo "conferma" da 0 in 1, oltre che modificare gli altri dati dell'abilità.
	 * 
	 * @param id	Identificativo dell'abilità da aggiungere (già presente nel database perché arriva come richiesta)
	 * @param nome	Nome dell'abilità
	 * @param descr	Descrizione dell'abilità
	 */
	public void aggiungiAbilita(String id, String nome, String desc, String email){
		Amministratore adm = cercaAdmin(email);
		Abilita ab = ricercaAbilita(id);
		ab.setNome(nome);
		ab.setDescrizione(desc);
		ab.switchConferma();
		ab.setAmministratore(adm);
		em.persist(ab);
	}
	
	/**
	 * Il metodo permette di eliminare l'abilità desiderata.
	 * 
	 * @param id	Identificativo dell'abilità da eliminare
	 */
	public void eliminaAbilita(String id){
		Abilita ab = ricercaAbilita(id);
		Query q = em.createQuery("DELETE FROM Abilita a WHERE a.id = :id");
		q.setParameter("id", ab.getId()).executeUpdate();
	}

}
