package swim.test;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import swim.entitybeans.Abilita;
import swim.entitybeans.Aiuto;
import swim.entitybeans.Amministratore;
import swim.entitybeans.UtenteRegistrato;

@Stateless
@Remote(ManagerInizializzazioneDatabaseRemote.class)
public class ManagerInizializzazioneDatabase implements ManagerInizializzazioneDatabaseRemote{
	
	@PersistenceContext(unitName="swim") 
	private EntityManager em;
	
	public void pulisciUtenteRegistrato(){
		List<UtenteRegistrato> u = (List<UtenteRegistrato>) em.createQuery("SELECT u FROM UtenteRegistrato u").getResultList();
		for(int i=0; i<u.size(); i++){	
			Query q = em.createQuery("DELETE FROM UtenteRegistrato u WHERE u.email = :email");
			q.setParameter("email", u.get(i).getEmail());
			q.executeUpdate();
		}
	}
	
	public void creaUtentiPredefiniti(){
		for(int i=0; i < NUMERO_UTENTI_PREDEFINITI; i++){
			UtenteRegistrato u = new UtenteRegistrato();
			u.setEmail(EMAIL_UTENTI[i]);
			u.setPsw(PASSWORD_UTENTI[i]);
			u.setNome(NOME_UTENTI[i]);
			u.setCognome(COGNOME_UTENTI[i]);
			em.persist(u);
		}
	}
	
	public void pulisciAmministratore(){
		List<Amministratore> a = (List<Amministratore>) em.createQuery("SELECT a FROM Amministratore a").getResultList();
		for(int i=0; i<a.size(); i++){	
			Query q = em.createQuery("DELETE FROM Amministratore a WHERE a.email = :email");
			q.setParameter("email", a.get(i).getEmail());
			q.executeUpdate();
		}
	}
	
	public void creaAdminPredefiniti(){
		for(int i=0; i < NUMERO_ADMIN_PREDEFINITI; i++){
			Amministratore a = new Amministratore();
			a.setEmail(EMAIL_ADMIN[i]);
			a.setPsw(PASSWORD_ADMIN[i]);
			em.persist(a);
		}
	}
	
	public void pulisciABILITA_UTENTE(){
		Query q = em.createNativeQuery("DELETE FROM `swim`.`ABILITA_UTENTE` WHERE `UtenteRegistrato_ID`='rp@mail.it'");
		q.executeUpdate();
	}
	
	public void pulisciAbilita(){
		List<Abilita> a = (List<Abilita>) em.createQuery("SELECT a FROM Abilita a").getResultList();
		for(int i=0; i<a.size(); i++){	
			Query q = em.createQuery("DELETE FROM Abilita a WHERE a.id = :id");
			q.setParameter("id", a.get(i).getId());
			q.executeUpdate();
		}	
	}
	
	public void creaAbilitaPredefinite(){
		for(int i=0; i < NUMERO_ABILITA_PREDEFINITE; i++){
			Abilita a = new Abilita();
			a.setNome(NOME_ABILITA[i]);
			a.setDescrizione(DESCR_ABILITA[i]);
			em.persist(a);
		}
	}
	
	public void pulisciAiuto(){
		List<Aiuto> a = (List<Aiuto>) em.createQuery("SELECT a FROM Aiuto a").getResultList();
		for(int i=0; i<a.size(); i++){	
			Query q = em.createQuery("DELETE FROM Abilita a WHERE a.id = :id");
			q.setParameter("id", a.get(i).getId());
			q.executeUpdate();
		}
	}
	
	public void creaAiutiPredefiniti(UtenteRegistrato fornitore, UtenteRegistrato ricevente){
			Aiuto a = new Aiuto();
			a.setTipo("Cucinare");
			a.setUtFornisce(fornitore);
			a.setUtRiceve(ricevente);
			em.persist(a);
			Aiuto a1 = new Aiuto();
			a1.setTipo("Verniciare");
			a1.setUtFornisce(fornitore);
			a1.setUtRiceve(ricevente);
			em.persist(a1);
	}
	
}

