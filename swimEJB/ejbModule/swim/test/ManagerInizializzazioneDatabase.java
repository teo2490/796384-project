package swim.test;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.rmi.PortableRemoteObject;

import swim.entitybeans.Abilita;
import swim.entitybeans.Aiuto;
import swim.entitybeans.Amicizia;
import swim.entitybeans.Amministratore;
import swim.entitybeans.UtenteRegistrato;
import swim.sessionbeans.ManagerAbilitaRemote;
import swim.sessionbeans.ManagerAiutoRemote;
import swim.sessionbeans.ManagerAmiciziaRemote;
import swim.sessionbeans.ManagerAmministratoreRemote;
import swim.sessionbeans.ManagerUtenteRegistratoRemote;
import swim.util.ContextUtil;

@Stateless
@Remote(ManagerInizializzazioneDatabaseRemote.class)
public class ManagerInizializzazioneDatabase implements ManagerInizializzazioneDatabaseRemote {
	
	
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
	
	public void pulisciAiuto(){
		List<Aiuto> a = (List<Aiuto>) em.createQuery("SELECT a FROM Aiuto a").getResultList();
		for(int i=0; i<a.size(); i++){	
			Query q = em.createQuery("DELETE FROM Aiuto a WHERE a.id = :id");
			q.setParameter("id", a.get(i).getId());
			q.executeUpdate();
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
	
	public void pulisciABILITA_UTENTE(){
		Query q = em.createNativeQuery("DELETE FROM `swim`.`ABILITA_UTENTE` WHERE NOT(`UtenteRegistrato_ID` = 'a')");
		//Query q1 = em.createNativeQuery("DELETE FROM `swim`.`ABILITA_UTENTE` WHERE `UtenteRegistrato_ID`='mr@mail.it'");
		q.executeUpdate();
		//q1.executeUpdate();
	}
	
	public void pulisciAbilita(){
		List<Abilita> a = (List<Abilita>) em.createQuery("SELECT a FROM Abilita a").getResultList();
		for(int i=0; i<a.size(); i++){	
			Query q = em.createQuery("DELETE FROM Abilita a WHERE a.id = :id");
			q.setParameter("id", a.get(i).getId());
			q.executeUpdate();
		}	
	}
	
	public void pulisciAmicizia(){
		List<Amicizia> a = (List<Amicizia>) em.createQuery("SELECT a FROM Amicizia a").getResultList();
		for(int i=0; i<a.size(); i++){	
			Query q = em.createQuery("DELETE FROM Amicizia a WHERE a.id = :id");
			q.setParameter("id", a.get(i).getId());
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
	
	public void creaAdminPredefiniti(){
		for(int i=0; i < NUMERO_ADMIN_PREDEFINITI; i++){
			Amministratore a = new Amministratore();
			a.setEmail(EMAIL_ADMIN[i]);
			a.setPsw(PASSWORD_ADMIN[i]);
			em.persist(a);
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
	
	public void creaAiutiPredefiniti(UtenteRegistrato Fornisce, UtenteRegistrato Riceve){
		Aiuto a = new Aiuto();
		a.setTipo("Cucinare");
		a.setUtFornisce(Fornisce);
		a.setUtRiceve(Riceve);
		em.persist(a);
		Aiuto a1 = new Aiuto();
		a1.setTipo("Verniciare");
		a1.setUtFornisce(Fornisce);
		a1.setUtRiceve(Riceve);
		em.persist(a1);
}
	
	public void creaABILITA_UTENTE(){
		Query q = em.createNativeQuery("INSERT INTO `swim`.`ABILITA_UTENTE` (`UtenteRegistrato_ID`, `Abilita_ID`) VALUES (:email, :id);");
		q.setParameter("email", "rp@mail.it");
		Query q1 = em.createQuery("SELECT a.id FROM Abilita a WHERE a.nome = 'C'");
		List<Integer> idAbilita = q1.getResultList();
		q.setParameter("id", idAbilita.get(0));
		q.executeUpdate();
		q = em.createNativeQuery("INSERT INTO `swim`.`ABILITA_UTENTE` (`UtenteRegistrato_ID`, `Abilita_ID`) VALUES (:email, :id);");
		q.setParameter("email", "rp@mail.it");
		q1 = em.createQuery("SELECT a.id FROM Abilita a WHERE a.nome = 'Java'");
		idAbilita = q1.getResultList();
		q.setParameter("id", idAbilita.get(0));
		q.executeUpdate();
		q = em.createNativeQuery("INSERT INTO `swim`.`ABILITA_UTENTE` (`UtenteRegistrato_ID`, `Abilita_ID`) VALUES (:email, :id);");
		q.setParameter("email", "mr@mail.it");
		q1 = em.createQuery("SELECT a.id FROM Abilita a WHERE a.nome = 'Verniciare'");
		idAbilita = q1.getResultList();
		q.setParameter("id", idAbilita.get(0));
		q.executeUpdate();
	}
	
	public void creaAmiciziePredefinite(UtenteRegistrato richiedente, UtenteRegistrato richiesto){
		Amicizia a = new Amicizia();
		a.setRichiedente(richiedente);
		a.setRichiesto(richiesto);
		a.switchConferma();
		em.persist(a);
	}
	
}

