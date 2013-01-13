package swim.test;

import static org.junit.Assert.*;

import java.util.List;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.junit.BeforeClass;
import org.junit.Test;

import swim.entitybeans.Abilita;
import swim.entitybeans.Aiuto;
import swim.sessionbeans.ManagerAbilitaRemote;
import swim.sessionbeans.ManagerAiutoRemote;
import swim.sessionbeans.ManagerAmministratoreRemote;
import swim.sessionbeans.ManagerUtenteRegistratoRemote;
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
		db.pulisciUtenteRegistrato();
		db.pulisciAbilita();
		db.pulisciAiuto();
		db.pulisciAmministratore();
		db.creaAdminPredefiniti();
		//db.creaAiutiPredefiniti();
		db.creaAbilitaPredefinite();
		db.creaUtentiPredefiniti();
		db.creaAiutiPredefiniti(manUser.ricercaUtente("mr@mail.it"), manUser.ricercaUtente("rp@mail.it"));
		
		List<Abilita> elenco = (List<Abilita>) manAdmin.getElencoRichieste();
		for(int i=0; i<elenco.size(); i++){
			manAdmin.aggiungiAbilita(Integer.toString(elenco.get(i).getId()), elenco.get(i).getNome(), elenco.get(i).getDescrizione(), "admin1@swim.it");
		}
	}
	
	@Test
	public void prova(){
		assertNull("prova", null);
	}

}
