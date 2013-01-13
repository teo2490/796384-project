package swim.sessionbeans;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import swim.entitybeans.Abilita;
import swim.entitybeans.UtenteRegistrato;
import swim.entitybeans.Aiuto;

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
	}
	
	// NON SO SE FUNZIONA!!
	public List<UtenteRegistrato> ricercaTraAmici(Abilita abilita, UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT u FROM UtenteRegistrato u, Amicizia a WHERE u = a.utRichiedente AND :abilita MEMBER OF u.abilita AND :utente = a.utRichiesto");
		Query q1 = em.createQuery("SELECT u FROM UtenteRegistrato u, Amicizia a WHERE u = a.utRichiesto AND :abilita MEMBER OF u.abilita AND :utente = a.utRichiedente");
		q.setParameter("abilita", abilita);
		q.setParameter("utente", utente);
		q1.setParameter("abilita", abilita);
		q1.setParameter("utente", utente);
		List<UtenteRegistrato> utenti = (List<UtenteRegistrato>) q.getResultList();
		List<UtenteRegistrato> daSvuotare = (List<UtenteRegistrato>) q1.getResultList();
		for(int i=0; i<daSvuotare.size(); i++){
			utenti.add(daSvuotare.get(i));
		}
		if(utenti.size() == 0){
			throw new SwimBeanException("Non hai amici con questa abilita'");
		} else {
			return utenti;
		}
	}
	
	
	//Funziona?? Oppure devo ritornare utente per utente??
	public List<UtenteRegistrato> ricercaPerAbilita(Abilita abilita) throws SwimBeanException{
		Query q = em.createQuery("SELECT u FROM UtenteRegistrato u WHERE :abilita MEMBER OF u.abilita");
		q.setParameter("abilita", abilita);
		List<UtenteRegistrato> utenti = (List<UtenteRegistrato>) q.getResultList();
		if(utenti.size() == 0){
			throw new SwimBeanException("Nessun utente ha questa abilita'");
		} else {
			return utenti;
		}
	}
	
	//Può essere finito così??
	public void invioRichiestaAiuto(String tipo, UtenteRegistrato richiedente, UtenteRegistrato richiesto){
		creaAiutoDirect(tipo, richiedente, richiesto);
	}
	
	public void confermaRichiesta(){
		aiuto.switchConferma();
	}
	
	public String controlloFeedback(Aiuto aiuto) throws SwimBeanException{
		String feedback = aiuto.getFeedback();
		if(feedback.equals("") || feedback == null){
			throw new SwimBeanException("Nessun feedback disponibile per questo aiuto!");
		} else {
			return feedback;
		}
	}
	
	public void aggiungiFeedback(String feedback){
		aiuto.setFeedback(feedback);
	}
	
	public List<String> getElencoFeedbackUtente(UtenteRegistrato u){
		Query q = em.createQuery("SELECT a.feedback FROM Aiuto a WHERE :utente = a.utFornisce AND NOT (a.feedback=null)");
		q.setParameter("utente", u);
		List<String> elencoFeedback = q.getResultList();
		if(elencoFeedback.isEmpty()){
			return null;
		} else {
			return elencoFeedback;
		}
	}
	
	//Funziona?? Oppure devo ritornare richiesta per richiesta??
	public List<Aiuto> getElencoRichiesteAiutoFatteNonConfermate(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Aiuto a WHERE a.conferma = false AND a.utRiceve = :utente");
		List<Aiuto> richieste = (List<Aiuto>) q.setParameter("utente", utente).getResultList();
		if(richieste.size() == 0){
			throw new SwimBeanException("Non ci sono richieste di aiuto per te!");
		} else {
			return richieste;
		}
	}
	
	public List<Aiuto> getElencoRichiesteAiutoFatteConfermate(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Aiuto a WHERE a.conferma = true AND a.utRiceve = :utente");
		List<Aiuto> richieste = (List<Aiuto>) q.setParameter("utente", utente).getResultList();
		if(richieste.size() == 0){
			throw new SwimBeanException("Non ci sono richieste di aiuto per te!");
		} else {
			return richieste;
		}
	}
	
	public List<Aiuto> getElencoRichiesteAiutoRicevuteNonConfermate(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Aiuto a WHERE a.conferma = false AND a.utFornisce = :utente");
		List<Aiuto> richieste = (List<Aiuto>) q.setParameter("utente", utente).getResultList();
		if(richieste.size() == 0){
			throw new SwimBeanException("Non ci sono richieste di aiuto per te!");
		} else {
			return richieste;
		}
	}
	
	public List<Aiuto> getElencoRichiesteAiutoRicevuteConfermate(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Aiuto a WHERE a.conferma = true AND a.utFornisce = :utente");
		List<Aiuto> richieste = (List<Aiuto>) q.setParameter("utente", utente).getResultList();
		if(richieste.size() == 0){
			throw new SwimBeanException("Non ci sono richieste di aiuto per te!");
		} else {
			return richieste;
		}
	}
	
	public Abilita ricercaAbilita(String id) {
		Query q = em.createQuery("SELECT a FROM Abilita a WHERE a.id = :id");
		System.out.println(id);
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
