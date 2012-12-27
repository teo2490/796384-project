package swim.test;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import swim.entitybeans.UtenteRegistrato;

@Stateless
@Remote(ManagerInizializzazioneDatabaseRemote.class)
public class ManagerInizializzazioneDatabase implements ManagerInizializzazioneDatabaseRemote{
	
	@PersistenceContext(unitName="swim") 
	private EntityManager em;
	
	private static final String[] NOME_ABILITA = {"Cucinare", "Verniciare", "Java", "C/C++"};
	
	public void pulisci(){
		for(UtenteRegistrato u: (ArrayList<UtenteRegistrato>) em.createQuery("SELECT u FROM UtenteRegistrato u").getResultList()){
			u.setAbilita(null);
			em.persist(u);
		}
		em.flush();
		for(String nome: NOME_ABILITA){
			em.createQuery("DELETE" +nome+ " o").executeUpdate();
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

}
