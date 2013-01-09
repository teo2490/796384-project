//-------------NON FUNZIONA IL CONTEXTUTIL!!!!!----------------

package swim.test;

import static org.junit.Assert.*;
import static swim.test.ManagerInizializzazioneDatabaseRemote.EMAIL_UTENTI;
import static swim.test.ManagerInizializzazioneDatabaseRemote.PASSWORD_UTENTI;

import java.io.IOException;
import java.io.NotSerializableException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.RequestDispatcher;

import org.jboss.remoting.InvocationFailureException;
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
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = ContextUtil.getInitialContext();
		manager = (ManagerUtenteRegistratoRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerUtenteRegistrato/remote"), ManagerUtenteRegistratoRemote.class);
		ctx = ContextUtil.getInitialContext();
		ManagerInizializzazioneDatabaseRemote db = (ManagerInizializzazioneDatabaseRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerInizializzazioneDatabase/remote"), ManagerInizializzazioneDatabaseRemote.class);
		//db.pulisci();
		db.creaUtentiPredefiniti();
	}
	
	@Test
	public void testVerificaLogin() throws InvocationFailureException{
		//String email = EMAIL_UTENTI[INDICE_UTENTE_PREDEFINITO];
		//String psw = PASSWORD_UTENTI[INDICE_UTENTE_PREDEFINITO];
		String email = "mr@email.it";
		String psw = "mrp";
		UtenteRegistrato u = manager.verificaLogin(email, psw);
		assertNotNull("L'utente deve esistere", u);
		/*try {
			Object obj = ContextUtil.getInitialContext().lookup("ManagerUtenteRegistrato/remote");
			ManagerUtenteRegistratoRemote manager = (ManagerUtenteRegistratoRemote) PortableRemoteObject.narrow(obj, ManagerUtenteRegistratoRemote.class);
			u = manager.verificaLogin(email, psw);
			assertNotNull("L'utente deve esistere", u);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		assertNotNull("L'utente deve esistere", u);*/
		/*u = manager.verificaLogin(EMAIL_NEW_UTENTE, PASSWORD_NEW_UTENTE);
		assertNull("Non deve essere restituito un utente se non esiste", u);*/
	}

}
