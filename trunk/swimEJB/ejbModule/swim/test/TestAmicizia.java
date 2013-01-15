package swim.test;

import static org.junit.Assert.*;

import java.util.List;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.junit.BeforeClass;
import org.junit.Test;

import swim.entitybeans.Amicizia;
import swim.entitybeans.UtenteRegistrato;
import swim.sessionbeans.ManagerAmiciziaRemote;
import swim.sessionbeans.ManagerUtenteRegistratoRemote;
import swim.sessionbeans.SwimBeanException;
import swim.util.ContextUtil;

public class TestAmicizia {
	
	private static InitialContext ctx = null;
	private static ManagerAmiciziaRemote manager = null;
	private static ManagerUtenteRegistratoRemote manUser = null;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = ContextUtil.getInitialContext();
		manager = (ManagerAmiciziaRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerAmicizia/remote"), ManagerAmiciziaRemote.class);
		manUser = (ManagerUtenteRegistratoRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerUtenteRegistrato/remote"), ManagerUtenteRegistratoRemote.class);
		ctx = ContextUtil.getInitialContext();
		ManagerInizializzazioneDatabaseRemote db = (ManagerInizializzazioneDatabaseRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerInizializzazioneDatabase/remote"), ManagerInizializzazioneDatabaseRemote.class);
		db.pulisciAmicizia();
		db.pulisciAiuto();
		db.pulisciABILITA_UTENTE();
		db.pulisciUtenteRegistrato();
		db.pulisciAbilita();
		db.pulisciAmministratore();
		db.creaUtentiPredefiniti();
		db.creaAmiciziePredefinite(manUser.ricercaUtente("ms@mail.it"), manUser.ricercaUtente("rp@mail.it"));
	}
	
	@Test
	public void testInvioRichiestaAmiciziaAndEsisteAmicizia(){
		UtenteRegistrato richiedente = manUser.ricercaUtente("rp@mail.it");
		UtenteRegistrato richiesto = manUser.ricercaUtente("mr@mail.it");
		manager.invioRichiestaAmicizia(richiedente, richiesto);
		boolean flag = manager.esisteAmicizia(richiesto, richiedente);
		assertTrue("L'amicizia non è stata inviata", flag);
		flag = manager.esisteAmicizia(richiedente, richiesto);
		assertTrue("L'amicizia non è stata inviata", flag);
	}
	
	@Test
	public void testGetElencoAmicizieRicevuteAndGetElencoAmicizieInviate() throws SwimBeanException{
		UtenteRegistrato richiedente = manUser.ricercaUtente("rp@mail.it");
		UtenteRegistrato richiesto = manUser.ricercaUtente("mr@mail.it");
		List<Amicizia> rp = (List<Amicizia>) manager.getElencoRichiesteAmiciziaInviate(richiedente);
		List<Amicizia> mr = (List<Amicizia>) manager.getElencoRichiesteAmiciziaRicevute(richiesto);
		assertEquals("Le amicizie non corrispondono nel richiedente", rp.get(0).getId(), mr.get(0).getId());
	}
	
	@Test
	public void testAccettaAmiciziaAndRicercaAmicizia() throws SwimBeanException{
		UtenteRegistrato richiesto = manUser.ricercaUtente("mr@mail.it");
		List<Amicizia> mr = (List<Amicizia>) manager.getElencoRichiesteAmiciziaRicevute(richiesto);
		Integer idd = mr.get(0).getId();
		String id = idd.toString();
		manager.accettaAmicizia(id);
		assertTrue("L'amicizia non è stata accettata", manager.ricercaAmicizia(id).getConferma());
	}

}
