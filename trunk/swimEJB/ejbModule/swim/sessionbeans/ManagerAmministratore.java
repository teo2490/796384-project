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
	
	//private Amministratore admin;
	private List<Amministratore> amministratori;
	//private Abilita abilita;
	
	public Amministratore verificaLogin(String email, String password) {
		Query q = em.createQuery("SELECT a FROM Amministratore a WHERE a.email = :email AND a.password = :password");
		q.setParameter("email", email);
		q.setParameter("password", password);
		amministratori = (List<Amministratore>) q.getResultList();
		if (amministratori.isEmpty()) {
			return null;
		} else {
			return amministratori.get(0);
		}
	}
	
	public Amministratore cercaAdmin(String email){
		Query q = em.createQuery("SELECT a FROM Amministratore a WHERE a.email = :email");
		q.setParameter("email", email);
		amministratori = (List<Amministratore>) q.getResultList();
		if (amministratori.isEmpty()) {
			return null;
		} else {
			return amministratori.get(0);
		}
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
	

	public Abilita cercaAbilita(int id) {
		Query q = em
				.createQuery("SELECT a FROM Abilita a WHERE a.id = :id");
		q.setParameter("id", id);
		List<Abilita> elenco = (List<Abilita>) q.getResultList();
		if (elenco.isEmpty()) {
			return null;
		} else {
			return elenco.get(0);
		}
	}
	
	public void aggiungiAbilita(String id, String nome, String desc, String email){
		Amministratore adm = cercaAdmin(email);
		int iid = Integer.parseInt(id);
		Abilita ab = cercaAbilita(iid);
		ab.setNome(nome);
		ab.setDescrizione(desc);
		ab.switchConferma();
		ab.setAmministratore(adm);
		em.persist(ab);
	}
	
	//Funziona?? Non so se va bene la query o serve altro!
	public void eliminaAbilita(Abilita abilita){
		Query q = em.createQuery("DELETE FROM Abilita a WHERE a.id = :id");
		q.setParameter("id", abilita.getId()).executeUpdate();
	}
	
	/*public void cambiaDatiAbilita(Abilita abilita, String nome, String descr){
		abilita.setNome(nome);
		abilita.setDescrizione(descr);
	}*/

}
