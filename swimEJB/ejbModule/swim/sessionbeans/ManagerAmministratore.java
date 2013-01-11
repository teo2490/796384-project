package swim.sessionbeans;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import swim.entitybeans.Abilita;
import swim.entitybeans.Amministratore;
import swim.entitybeans.UtenteRegistrato;

@Stateless
@Remote(ManagerAmministratoreRemote.class)
public class ManagerAmministratore implements ManagerAmministratoreRemote{
	
	@PersistenceContext( unitName = "swim" )
	private EntityManager em;
	
	private Amministratore admin;
	private Abilita abilita;
	
	public Amministratore verificaLogin(String email, String password){
		admin = em.find(Amministratore.class, email);
		if(admin.getPsw().equals(password)) {
			return admin;
		} else {
			return null;
		}
	}
	
	public Amministratore cercaAdmin(String email){
		admin = em.find(Amministratore.class, email);
		return admin;
	}
	/*
	public void logout(){
		admin = null;
	}
	*/
	public List<Abilita> getElencoRichieste(){
		Query q = em.createQuery("SELECT a FROM Abilita a WHERE a.conferma = 0");
		List<Abilita> richieste = (List<Abilita>) q.getResultList();
		return richieste;
	}
	
	public Abilita cercaAbilita(String id) {
		Query q = em
				.createQuery("SELECT a FROM Abilita a WHERE a.id = :id");
		q.setParameter("id", id);
		Abilita a = (Abilita) q.getResultList().get(0);
		return a;
	}
	
	public void aggiungiAbilita(String id, String nome, String desc, String email){
		Amministratore adm = cercaAdmin(email);
		Abilita ab = cercaAbilita(id);
		ab.setNome(nome);
		ab.setDescrizione(desc);
		ab.switchConferma();
		ab.setAmministratore(adm);
		em.persist(ab);
	}
	
	//Funziona?? Non so se va bene la query o serve altro!
	public void eliminaAbilita(){
		Query q = em.createQuery("DELETE a FROM Abilita a WHERE a.id = :abl.id");
		q.setParameter("abl", abilita).executeUpdate();
	}
	
	public void cambiaDatiAbilita(String nome, String descr){
		abilita.setNome(nome);
		abilita.setDescrizione(descr);
	}

}
