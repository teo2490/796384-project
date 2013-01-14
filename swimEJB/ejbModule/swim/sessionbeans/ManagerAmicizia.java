package swim.sessionbeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import swim.entitybeans.Aiuto;
import swim.entitybeans.UtenteRegistrato;
import swim.entitybeans.Amicizia;

@Stateless
@Remote(ManagerAmiciziaRemote.class)
public class ManagerAmicizia implements ManagerAmiciziaRemote{
	
	@PersistenceContext( unitName = "swim" )
	private EntityManager em;
	
	//private Amicizia amicizia;
	
	private void creaAmiciziaDirect(UtenteRegistrato richiedente, UtenteRegistrato richiesto){
		Amicizia amicizia = new Amicizia();
		amicizia.setRichiedente(richiedente);
		amicizia.setRichiesto(richiesto);
	}
	
	public void invioRichiestaAmicizia(UtenteRegistrato richiedente, UtenteRegistrato richiesto) throws SwimBeanException{
		creaAmiciziaDirect(richiedente, richiesto);
	}
	
	//Funziona?? Oppure devo ritornare utente per utente??
	public List<UtenteRegistrato> getElencoAmici(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Amicizia a WHERE a.conferma = true AND a.utRichiedente = :utente");
		List<Amicizia> amicRichiedente = (List<Amicizia>) q.setParameter("utente", utente).getResultList();
		q = em.createQuery("SELECT a FROM Amicizia a WHERE a.conferma = true AND a.utRichiesto = :utente");
		List<Amicizia> amicRichiesto = (List<Amicizia>) q.setParameter("utente", utente).getResultList();
		List<UtenteRegistrato> amici = new ArrayList<UtenteRegistrato>();
		for(int i=0; i<amicRichiedente.size(); i++){
			amici.add(amicRichiedente.get(i).getRichiedente());
		}
		for(int i=0; i<amicRichiesto.size(); i++){
			amici.add(amicRichiesto.get(i).getRichiesto());
		}
		if(amici.size() == 0){
			throw new SwimBeanException("Non hai amici!");
		} else {
			return amici;
		}
	}
	
	//Funziona?? Oppure devo ritornare richiesta per richiesta??
	public List<Amicizia> getElencoRichiesteAmiciziaRicevute(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Amicizia a WHERE (a.conferma = false AND a.utRichiesto = :utente))");
		List<Amicizia> richieste = (List<Amicizia>) q.setParameter("utente", utente).getResultList();
		if(richieste.size() == 0){
			throw new SwimBeanException("Non ci sono richieste di amicizia per te!");
		} else {
			return richieste;
		}
	}
	
	public List<Amicizia> getElencoRichiesteAmiciziaInviate(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Amicizia a WHERE (a.conferma = false AND a.utRichiedente = :utente))");
		List<Amicizia> richieste = (List<Amicizia>) q.setParameter("utente", utente).getResultList();
		if(richieste.size() == 0){
			throw new SwimBeanException("Non ci sono amicizie inviate da te!");
		} else {
			return richieste;
		}
	}
	
	public void accettaAmicizia(String id){
		Amicizia a = ricercaAmicizia(id);
		a.switchConferma();
	}
	
	public boolean esisteAmicizia(UtenteRegistrato u1, UtenteRegistrato u2){
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
	
	public Amicizia ricercaAmicizia(String id) {
		Query q = em.createQuery("SELECT a FROM Aiuto a WHERE a.id = :id");
		//System.out.println(id);
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
