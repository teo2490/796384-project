package swim.sessionbeans;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import swim.entitybeans.Abilita;
import swim.entitybeans.UtenteRegistrato;
import swim.entitybeans.Aiuto;

import java.util.ArrayList;
import java.util.List;

@Stateless
@Remote(ManagerAiutoRemote.class)
public class ManagerAiuto implements ManagerAiutoRemote{
	
	@PersistenceContext( unitName = "swim" )
	private EntityManager em;
	
	private void creaAiutoDirect(String abilita, UtenteRegistrato richiedente, UtenteRegistrato richiesto){
		Aiuto aiuto = new Aiuto();
		aiuto.setTipo(abilita);
		aiuto.setUtFornisce(richiesto);
		aiuto.setUtRiceve(richiedente);
		em.persist(aiuto);
	}
	
	/**
	 * Il metodo cerca tra gli amici di chi fa la richiesta se c'è qualcuno con l'abilità cercata.
	 * Le email delgi amici sono restituiti in una lista. Qualora la lista fosse vuota viene lanciata un'eccezione.
	 * 
	 * @param abilita	Abilità che si cerca negli amici
	 * @param utente	Utente che effettua la ricerca
	 */
	public List<String> ricercaTraAmici(Abilita abilita, UtenteRegistrato utente) throws SwimBeanException{
		//Query sulle amicizie di cui utente è richiedente
		Query q = em.createQuery("SELECT u FROM UtenteRegistrato u, Amicizia a WHERE u = a.utRichiedente AND :abilita MEMBER OF u.abilita AND :utente = a.utRichiesto");
		//Query sulle amcizie di cui utente è richiesto
		Query q1 = em.createQuery("SELECT u FROM UtenteRegistrato u, Amicizia a WHERE u = a.utRichiesto AND :abilita MEMBER OF u.abilita AND :utente = a.utRichiedente");
		q.setParameter("abilita", abilita);
		q.setParameter("utente", utente);
		q1.setParameter("abilita", abilita);
		q1.setParameter("utente", utente);
		List<String> idUtenti = new ArrayList<String>();
		List<UtenteRegistrato> utenti = (List<UtenteRegistrato>) q.getResultList();
		List<UtenteRegistrato> daSvuotare = (List<UtenteRegistrato>) q1.getResultList();
		for(int i=0; i<daSvuotare.size(); i++){
			//Metto tutto in una sola lista
			utenti.add(daSvuotare.get(i));
		}
		if(utenti.isEmpty()){
			throw new SwimBeanException("Non hai amici con questa abilita'");
		} else {
			for(int i=0; i<utenti.size(); i++){
				idUtenti.add(utenti.get(i).getEmail()); 
			}
			return idUtenti;
		}
	}
	
	/**
	 * Il metodo cerca far tutti gli utenti nel database quelli che dispongono dell'abilità desiderata.
	 * Viene restituita la lista delle email degli utenti, ma se la lista è vuota viene lanciata un'eccezione.
	 * 
	 * @param abilita	Elemento discriminatore per la ricerca.
	 */
	public List<String> ricercaPerAbilita(Abilita abilita) throws SwimBeanException{
		Query q = em.createQuery("SELECT u FROM UtenteRegistrato u WHERE :abilita MEMBER OF u.abilita");
		q.setParameter("abilita", abilita);
		List<String> idUtenti = new ArrayList<String>();
		List<UtenteRegistrato> utenti = (List<UtenteRegistrato>) q.getResultList();
		if(utenti.isEmpty()){
			throw new SwimBeanException("Nessun utente ha questa abilita'");
		} else {
			for(int i=0; i<utenti.size(); i++){
				idUtenti.add(utenti.get(i).getEmail()); 
			}
			return idUtenti;
		}
	}
	
	/**
	 * Il metodo permette di creare una nuova istanza all'interno del database con campo "conferma" a 0.
	 * 
	 * @param tipo	Tipo di aiuto che si desidera ricevere (da riempire con il nome di un'abilità)
	 * @param richiedente	Utente che richiede l'aiuto
	 * @param richiesti	Utente a cui viene chiesto l'aiuto
	 */
	public void invioRichiestaAiuto(String tipo, UtenteRegistrato richiedente, UtenteRegistrato richiesto){
		creaAiutoDirect(tipo, richiedente, richiesto);
	}
	
