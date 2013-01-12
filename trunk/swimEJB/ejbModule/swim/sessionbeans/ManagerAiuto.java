package swim.sessionbeans;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
	
	// SERVE UN METODO DI RICERCA AIUTO SOLO TRA GLI AMICI!!
	public Set<UtenteRegistrato> ricercaTraAmici(String abilita) throws SwimBeanException{
		Query q = em.createQuery("");
		Set<UtenteRegistrato> utenti = (Set<UtenteRegistrato>) q.setParameter("abilita", abilita).getResultList();
		if(utenti.size() == 0){
			throw new SwimBeanException("Nessun utente ha questa abilita'");
		} else {
			return utenti;
		}
	}
	
	
	//Funziona?? Oppure devo ritornare utente per utente??
	public List<UtenteRegistrato> ricercaPerAbilita(String abilita) throws SwimBeanException{
		Query q = em.createQuery("SELECT u FROM UtenteRegistrato u WHERE :abilita IN u.abilita");
		List<UtenteRegistrato> utenti = (List<UtenteRegistrato>) q.setParameter("abilita", abilita).getResultList();
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
	
	public String controlloFeedback() throws SwimBeanException{
		String feedback = aiuto.getFeedback();
		if(feedback.equals("")){
			throw new SwimBeanException("Nessun feedback disponibile per questo aiuto!");
		} else {
			return feedback;
		}
	}
	
	public void aggiungiFeedback(String feedback){
		aiuto.setFeedback(feedback);
	}
	
	//Funziona?? Oppure devo ritornare richiesta per richiesta??
	public Set<Aiuto> getElencoRichiesteAiuto(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Aiuto a WHERE (a.conferma = false AND (a.utFornisce = :utente OR a.utRiceve = :utente))");
		Set<Aiuto> richieste = (Set<Aiuto>) q.setParameter("utente", utente).getResultList();
		if(richieste.size() == 0){
			throw new SwimBeanException("Non ci sono richieste di aiuto per te!");
		} else {
			return richieste;
		}
	}

}
