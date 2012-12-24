package swim.sessionbeans;

import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import swim.entitybeans.Abilita;
import swim.entitybeans.Amministratore;

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
	
	public void logout(){
		admin = null;
	}
	
	public Set<Abilita> getElencoRichieste(){
		Query q = em.createQuery("SELECT a FROM Abilita a WHERE a.conferma = 0");
		Set<Abilita> richieste = (Set<Abilita>) q.getResultList();
		return richieste;
	}
	
	public void aggiungiAbilita(){
		abilita.switchConferma();
		abilita.setAmministratore(admin);
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
