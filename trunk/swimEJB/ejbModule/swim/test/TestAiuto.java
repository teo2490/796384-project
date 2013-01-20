package swim.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = ContextUtil.getInitialContext();
		manager = (ManagerAiutoRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerAiuto/remote"), ManagerAiutoRemote.class);
		manAbil = (ManagerAbilitaRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerAbilita/remote"), ManagerAbilitaRemote.class);
		manAdmin = (ManagerAmministratoreRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerAmministratore/remote"), ManagerAmministratoreRemote.class);
		manUser = (ManagerUtenteRegistratoRemote) PortableRemoteObject.narrow(ctx.lookup("ManagerUtenteRegistrato/remote"), ManagerUtenteRegistratoRemote.class);
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
		db.creaUtentiPredefiniti();
		db.creaAiutiPredefiniti(manUser.ricercaUtente("mr@mail.it"), manUser.ricercaUtente("rp@mail.it"));
		db.creaABILITA_UTENTE();
		db.creaAmiciziePredefinite(manUser.ricercaUtente("ms@mail.it"), manUser.ricercaUtente("rp@mail.it"));
		
		List<Abilita> elenco = (List<Abilita>) manAdmin.getElencoRichieste();
		for(int i=0; i<elenco.size(); i++){
			manAdmin.aggiungiAbilita(Integer.toString(elenco.get(i).getId()), elenco.get(i).getNome(), elenco.get(i).getDescrizione(), "admin1@swim.it");
		}
	}
	
	@Test
	public void testRicercaPerAbilita() throws SwimBeanException{
		List<Abilita> elenco = (List<Abilita>) manAbil.getElencoAbilita();
		for(int i=0; i<elenco.size(); i++){
			if(elenco.get(i).getNome().equals("C") || elenco.get(i).getNome().equals("Java")){
				List<String> elencoEmail = manager.ricercaPerAbilita(elenco.get(i));
				UtenteRegistrato u = manUser.ricercaUtente(elencoEmail.get(0));
				assertEquals("L'abilità è posseduta dall'utente sbagliato", "rp@mail.it", u.getEmail());
			} else if(elenco.get(i).getNome().equals("Verniciare")){
				List<String> elencoEmail = manager.ricercaPerAbilita(elenco.get(i));
				UtenteRegistrato u = manUser.ricercaUtente(elencoEmail.get(0));
				assertEquals("L'abilità è posseduta dall'utente sbagliato", "mr@mail.it", u.getEmail());
			} else {
				List<String> elencoEmail = null;
				try{
					elencoEmail = manager.ricercaPerAbilita(elenco.get(i));
				} catch (SwimBeanException e){}
				assertNull("Ci sono degli utenti con abilità che non dovrebbero avere", elencoEmail);
			}
		}
	}
	
	@Test
	public void testRicercaTraAmici() throws SwimBeanException{
		UtenteRegistrato u = manUser.ricercaUtente("ms@mail.it");
		List<Abilita> elenco = manAbil.getElencoAbilita();
		for(int i=0; i<elenco.size(); i++){
			if(elenco.get(i).getNome().equals("Java")){
				List<String> idUtenti = manager.ricercaTraAmici(elenco.get(i), u);
				assertNotNull("Non ha trovato nessuno con l'abilità Java", idUtenti.get(0));
				assertEquals("Java non è posseduta da rp@mail.it", "rp@mail.it", idUtenti.get(0));
			} else if(elenco.get(i).getNome().equals("C")){
				List<String> idUtenti = manager.ricercaTraAmici(elenco.get(i), u);
				assertNotNull("Non ha trovato nessuno con l'abilità C", idUtenti.get(0));
				assertEquals("C non è posseduta da rp@mail.it", "rp@mail.it", idUtenti.get(0));
			} else {
				List<String> idUtenti = null;
				try{
					idUtenti = manager.ricercaTraAmici(elenco.get(i), u);
				} catch (SwimBeanException e) {}
				assertNull("Trovata un abilità in un utente non amico", idUtenti);
			}
		}
	}
	
	@Test
	public void testInvioRichiestaAiutoAndGetElencoRichiesteAiutoRicevuteNonConfermate() throws SwimBeanException{
		String tipo = "Java";
		UtenteRegistrato richiedente = manUser.ricercaUtente("mr@mail.it");
		UtenteRegistrato richiesto = manUser.ricercaUtente("rp@mail.it");
		manager.invioRichiestaAiuto(tipo, richiedente, richiesto);
		List<Aiuto> elenco = (List<Aiuto>) manager.getElencoRichiesteAiutoRicevuteNonConfermate(richiesto);
		boolean flag = false;
		int cont = 0;
		for(int i=0; i<elenco.size(); i++){
			if(elenco.get(i).getTipo().equals(tipo)){
				flag = true;
				cont++;
			}
		}
		assertTrue("La richiesta non è stata inviata", flag);
		assertEquals("Sono arrivate più richieste in una", 1, cont);
	}
	
	@Test
	public void testConfermaRichiestaAndRicercaAiuto() throws SwimBeanException{
		UtenteRegistrato u = manUser.ricercaUtente("mr@mail.it");
		List<Aiuto> elenco = (List<Aiuto>) manager.getElencoRichiesteAiutoRicevuteNonConfermate(u);
		String id = null;
		Integer idd = null;
		for(int i=0; i<elenco.size(); i++){
			if(elenco.get(i).getTipo().equals("Verniciare")){
				idd = (Integer) elenco.get(i).getId();
				id = idd.toString();
				manager.confermaRichiesta(id);
				assertTrue("La richiesta non è stata confermata", manager.ricercaAiuto(id).getConferma());
			} else {
				idd = (Integer) elenco.get(i).getId();
				id = idd.toString();
				assertFalse("Sono state confermate richieste che non lo dovevano essere", manager.ricercaAiuto(id).getConferma());
			}	
		}
	}
	
	@Test
	public void testAggiungiFeedbackAndControlloFeedbackAndGetElencoRichiesteAiutoFatteConfermateSenzaFeedbackAndGetElencoRichiesteAiutoRicevuteConfermateConFeedback() throws SwimBeanException{
		UtenteRegistrato richiedente = manUser.ricercaUtente("rp@mail.it");
		UtenteRegistrato richiesto = manUser.ricercaUtente("mr@mail.it");
		List<Aiuto> elenco = (List<Aiuto>) manager.getElencoRichiesteAiutoFatteConfermateSenzaFeedback(richiedente);
		List<Aiuto> elencoCF = null;
		String feedback = "Ottima prestazione";
		String fbRecuperato = null;
		for(int i=0; i<elenco.size(); i++){
			if(elenco.get(i).getTipo().equals("Verniciare")){
				manager.aggiungiFeedback(elenco.get(i), feedback);
			}
		}
		try{
			elencoCF = (List<Aiuto>) manager.getElencoRichiesteAiutoRicevuteConfermateConFeedback(richiesto);
		} catch (SwimBeanException e) {}
		for(int i=0; i<elenco.size(); i++){
			if(elencoCF.get(i).getTipo().equals("Verniciare")){
				try{
					fbRecuperato = manager.controlloFeedback(elencoCF.get(i));
				} catch (SwimBeanException e) {}
				assertNotNull("Non è stato inserito alcun feedback", fbRecuperato);
				assertEquals("Il feedback non è quello inserito", feedback, fbRecuperato);
			} else {
				try{
					fbRecuperato = manager.controlloFeedback(elencoCF.get(i));
				} catch (SwimBeanException e) {}
				assertNull("Sono stati modificati feedback di altri aiuti", fbRecuperato);
			}	
		}
	}
	
	@Test
	public void testEliminaAiutoAndGetElencoRichiesteAiutoRicevuteConfermate() throws SwimBeanException{
		UtenteRegistrato u = manUser.ricercaUtente("rp@mail.it");
		List<Aiuto> elenco = (List<Aiuto>) manager.getElencoRichiesteAiutoRicevuteNonConfermate(u);
		List<Aiuto> newElenco = new ArrayList<Aiuto>();
		for(int i=0; i<elenco.size(); i++){
			if(elenco.get(i).getTipo().equals("Java")){
				manager.eliminaAiuto(elenco.get(i));
			}
		}
		try{
			newElenco = (List<Aiuto>) manager.getElencoRichiesteAiutoRicevuteNonConfermate(u);
		} catch (SwimBeanException e) {}
		boolean flag = false;
		if(!newElenco.isEmpty()){
			for(int i=0; i<newElenco.size(); i++){
				if(newElenco.get(i).getTipo().equals("Java")){
					flag = true;
				}
			}
		}
		assertFalse("L'aiuto con tipo Java non è stato cancellato", flag);
	}
	
	@Test
	public void testGetElencoFeedbackUtente(){
		UtenteRegistrato u = manUser.ricercaUtente("mr@mail.it");
		List<String> elenco = manager.getElencoFeedbackUtente(u);
		assertEquals("C'è più di un feedback", 1, elenco.size());
		assertEquals("Il feedback non è quello aspettato", "Ottima prestazione", elenco.get(0));
	}

}
