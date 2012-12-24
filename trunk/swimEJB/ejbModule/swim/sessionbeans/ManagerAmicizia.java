package swim.sessionbeans;

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
	
	private Amicizia amicizia;
	
	private void creaAmiciziaDirect(UtenteRegistrato richiedente, UtenteRegistrato richiesto){
		amicizia = new Amicizia();
		amicizia.setRichiedente(richiedente);
		amicizia.setRichiesto(richiesto);
	}
	
	public void invioRichiestaAmicizia(UtenteRegistrato richiedente, UtenteRegistrato richiesto) throws SwimBeanException{
		creaAmiciziaDirect(richiedente, richiesto);
	}
	
	//Funziona?? Oppure devo ritornare utente per utente??
	public Set<UtenteRegistrato> getElecoAmici(UtenteRegistrato utente){
		Query q = em.createQuery("SELECT u FROM (UtenteRegistrato u, Amicizia a) WHERE (a.conferma = true AND (u IN :utente.sFriendship OR u IN :utente.rFriendship))");
		Set<UtenteRegistrato> amici = (Set<UtenteRegistrato>) q.setParameter("utente", utente).getResultList();
		return amici;
	}
	
	//Funziona?? Oppure devo ritornare richiesta per richiesta??
	public Set<Amicizia> getElencoRichiesteAmicizia(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Amicizia a WHERE (a.conferma = false AND (a.utRichiedente = :utente OR a.utRichiesto = :utente))");
		Set<Amicizia> richieste = (Set<Amicizia>) q.setParameter("utente", utente).getResultList();
		if(richieste.size() == 0){
			throw new SwimBeanException("Non ci sono richieste di amicizia per te!");
		} else {
			return richieste;
		}
	}
	
	public void accettaAmicizia(){
		amicizia.switchConferma();
	}

}
