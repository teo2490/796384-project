//-------------NON FUNZIONA IL CONTEXTUTIL!!!!!----------------

package swim.test;

import static org.junit.Assert.*;
import static swim.test.ManagerInizializzazioneDatabaseRemote.EMAIL_UTENTI;
import static swim.test.ManagerInizializzazioneDatabaseRemote.PASSWORD_UTENTI;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.junit.BeforeClass;
import org.junit.Test;

import swim.entitybeans.UtenteRegistrato;
import swim.sessionbeans.ManagerUtenteRegistratoRemote;
import swim.util.ContextUtil;

public class TestUtenteRegistrato {
	
	private static InitialContext ctx = null;
	private static ManagerUtenteRegistratoRemote manager = null;
	
	private static final String EMAIL_NEW_UTENTE = "test@email.it";
	private static final String PASSWORD_NEW_UTENTE = "test";
	private static final int INDICE_UTENTE_PREDEFINITO = 0;
	

	/*@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = ContextUtil.getInitialContext();
		manager = (ManagerUtenteRegistratoRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerUtenteRegistrato/remote"), ManagerUtenteRegistratoRemote.class);
		ManagerInizializzazioneDatabaseRemote db = (ManagerInizializzazioneDatabaseRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerInizializzazioneDatabase/remote"), ManagerInizializzazioneDatabaseRemote.class);
		db.pulisci();
		db.creaUtentiPredefiniti();
	}*/
	
	@Test
	public void testVerificaLogin(){
		//String email = EMAIL_UTENTI[INDICE_UTENTE_PREDEFINITO];
		//String psw = PASSWORD_UTENTI[INDICE_UTENTE_PREDEFINITO];
		String email = "user@user.it";
		String psw = "user";
		
		UtenteRegistrato u = manager.verificaLogin(email, psw);
		assertNotNull("L'utente deve esistere", u);
		assertEquals("L'utente restituito è diverso da quello richiesto", u.getEmail(), email);
		
		u = manager.verificaLogin(EMAIL_NEW_UTENTE, PASSWORD_NEW_UTENTE);
		assertNull("Non deve essere restituito un utente se non esiste", u);
	}

}
