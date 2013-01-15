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
import java.util.Set;

@Stateless
@Remote(ManagerAiutoRemote.class)
public class ManagerAiuto implements ManagerAiutoRemote{
	
	@PersistenceContext( unitName = "swim" )
	private EntityManager em;
	
	private Aiuto aiuto;
	
	private void creaAiutoDirect(String abilita, UtenteRegistrato richiedente, UtenteRegistrato richiesto){
		aiuto = new Aiuto();
		aiuto.setTipo(abilita); //Cosa intendiamo con tipo??
		aiuto.setUtFornisce(richiesto);
		aiuto.setUtRiceve(richiedente);
		em.persist(aiuto);
	}
	
	// NON SO SE FUNZIONA!!
	public List<String> ricercaTraAmici(Abilita abilita, UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT u FROM UtenteRegistrato u, Amicizia a WHERE u = a.utRichiedente AND :abilita MEMBER OF u.abilita AND :utente = a.utRichiesto");
		Query q1 = em.createQuery("SELECT u FROM UtenteRegistrato u, Amicizia a WHERE u = a.utRichiesto AND :abilita MEMBER OF u.abilita AND :utente = a.utRichiedente");
		q.setParameter("abilita", abilita);
		q.setParameter("utente", utente);
		q1.setParameter("abilita", abilita);
		q1.setParameter("utente", utente);
		List<String> idUtenti = new ArrayList<String>();
		List<UtenteRegistrato> utenti = (List<UtenteRegistrato>) q.getResultList();
		List<UtenteRegistrato> daSvuotare = (List<UtenteRegistrato>) q1.getResultList();
		for(int i=0; i<daSvuotare.size(); i++){
			utenti.add(daSvuotare.get(i));
		}
		if(utenti.size() == 0){
			throw new SwimBeanException("Non hai amici con questa abilita'");
		} else {
			for(int i=0; i<utenti.size(); i++){
				idUtenti.add(utenti.get(i).getEmail()); 
			}
			return idUtenti;
		}
	}
	
	
	//Funziona?? Oppure devo ritornare utente per utente??
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
	
	//Può essere finito così??
	public void invioRichiestaAiuto(String tipo, UtenteRegistrato richiedente, UtenteRegistrato richiesto){
		creaAiutoDirect(tipo, richiedente, richiesto);
	}
	
	public void confermaRichiesta(String id){
		Aiuto a = ricercaAiuto(id);
		a.switchConferma();
	}
	
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
	
	public void eliminaAiuto(Aiuto at){
		Query q = em.createQuery("DELETE FROM Aiuto a WHERE a.id = :id");
		q.setParameter("id", at.getId()).executeUpdate();
	}
	
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
	
	/*
	public List<Aiuto> getElencoRichiesteAiutoFatteNonConfermate(UtenteRegistrato utente) throws SwimBeanException{
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
	
	public List<Aiuto> getElencoRichiesteAiutoFatteConfermateSenzaFeedback(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Aiuto a WHERE a.conferma = true AND a.feedback = NULL AND a.utRiceve = :utente");
		List<Aiuto> richieste = (List<Aiuto>) q.setParameter("utente", utente).getResultList();
		if(richieste.isEmpty()){
			throw new SwimBeanException("Non ci sono richieste di aiuto per te!");
		} else {
			return richieste;
		}
	}
	
	public List<Aiuto> getElencoRichiesteAiutoRicevuteConfermateConFeedback(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Aiuto a WHERE a.conferma = true AND NOT (a.feedback = NULL) AND a.utFornisce = :utente");
		List<Aiuto> richieste = (List<Aiuto>) q.setParameter("utente", utente).getResultList();
		if(richieste.isEmpty()){
			throw new SwimBeanException("Non ci sono feedback!");
		} else {
			return richieste;
		}
	}
	
	
	public List<Aiuto> getElencoRichiesteAiutoRicevuteNonConfermate(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Aiuto a WHERE a.conferma = false AND a.utFornisce = :utente");
		List<Aiuto> richieste = (List<Aiuto>) q.setParameter("utente", utente).getResultList();
		if(richieste.isEmpty()){
			throw new SwimBeanException("Non ci sono richieste di aiuto per te!");
		} else {
			return richieste;
		}
	}
	
	public List<Aiuto> getElencoRichiesteAiutoRicevuteConfermate(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Aiuto a WHERE a.conferma = true AND a.utFornisce = :utente");
		List<Aiuto> richieste = (List<Aiuto>) q.setParameter("utente", utente).getResultList();
		if(richieste.isEmpty()){
			throw new SwimBeanException("Non ci sono richieste di aiuto per te!");
		} else {
			return richieste;
		}
	}
	
	public Abilita ricercaAbilita(String id) {
		Query q = em.createQuery("SELECT a FROM Abilita a WHERE a.id = :id");
		//System.out.println(id);
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
	
	public Aiuto ricercaAiuto(String id) {
		Query q = em.createQuery("SELECT a FROM Aiuto a WHERE a.id = :id");
		//System.out.println(id);
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

}
