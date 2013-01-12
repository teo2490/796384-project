package swim.test;

import static org.junit.Assert.*;

import java.util.List;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.junit.BeforeClass;
import org.junit.Test;

import swim.entitybeans.Abilita;
import swim.entitybeans.UtenteRegistrato;
import swim.sessionbeans.ManagerAbilitaRemote;
import swim.sessionbeans.ManagerAmministratoreRemote;
import swim.sessionbeans.ManagerUtenteRegistratoRemote;
import swim.sessionbeans.SwimBeanException;
import swim.util.ContextUtil;

public class TestAbilita {
	
	private static InitialContext ctx = null;
	private static ManagerAbilitaRemote manager = null;
	private static ManagerAmministratoreRemote manAdmin = null;
	private static ManagerUtenteRegistratoRemote manUser = null;
	
	private static final String EMAIL_NEW_ADMIN = "test@test.it";
	private static final String PASSWORD_NEW_ADMIN = "test";
	private static final int NUMERO_ADMIN_PREDEFINITI = 2;
	private static final int NUMERO_ABILITA_PREDEFINITE = 4;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = ContextUtil.getInitialContext();
		manager = (ManagerAbilitaRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerAbilita/remote"), ManagerAbilitaRemote.class);
		manAdmin = (ManagerAmministratoreRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerAmministratore/remote"), ManagerAmministratoreRemote.class);
		manUser = (ManagerUtenteRegistratoRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerUtenteRegistrato/remote"), ManagerUtenteRegistratoRemote.class);
		ctx = ContextUtil.getInitialContext();
		ManagerInizializzazioneDatabaseRemote db = (ManagerInizializzazioneDatabaseRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerInizializzazioneDatabase/remote"), ManagerInizializzazioneDatabaseRemote.class);
		db.pulisciAbilita();
		db.pulisciAmministratore();
		db.pulisciUtenteRegistrato();
		db.creaUtentiPredefiniti();
		db.creaAdminPredefiniti();
		db.creaAbilitaPredefinite();
		List<Abilita> elenco = (List<Abilita>) manAdmin.getElencoRichieste();
		for(int i=0; i<elenco.size(); i++){
			manAdmin.aggiungiAbilita(Integer.toString(elenco.get(i).getId()), elenco.get(i).getNome(), elenco.get(i).getDescrizione(), "admin1@swim.it");
		}
		
	}
	
	@Test
	public void testInvioRichiestaNuovaAbilita(){
		String nome = "newAbilita";
		String descr = "newDescr";
		manager.invioRichiestaNuovaAbilita(nome, descr);
		List<Abilita> elenco = (List<Abilita>) manAdmin.getElencoRichieste();
		Abilita a = elenco.get(0);
		assertEquals("L'abilità è stata inserita con un altro nome", nome, a.getNome());
		assertEquals("L'abilità è stata inserita con un'altra descrizione", descr, a.getDescrizione());
	}
	
	@Test
	public void testGetElencoAbilita(){
		List<Abilita> elenco = (List<Abilita>)manager.getElencoAbilita();
		assertEquals("Non sono state prese tutte le abilità", NUMERO_ABILITA_PREDEFINITE, elenco.size());
	}
	
	@Test
	public void testAggiungiAbilitaAndGetElencoAbilita() throws SwimBeanException{
		UtenteRegistrato u = manUser.ricercaUtente("rp@mail.it");
		List<Abilita> elenco = (List<Abilita>)manager.getElencoAbilita();
		List<Abilita> ablOwned = null;
		manager.aggiungiAbilita(u, elenco.get(0));
		System.out.println(elenco.get(0).getNome());
		System.out.println(u.getEmail());
		manager.aggiungiAbilita(u, elenco.get(1));
		try{
			ablOwned = manager.getAbilitaUtente(u);
		} catch (SwimBeanException e) {}
		assertNotNull("Abilità in 0 è nulla", ablOwned.get(0));
		assertNotNull("Abilità in 1 è nulla", ablOwned.get(1));
	}

}
