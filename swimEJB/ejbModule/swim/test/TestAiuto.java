package swim.test;

import static org.junit.Assert.*;

import java.util.List;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.junit.BeforeClass;
import org.junit.Test;

import swim.entitybeans.Abilita;
import swim.entitybeans.Aiuto;
import swim.entitybeans.UtenteRegistrato;
import swim.sessionbeans.ManagerAbilitaRemote;
import swim.sessionbeans.ManagerAiutoRemote;
import swim.sessionbeans.ManagerAmministratoreRemote;
import swim.sessionbeans.ManagerUtenteRegistratoRemote;
import swim.sessionbeans.SwimBeanException;
import swim.util.ContextUtil;

public class TestAiuto {
	
	private static InitialContext ctx = null;
	private static ManagerAiutoRemote manager = null;
	private static ManagerAbilitaRemote manAbil = null;
	private static ManagerAmministratoreRemote manAdmin = null;
	private static ManagerUtenteRegistratoRemote manUser = null;
	
	private static final int NUMERO_ABILITA_PREDEFINITE = 4;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = ContextUtil.getInitialContext();
		manager = (ManagerAiutoRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerAiuto/remote"), ManagerAiutoRemote.class);
		manAbil = (ManagerAbilitaRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerAbilita/remote"), ManagerAbilitaRemote.class);
		manAdmin = (ManagerAmministratoreRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerAmministratore/remote"), ManagerAmministratoreRemote.class);
		manUser = (ManagerUtenteRegistratoRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerUtenteRegistrato/remote"), ManagerUtenteRegistratoRemote.class);
		ctx = ContextUtil.getInitialContext();
		ManagerInizializzazioneDatabaseRemote db = (ManagerInizializzazioneDatabaseRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerInizializzazioneDatabase/remote"), ManagerInizializzazioneDatabaseRemote.class);
		db.pulisciABILITA_UTENTE();
		db.pulisciAiuto();
		db.pulisciAbilita();
		db.pulisciUtenteRegistrato();
		db.pulisciAmministratore();
		db.creaAdminPredefiniti();
		db.creaUtentiPredefiniti();
		db.creaAbilitaPredefinite();
		db.creaAiutiPredefiniti(manUser.ricercaUtente("mr@mail.it"), manUser.ricercaUtente("rp@mail.it"));
		db.creaABILITA_UTENTE();
		
		List<Abilita> elenco = (List<Abilita>) manAdmin.getElencoRichieste();
		for(int i=0; i<elenco.size(); i++){
			manAdmin.aggiungiAbilita(Integer.toString(elenco.get(i).getId()), elenco.get(i).getNome(), elenco.get(i).getDescrizione(), "admin1@swim.it");
		}
	}
	
	@Test
	public void testRicercaTraAmici(){
		//Da fare quando creo la tabella amicizia!!
	}
	
	@Test
	public void testRicercaPerAbilita() throws SwimBeanException{
		
		List<Abilita> elenco = (List<Abilita>) manAbil.getElencoAbilita();
		for(int i=0; i<elenco.size(); i++){
			if(elenco.get(i).getNome() == "C" || elenco.get(i).getNome() == "Java"){
				List<String> elencoEmail = manager.ricercaPerAbilita(elenco.get(i));
				UtenteRegistrato u = manUser.ricercaUtente(elencoEmail.get(0));
				assertNotNull("L'abilità è posseduta da almeno un utente", u);
			} else {
				List<String> elencoEmail = manager.ricercaPerAbilita(elenco.get(i));
				System.out.println(elenco.get(i));
				System.out.println(manUser.ricercaUtente(elencoEmail.get(0)).getEmail());
				UtenteRegistrato u = manUser.ricercaUtente(elencoEmail.get(0));
				assertNull("L'abilità" +elenco.get(i).getNome()+ "non è posseduta da nessun utente", u);
			}
		}
	}

}
