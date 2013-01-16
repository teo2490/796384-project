package swim.test;

import static org.junit.Assert.*;
import static swim.test.ManagerInizializzazioneDatabaseRemote.EMAIL_ADMIN;
import static swim.test.ManagerInizializzazioneDatabaseRemote.PASSWORD_ADMIN;

import java.util.List;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.junit.BeforeClass;
import org.junit.Test;

import swim.entitybeans.Abilita;
import swim.entitybeans.Amministratore;
import swim.sessionbeans.ManagerAmministratoreRemote;
import swim.util.ContextUtil;

public class TestAmministratore {
	
	private static InitialContext ctx = null;
	private static ManagerAmministratoreRemote manager = null;
	
	private static final String EMAIL_NEW_ADMIN = "test@test.it";
	private static final String PASSWORD_NEW_ADMIN = "test";
	private static final int NUMERO_ADMIN_PREDEFINITI = 2;
	private static final int NUMERO_ABILITA_PREDEFINITE = 4;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = ContextUtil.getInitialContext();
		manager = (ManagerAmministratoreRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerAmministratore/remote"), ManagerAmministratoreRemote.class);
		ctx = ContextUtil.getInitialContext();
		ManagerInizializzazioneDatabaseRemote db = (ManagerInizializzazioneDatabaseRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerInizializzazioneDatabase/remote"), ManagerInizializzazioneDatabaseRemote.class);
		db.pulisciAmicizia();
		db.pulisciAiuto();
		db.pulisciABILITA_UTENTE();
		db.pulisciUtenteRegistrato();
		db.pulisciAbilita();
		db.pulisciAmministratore();
		db.creaAdminPredefiniti();
		db.creaAbilitaPredefinite();
	}
	
	@Test
	public void testVerificaLogin(){
		Amministratore a = null;
		for(int i=0; i<NUMERO_ADMIN_PREDEFINITI; i++){
			String email = EMAIL_ADMIN[i];
			String psw = PASSWORD_ADMIN[i];
			a = manager.verificaLogin(email, psw);
			assertNotNull("Mancato login di un amministratore nel DB", a);
			assertEquals("L'amministratore restituito non è quello che ha fatto il login", a.getEmail(), email);
			String pswSbagliata = "0123";
			a = manager.verificaLogin(email, pswSbagliata);
			assertNull("Login effettuato con password sbagliata", a);
		}
		a = manager.verificaLogin(EMAIL_NEW_ADMIN, PASSWORD_NEW_ADMIN);
		assertNull("Login di un amministratore non nel DB", a);
	}
	
	@Test
	public void testCercaAmministratore(){
		Amministratore a = null;
		for(int i=0; i<NUMERO_ADMIN_PREDEFINITI; i++){
			String email = EMAIL_ADMIN[i];
			a = manager.cercaAdmin(email);
			assertEquals("L'amministratore trovato non è quello cercato", a.getEmail(), email);
		}
		a = manager.cercaAdmin("EMAIL_NEW_UTENTE");
		assertNull("Trovato un utente non nel DB", a);
	}
	
	@Test
	public void testCercaAbilita(){
		Abilita a = null;
		List<Abilita> elenco = (List<Abilita>)manager.getElencoRichieste();
		for(int i=0; i<elenco.size(); i++){
			int id = elenco.get(i).getId();
			a = manager.cercaAbilita(id);
			assertEquals("L'abilità trovata non è quella cercata", a.getId(), id);
		}
	}
	
	@Test
	public void testEliminaAbilita(){
		List<Abilita> elenco = (List<Abilita>)manager.getElencoRichieste();
		Integer[] id = new Integer[elenco.size()];
		for(int i=0; i<elenco.size(); i++){
			id[i] = elenco.get(i).getId();
		}
		Abilita a = manager.cercaAbilita(id[0]);
		manager.eliminaAbilita(id[0].toString());
		a = manager.cercaAbilita(id[0]);
		assertNull("L'abilità non è stata eliminata", a);
		for(int i=1; i<elenco.size(); i++){
			a = manager.cercaAbilita(id[i]);
			assertNotNull("L'abilità che è stata eliminata non doveva essere cancellata", a);
		}
	}
	
	@Test
	public void testAggiungiAbilita(){
		List<Abilita> elenco = (List<Abilita>)manager.getElencoRichieste();
		int[] id = new int[elenco.size()];
		for(int i=0; i<elenco.size(); i++){
			id[i] = elenco.get(i).getId();
		}
		Abilita a = manager.cercaAbilita(id[0]);
		String nome = a.getNome();
		String descr = a.getDescrizione();
		manager.aggiungiAbilita(Integer.toString(a.getId()), nome, descr, "admin1@swim.it");
		a = manager.cercaAbilita(id[0]);
		assertTrue("L'abilità non è stata aggiunta", a.getConferma());
		assertEquals("Il creatore dell'abilità non corrisponde", "admin1@swim.it", a.getAdmin().getEmail());
		for(int i=1; i<elenco.size(); i++){
			a = manager.cercaAbilita(id[i]);
			assertFalse("L'abilità che è stata aggiunta non doveva essere aggiunta", a.getConferma());
		}
	}
	
	//Da NUMERO_ABILITA_PREDEFINITE tolgo 2 perché una è stata eliminata e l'altra confermata
	@Test
	public void testGetElencoRichieste(){
		List<Abilita> elenco = (List<Abilita>)manager.getElencoRichieste();
		assertEquals("Non sono state prese tutte le richieste", NUMERO_ABILITA_PREDEFINITE-2, elenco.size());
	}
	
}
