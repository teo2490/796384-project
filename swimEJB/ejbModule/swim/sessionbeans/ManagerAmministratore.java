package swim.sessionbeans;

import java.util.List;

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
	
	private List<Amministratore> amministratori;
	
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
	
	public void eliminaAbilita(String id){
		int iid = Integer.parseInt(id);
		Abilita ab = cercaAbilita(iid);
		Query q = em.createQuery("DELETE FROM Abilita a WHERE a.id = :id");
		q.setParameter("id", ab.getId()).executeUpdate();
	}

}
