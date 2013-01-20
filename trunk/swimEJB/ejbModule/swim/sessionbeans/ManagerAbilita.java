package swim.sessionbeans;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import swim.entitybeans.Abilita;
import swim.entitybeans.UtenteRegistrato;

@Stateless
@Remote(ManagerAbilitaRemote.class)
public class ManagerAbilita implements ManagerAbilitaRemote{
	
	@PersistenceContext( unitName = "swim" )
	private EntityManager em;
	
	private void creaNuovaAbilitaDirect(String nome, String descr){
		Abilita abilita = new Abilita();
		abilita.setNome(nome);
		abilita.setDescrizione(descr);
		em.persist(abilita);
	}
	
	/**
	 * Il metodo seleziona tutte le abilità confermate dagli amministratori e le restituisce in una lista.
	 */
	public List<Abilita> getElencoAbilita(){
		Query q = em.createQuery("SELECT a FROM Abilita a WHERE a.conferma = 1");
		List<Abilita> allAbilita = (List<Abilita>) q.getResultList();
		return allAbilita;
	}
	
	/**
	 * Il metodo seleziona le abilità possedute dall'utente e le restituisce in una lista.
	 * Nel caso l'utente non abbia nessuna abilità associata viene lanciata un'eccezione.
	 * 
	 * @param utente	Utente di cui si vogliono selezionare le abilità
	 */
	public List<Abilita> getAbilitaUtente(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Abilita a WHERE :utente MEMBER OF a.utente");
		q.setParameter("utente", utente);
		List<Abilita> abilitaUtente = (List<Abilita>) q.getResultList();
		if(abilitaUtente.isEmpty()){
			throw new SwimBeanException("Questo utente non possiede alcuna abilita'");
		} else {
			return abilitaUtente;
		}
	}
	
	/**
	 * Il metodo recupera tutte le abilità che l'utente non possiede e le restituisce in una lista.
	 * 
	 * @param utente	Utente di cui si vogliono recuperare le abilità non possedute
	 */
	public List<Abilita> getElencoAbilitaNonMie(UtenteRegistrato utente){
		Query q = em.createQuery("SELECT a FROM Abilita a WHERE NOT (:utente MEMBER OF a.utente) AND a.conferma = 1");
		q.setParameter("utente", utente);
		List<Abilita> abilitaNonUtente = (List<Abilita>) q.getResultList();
		return abilitaNonUtente;
	}
	
	/**
	 * Il metodo aggiunge una nuova abilità all'utente.
	 * 
	 * @param u	Utente a cui si vuole aggiungere la nuova abilità
	 * @param a	Abilità da aggiungere all'utente
	 */
	public void aggiungiAbilita(UtenteRegistrato u, Abilita a){
		Query q = em.createNativeQuery("INSERT INTO `swim`.`ABILITA_UTENTE` (`UtenteRegistrato_ID`, `Abilita_ID`) VALUES (:email, :id);");
		q.setParameter("email", u.getEmail());
		q.setParameter("id", a.getId());
		q.executeUpdate();
	}
	
	/**
	 * Il metodo permette di creare una nuova abilità all'interno del database con il campo "conferma" inizializzato a 0.
	 * 
	 * @param nome	Nome proposto per l'abilità
	 * @param descr	Descrizione proposta per l'abilità
	 */
	public void invioRichiestaNuovaAbilita(String nome, String descr){
		creaNuovaAbilitaDirect(nome, descr);
	}
	
	/**
	 * Il metodo permette di recuperare un'abilità facendo la ricerca sull'id di questa.
	 * 
	 * @param id	Elemento discriminatore su cui si vuole compiere la selezione
	 */
	public Abilita ricercaAbilita(String id) {
		Query q = em.createQuery("SELECT a FROM Abilita a WHERE a.id = :id");
		//L'id è passato come String perché altrimenti vengono restituiti problemi di serializzabilità
		//Con questa istruzione la stringa viene tradotta in un int
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

}
