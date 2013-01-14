package swim.test;

import static org.junit.Assert.*;
import static swim.test.ManagerInizializzazioneDatabaseRemote.EMAIL_UTENTI;
import static swim.test.ManagerInizializzazioneDatabaseRemote.PASSWORD_UTENTI;

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

public class TestUtenteRegistrato {
	
	private static InitialContext ctx = null;
	private static ManagerUtenteRegistratoRemote manager = null;
	private static ManagerAbilitaRemote manAbil = null;
	private static ManagerAmministratoreRemote manAdmin = null;
	
	private static final String EMAIL_NEW_UTENTE = "test@test.fr";
	private static final String PASSWORD_NEW_UTENTE = "test";
	private static final int NUMERO_UTENTI_PREDEFINITI = 3;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = ContextUtil.getInitialContext();
		manager = (ManagerUtenteRegistratoRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerUtenteRegistrato/remote"), ManagerUtenteRegistratoRemote.class);
		manAbil = (ManagerAbilitaRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerAbilita/remote"), ManagerAbilitaRemote.class);
		manAdmin = (ManagerAmministratoreRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerAmministratore/remote"), ManagerAmministratoreRemote.class);
		ctx = ContextUtil.getInitialContext();
		ManagerInizializzazioneDatabaseRemote db = (ManagerInizializzazioneDatabaseRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerInizializzazioneDatabase/remote"), ManagerInizializzazioneDatabaseRemote.class);
		db.pulisciABILITA_UTENTE();
		db.pulisciAbilita();
		db.pulisciUtenteRegistrato();
		db.creaUtentiPredefiniti();
		db.creaAbilitaPredefinite();
		db.creaABILITA_UTENTE();
		
		List<Abilita> elenco = (List<Abilita>) manAdmin.getElencoRichieste();
		for(int i=0; i<elenco.size(); i++){
			manAdmin.aggiungiAbilita(Integer.toString(elenco.get(i).getId()), elenco.get(i).getNome(), elenco.get(i).getDescrizione(), "admin1@swim.it");
		}
	}
	
	@Test
	public void testVerificaLogin(){
		UtenteRegistrato u = null;
		for(int i=0; i<NUMERO_UTENTI_PREDEFINITI; i++){
			String email = EMAIL_UTENTI[i];
			String psw = PASSWORD_UTENTI[i];
			u = manager.verificaLogin(email, psw);
			assertNotNull("Mancato login di un utente nel DB", u);
			assertEquals("Utente restituito non è quello che ha fatto il login", u.getEmail(), email);
			String pswSbagliata = "0123";
			u = manager.verificaLogin(email, pswSbagliata);
			assertNull("Login effettuato con password sbagliata", u);
		}
		u = manager.verificaLogin(EMAIL_NEW_UTENTE, PASSWORD_NEW_UTENTE);
		assertNull("Login di un utente non nel DB", u);
	}
	
	@Test
	public void testRicercaUtente(){
		UtenteRegistrato u = null;
		for(int i=0; i<NUMERO_UTENTI_PREDEFINITI; i++){
			String email = EMAIL_UTENTI[i];
			u = manager.ricercaUtente(email);
			assertEquals("L'utente trovato non è quello cercato", u.getEmail(), email);
		}
		u = manager.ricercaUtente("EMAIL_NEW_UTENTE");
		assertNull("Trovato un utente non nel DB", u);
	}
	
	@Test
	public void testControlloMail(){
		for(int i=0; i<NUMERO_UTENTI_PREDEFINITI; i++){
			String email = EMAIL_UTENTI[i];
			Boolean flag = manager.controlloEmail(email);
			assertTrue("Un'email corretta non è stata accettata", flag);
		}
		Boolean flag = manager.controlloEmail(EMAIL_NEW_UTENTE);
		assertFalse("Un'email non corretta è stata accettata", flag);
	}
	
	@Test
	public void testEsisteMail(){
		for(int i=0; i<NUMERO_UTENTI_PREDEFINITI; i++){
			String email = EMAIL_UTENTI[i];
			Boolean flag = manager.controlloEmail(email);
			assertTrue("Email nel DB non riconosciuta", flag);
		}
		Boolean flag = manager.controlloEmail(EMAIL_NEW_UTENTE);
		assertFalse("Email non nel DB riconosciuta", flag);
	}
	
	@Test
	public void testAggiungiUtente(){
		manager.aggiungiUtente(EMAIL_NEW_UTENTE, PASSWORD_NEW_UTENTE, "testn", "testc");
		UtenteRegistrato u = manager.verificaLogin(EMAIL_NEW_UTENTE, PASSWORD_NEW_UTENTE);
		assertNotNull("Il nuovo utente non è stato inserito", u);
	}
	
	@Test
	public void testCambioPsw() throws SwimBeanException{
		String newPsw = "np";
		String oldPsw = "mr";
		String email = "mr@mail.it";
		manager.cambioPsw(email, newPsw, oldPsw);
		UtenteRegistrato u = manager.ricercaUtente(email);
		assertEquals("La password non è stata cambiata", u.getPsw(), newPsw);
		u = manager.ricercaUtente("rp@mail.it");
		assertEquals("La password è stata cambiata ad un utente sbagliato", u.getPsw(), "rp");
		u = manager.ricercaUtente("ms@mail.it");
		assertEquals("La password è stata cambiata ad un utente sbagliato", u.getPsw(), "ms");
		try{
			manager.cambioPsw(email, oldPsw, "pswSbagliata");
		} catch (SwimBeanException e) {}
		u = manager.ricercaUtente(email);
		assertEquals("La password è stata cambiata nonostante la vecchia password fosse sbagliata", u.getPsw(), newPsw);
	}
	
	@Test
	public void testCambioImm(){
		String newUrl = "www.imm.it";
		manager.cambioImm("rp@mail.it", newUrl);
		UtenteRegistrato u = manager.ricercaUtente("rp@mail.it");
		assertEquals("L'immagine non è stata cambiata", u.getUrl(), newUrl);
		u = manager.ricercaUtente("ms@mail.it");
		assertNull("L'immagine è stata cambiata ad un utente sbagliato", u.getUrl());
		u = manager.ricercaUtente("mr@mail.it");
		assertNull("L'immagine è stata cambiata ad un utente sbagliato", u.getUrl());
	}
	
	@Test
	public void testUtentePossiedeAbilita() throws SwimBeanException{
		UtenteRegistrato u = (UtenteRegistrato) manager.ricercaUtente("rp@mail.it");
		List<Abilita> elenco = (List<Abilita>) manAbil.getAbilitaUtente(u);
		for(int i=0; i<elenco.size(); i++){
			boolean flag = manager.utentePossiedeAbilita(u, elenco.get(i));
			assertTrue("L'utente non possiede un'abilità che dovrebbe possedere", flag);
		}
		elenco = (List<Abilita>) manAbil.getElencoAbilitaNonMie(u);
		for(int i=0; i<elenco.size(); i++){
			boolean flag = manager.utentePossiedeAbilita(u, elenco.get(i));
			assertFalse("L'utente possiede un'abilità che non dovrebbe possedere", flag);
		}
	}

}