	/**
	 * Il metodo permette di confermare l'aiuto cambiando il campo "conferma" da 0 a 1.
	 * 
	 * @param id	Identificativo dell'aiuto da confermare
	 */
	public void confermaRichiesta(String id){
		Aiuto a = ricercaAiuto(id);
		a.switchConferma();
	}
	
	/**
	 * Il metodo restituisce il feedback che è stato lasciato su un aiuto.
	 * Se non ci sono feedback o il feedback lasciato non contiene alcun carattere viene lanciata un'eccezione.
	 * Se è presente almeno un carattere viene restituito il feedback.
	 * 
	 * @param aiuto	Aiuto di cui si vuole recuperare il feedback
	 */
	public String controlloFeedback(Aiuto aiuto) throws SwimBeanException{
		String feedback = aiuto.getFeedback();
		if(feedback == null){
			throw new SwimBeanException("Nessun feedback disponibile per questo aiuto!");
		} else if (feedback.equals("")){
			throw new SwimBeanException("Nessun feedback disponibile per questo aiuto!");
		}else{
			return feedback;
		}
	}
	
	/**
	 * Il metodo permette di aggiungere un feedback ad un aiuto. Viene instanziato un nuovo aiuto uguale a quello precedente in ogni campo (tranne che nell'id) e ora anche con il feedback.
	 * Il vecchio aiuto viene cancellato e poi il nuovo aiuto è aggiunto.
	 * 
	 * @param at	Aiuto a cui aggiungere il feedback
	 * @param feedback	Feedback da aggiungere
	 */
	public void aggiungiFeedback(Aiuto at, String feedback){
		Aiuto a = new Aiuto();
		a.setTipo(at.getTipo());
		a.setUtFornisce(at.getRichiesto());
		a.setUtRiceve(at.getRichiedente());
		a.switchConferma();
		a.setFeedback(feedback);
		eliminaAiuto(at);
		em.persist(a);
	}
	
	/**
	 * Il metodo permette di eliminare un aiuto.
	 * 
	 * @param at	Aiuto da eliminare
	 */
	public void eliminaAiuto(Aiuto at){
		Query q = em.createQuery("DELETE FROM Aiuto a WHERE a.id = :id");
		q.setParameter("id", at.getId()).executeUpdate();
	}
	
	/**
	 * Il metodo permette di ottenere tutti i feedback che sono stati dati agli aiuti forniti dall'utente.
	 * 
	 * @param u	Utente di cui si vogliono recuperare i feedback ottenuti
	 */
	public List<String> getElencoFeedbackUtente(UtenteRegistrato u){
		Query q = em.createQuery("SELECT a.feedback FROM Aiuto a WHERE :utente = a.utFornisce AND NOT (a.feedback=null)");
		q.setParameter("utente", u);
		List<String> elencoFeedback = (List<String>) q.getResultList();
		if(elencoFeedback.isEmpty()){
			return null;
		} else {
			return elencoFeedback;
		}
	}
	
	/**
	 * Il metodo recupera le richieste di aiuto che sono state inviate dall'utente e che sono state confermate dal richiesto, ma a cui non è stato ancora dato un feedback.
	 * Se la lista che deve essere restituita è vuota viene lanciata un'eccezione.
	 * 
	 * @param utente	Utente di cui si vogliono recuperare le richieste inviate e confermate, ma senza feedback
	 */
	public List<Aiuto> getElencoRichiesteAiutoFatteConfermateSenzaFeedback(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Aiuto a WHERE a.conferma = true AND a.feedback = NULL AND a.utRiceve = :utente");
		List<Aiuto> richieste = (List<Aiuto>) q.setParameter("utente", utente).getResultList();
		if(richieste.isEmpty()){
			throw new SwimBeanException("Non ci sono richieste di aiuto per te!");
		} else {
			return richieste;
		}
	}
	
