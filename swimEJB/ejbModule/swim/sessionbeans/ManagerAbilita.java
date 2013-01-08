package swim.sessionbeans;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sun.jmx.snmp.Timestamp;

import swim.entitybeans.Abilita;
import swim.entitybeans.UtenteRegistrato;

@Stateless
@Remote(ManagerAbilitaRemote.class)
public class ManagerAbilita implements ManagerAbilitaRemote{
	
	@PersistenceContext( unitName = "swim" )
	private EntityManager em;
	
	private Abilita abilita;
	
	private void creaNuovaAbilitaDirect(String nome, String descr){
		abilita = new Abilita();
		abilita.setNome(nome);
		abilita.setDescrizione(descr);
		em.persist(abilita);
	}
	
	//Funziona?? Oppure devo tornare abilita per abilita??
	public List<Abilita> getElencoAbilita(){
		Query q = em.createQuery("SELECT a FROM Abilita a WHERE a.conferma = 1");
		List<Abilita> allAbilita = (List<Abilita>) q.getResultList();
		return allAbilita;
	}
	
	//Funziona?? Oppure devo tornare abilita per abilita??
	public List<Abilita> getAbilitaUtente(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT ua.Abilita_ID FROM EJB_ROSTER_TEAM_PLAYER ua WHERE ua.UtenteRegistrato_ID = :utente.email");
		q.setParameter("utente", utente);
		List<Abilita> abilitaUtente = (List<Abilita>) q.getResultList();
		if(abilitaUtente.size() == 0){
			throw new SwimBeanException("Questo utente non possiede alcuna abilita'");
		} else {
			return abilitaUtente;
		}
	}
	
	public List<Abilita> getElencoAbilitaNonMie(UtenteRegistrato utente){
		Query q = em.createQuery("SELECT a FROM Abilita a WHERE a NOT IN :utente.abilita");
		List<Abilita> abilitaNonUtente = (List<Abilita>) q.setParameter("utente", utente).getResultList();
		return abilitaNonUtente;
	}
	
	public void aggiungiAbilita(UtenteRegistrato utente){
		utente.setAbilita(abilita);
	}
	
	public void invioRichiestaNuovaAbilita(String nome, String descr){
		creaNuovaAbilitaDirect(nome, descr);
	}

}
