package swim.sessionbeans;

import java.util.Set;

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
	
	private Abilita abilita;
	
	private void creaNuovaAbilitaDirect(String nome, String descr){
		abilita = new Abilita();
		abilita.setNome(nome);
		abilita.setDescrizione(descr);
	}
	
	//Funziona?? Oppure devo tornare abilita per abilita??
	public Set<Abilita> getElencoAbilita(){
		Query q = em.createQuery("SELECT a FROM Abilita a");
		Set<Abilita> allAbilita = (Set<Abilita>) q.getResultList();
		return allAbilita;
	}
	
	//Funziona?? Oppure devo tornare abilita per abilita??
	public Set<Abilita> getAbilitaUtente(UtenteRegistrato utente) throws SwimBeanException{
		Query q = em.createQuery("SELECT a FROM Abilita a WHERE a IN :utente.abilita");
		Set<Abilita> abilitaUtente = (Set<Abilita>) q.setParameter("utente", utente).getResultList();
		if(abilitaUtente.size() == 0){
			throw new SwimBeanException("Questo utente non possiede alcuna abilita'");
		} else {
			return abilitaUtente;
		}
	}
	
	public void aggiungiAbilita(UtenteRegistrato utente){
		utente.setAbilita(abilita);
	}
	
	public void invioRichiestaNuovaAbilita(String nome, String descr){
		creaNuovaAbilitaDirect(nome, descr);
	}

}