	/**
	 * Il metodo recupera le richieste di aiuto che sono state ricevute dall'utente, che sono state confermate e a cui è stato dato un feedback.
	 * Se la lista che deve essere restituita è vuota viene lanciata un'eccezione.
	 * 
	 * @param utente	Utente di cui si vogliono recuperare le richieste
	 */
	public List<Aiuto> getElencoRichiesteAiutoRicevuteConfermateConFeedback(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Aiuto a WHERE a.conferma = true AND NOT (a.feedback = NULL) AND a.utFornisce = :utente");
		List<Aiuto> richieste = (List<Aiuto>) q.setParameter("utente", utente).getResultList();
		if(richieste.isEmpty()){
			throw new SwimBeanException("Non ci sono feedback!");
		} else {
			return richieste;
		}
	}
	
	/**
	 * Il metodo recupera le richieste di aiuto che sono state ricevute dall'utente, ma che non sono state ancora confermate.
	 * Se la lista che deve essere restituita è vuota viene lanciata un'eccezione.
	 * 
	 * @param utente	Utente di cui si vogliono recuperare le richieste
	 */
	public List<Aiuto> getElencoRichiesteAiutoRicevuteNonConfermate(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Aiuto a WHERE a.conferma = false AND a.utFornisce = :utente");
		List<Aiuto> richieste = (List<Aiuto>) q.setParameter("utente", utente).getResultList();
		if(richieste.isEmpty()){
			throw new SwimBeanException("Non ci sono richieste di aiuto per te!");
		} else {
			return richieste;
		}
	}
	
	/**
	 * Il metodo recupera le richieste di aiuto che sono state ricevute dall'utente e che sono state confermate.
	 * Se la lista che deve essere restituita è vuota viene lanciata un'eccezione.
	 * 
	 * @param utente	Utente di cui si vogliono recuperare le richieste
	 */
	public List<Aiuto> getElencoRichiesteAiutoRicevuteConfermate(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Aiuto a WHERE a.conferma = true AND a.utFornisce = :utente");
		List<Aiuto> richieste = (List<Aiuto>) q.setParameter("utente", utente).getResultList();
		if(richieste.isEmpty()){
			throw new SwimBeanException("Non ci sono richieste di aiuto per te!");
		} else {
			return richieste;
		}
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
	 * Il metodo permette di recuperare un aiuto facendo la ricerca sull'id di questo.
	 * 
	 * @param id	Elemento discriminatore su cui si vuole compiere la selezione
	 */
	public Aiuto ricercaAiuto(String id) {
		Query q = em.createQuery("SELECT a FROM Aiuto a WHERE a.id = :id");
		//L'id è passato come String perché altrimenti vengono restituiti problemi di serializzabilità
		//Con questa istruzione la stringa viene tradotta in un int
		int idd = Integer.parseInt(id);
		q.setParameter("id", idd);
		List<Aiuto> ab;
		ab = (List<Aiuto>) q.getResultList();
		if (ab.isEmpty()) {
			return null;
		} else {
			return ab.get(0);
		}
	}
	
	
	//Possibile implementarle in seguito qualora dovessero servire
	/*public List<Aiuto> getElencoRichiesteAiutoFatteNonConfermate(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Aiuto a WHERE a.conferma = false AND a.utRiceve = :utente");
		List<Aiuto> richieste = (List<Aiuto>) q.setParameter("utente", utente).getResultList();
		if(richieste.isEmpty()){
			throw new SwimBeanException("Non ci sono richieste di aiuto per te!");
		} else {
			return richieste;
		}
	}
	
	public List<Aiuto> getElencoRichiesteAiutoFatteConfermate(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Aiuto a WHERE a.conferma = true AND a.utRiceve = :utente");
		List<Aiuto> richieste = (List<Aiuto>) q.setParameter("utente", utente).getResultList();
		if(richieste.isEmpty()){
			throw new SwimBeanException("Non ci sono richieste di aiuto per te!");
		} else {
			return richieste;
		}
	}*/

}
