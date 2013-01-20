package swim.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import swim.entitybeans.UtenteRegistrato;
import swim.entitybeans.Amicizia;

@Stateless
@Remote(ManagerAmiciziaRemote.class)
public class ManagerAmicizia implements ManagerAmiciziaRemote{
	
	@PersistenceContext( unitName = "swim" )
	private EntityManager em;
	
	private void creaAmiciziaDirect(UtenteRegistrato richiedente, UtenteRegistrato richiesto){
		Amicizia amicizia = new Amicizia();
		amicizia.setRichiedente(richiedente);
		amicizia.setRichiesto(richiesto);
		em.persist(amicizia);
	}
	
	/**
	 * Aggiunge una nuova istanza nel database con campo "conferma" uguale a 0.
	 * 
	 * @param richiedente	Utente che invia la richiesta
	 * @param richiesto	Utente che riceve la richiesta
	 */
	public void invioRichiestaAmicizia(UtenteRegistrato richiedente, UtenteRegistrato richiesto){
		creaAmiciziaDirect(richiedente, richiesto);
	}
	
	/**
	 * Il metodo recupera tutte le amicizie (già confermate) dell'utente.
	 * Se l'utente non ha amici viene lanciata un'eccezione, altrimenti viene restituita la lista con gli amici.
	 * 
	 * @param utente	Utente di cui si volgiono recuperare le amicizie
	 */
	public List<UtenteRegistrato> getElencoAmici(UtenteRegistrato utente) throws SwimBeanException{
		//Query per recuperare le amicizie di cui utente è richiedente
		Query q = em.createQuery("SELECT a FROM Amicizia a WHERE a.conferma = true AND a.utRichiedente = :utente");
		//Questa lista verrà svuotata in favore della lista amici
		List<Amicizia> amicRichiedente = (List<Amicizia>) q.setParameter("utente", utente).getResultList();
		//Query per recuperare le amicizie di cui utente è richiesto
		q = em.createQuery("SELECT a FROM Amicizia a WHERE a.conferma = true AND a.utRichiesto = :utente");
		//Questa lista verrà svuotata in favore della lista amici
		List<Amicizia> amicRichiesto = (List<Amicizia>) q.setParameter("utente", utente).getResultList();
		//Lista che verrà restituita
		List<UtenteRegistrato> amici = new ArrayList<UtenteRegistrato>();
		for(int i=0; i<amicRichiedente.size(); i++){
			amici.add(amicRichiedente.get(i).getRichiesto());
		}
		for(int i=0; i<amicRichiesto.size(); i++){
			amici.add(amicRichiesto.get(i).getRichiedente());
		}
		if(amici.size() == 0){
			throw new SwimBeanException("Non hai amici!");
		} else {
			return amici;
		}
	}
	
	/**
	 * Il metodo recupera le richieste di amicizia ricevute dall'utente ma che non sono ancora state confermate.
	 * Se la lista che deve essere restituita è vuota viene lanciata un'eccezione
	 * 
	 * @param utente	Utente di cui si vogliono recuperare le richieste di amicizia
	 */
	public List<Amicizia> getElencoRichiesteAmiciziaRicevute(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Amicizia a WHERE (a.conferma = false AND a.utRichiesto = :utente))");
		List<Amicizia> richieste = (List<Amicizia>) q.setParameter("utente", utente).getResultList();
		if(richieste.size() == 0){
			throw new SwimBeanException("Non ci sono richieste di amicizia per te!");
		} else {
			return richieste;
		}
	}
	
	/**
	 * Il metodo recupera le richieste di amicizia inviate dall'utente ma che non sono ancora state confermate.
	 * Se la lista che deve essere restituita è vuota viene lanciata un'eccezione
	 * 
	 * @param utente	Utente di cui si vogliono recuperare le richieste di amicizia
	 */
	public List<Amicizia> getElencoRichiesteAmiciziaInviate(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Amicizia a WHERE (a.conferma = false AND a.utRichiedente = :utente))");
		List<Amicizia> richieste = (List<Amicizia>) q.setParameter("utente", utente).getResultList();
		if(richieste.size() == 0){
			throw new SwimBeanException("Non ci sono amicizie inviate da te!");
		} else {
			return richieste;
		}
	}
	
	/**
	 * Il metodo permette di portare il campo "conferma" a 1.
	 * 
	 * @param id	Identificativo dell'amicizia
	 */
	public void accettaAmicizia(String id){
		Amicizia a = ricercaAmicizia(id);
		a.switchConferma();
	}
	
	/**
	 * Il metodo controlla se esiste già un'amicizia tra i due utenti passati.
	 * 
	 * @param u1	Primo utente
	 * @param u2	secondo utente
	 */
	public boolean esisteAmicizia(UtenteRegistrato u1, UtenteRegistrato u2){
		//La query controlla sia le amicizie con richiedente=u1 e richiesto=u2, sia le amicizie con richiedente=u2 e richiesto=u1
		Query q = em.createQuery("SELECT a FROM Amicizia a WHERE ((a.utRichiedente = :u1 AND a.utRichiesto = :u2) OR (a.utRichiedente = :u2 AND a.utRichiesto = :u1))");
		q.setParameter("u1", u1);
		q.setParameter("u2", u2);
		List<Amicizia> amicizia = (List<Amicizia>) q.getResultList();
		if(amicizia.isEmpty()){
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Il metodo permette di recuperare un'amicizia facendo la ricerca sull'id di questa.
	 * 
	 * @param id	Elemento discriminatore su cui si vuole compiere la selezione
	 */
	public Amicizia ricercaAmicizia(String id) {
		Query q = em.createQuery("SELECT a FROM Amicizia a WHERE a.id = :id");
		//L'id è passato come String perché altrimenti vengono restituiti problemi di serializzabilità
		//Con questa istruzione la stringa viene tradotta in un int
		int idd = Integer.parseInt(id);
		q.setParameter("id", idd);
		List<Amicizia> ab;
		ab = (List<Amicizia>) q.getResultList();
		if (ab.isEmpty()) {
			return null;
		} else {
			return ab.get(0);
		}
	}

}
