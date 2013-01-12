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
		Query q = em.createQuery("SELECT a FROM Abilita a WHERE :utente MEMBER OF a.utente");
		q.setParameter("utente", utente);
		List<Abilita> abilitaUtente = (List<Abilita>) q.getResultList();
		if(abilitaUtente.isEmpty()){
			throw new SwimBeanException("Questo utente non possiede alcuna abilita'");
		} else {
			return abilitaUtente;
		}
		//return utente.getAbilita();
	}
	
	public List<Abilita> getElencoAbilitaNonMie(UtenteRegistrato utente){
		Query q = em.createQuery("SELECT a FROM Abilita a WHERE NOT (:utente MEMBER OF a.utente) AND a.conferma = 1");
		q.setParameter("utente", utente);
		List<Abilita> abilitaNonUtente = (List<Abilita>) q.getResultList();
		return abilitaNonUtente;
	}
	
	public void aggiungiAbilita(UtenteRegistrato u, Abilita a){
//		Set<Abilita> al = u.getAbilita();
//		al.add(a);
//		u.setAbilita(al);
		Query q = em.createNativeQuery("INSERT INTO `swim`.`ABILITA_UTENTE` (`UtenteRegistrato_ID`, `Abilita_ID`) VALUES (:email, :id);");
		q.setParameter("email", u.getEmail());
		q.setParameter("id", a.getId());
		q.executeUpdate();
	}
	
	public void invioRichiestaNuovaAbilita(String nome, String descr){
		creaNuovaAbilitaDirect(nome, descr);
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
