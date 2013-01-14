package swim.sessionbeans;

import swim.entitybeans.Abilita;
import swim.entitybeans.Aiuto;
import swim.entitybeans.UtenteRegistrato;

import javax.ejb.Remote;

import java.util.List;
import java.util.Set;

@Remote
public interface ManagerAiutoRemote {
	
	public List<String> ricercaPerAbilita(Abilita abilita) throws SwimBeanException;
	public List<String> ricercaTraAmici(Abilita abilita, UtenteRegistrato utente) throws SwimBeanException;
	public void invioRichiestaAiuto(String tipo, UtenteRegistrato richiedente, UtenteRegistrato richiesto);
	public void confermaRichiesta(String id);
	public String controlloFeedback(Aiuto aiuto) throws SwimBeanException;
	public void aggiungiFeedback(String testo);
	public List<String> getElencoFeedbackUtente(UtenteRegistrato u);
	public List<Aiuto> getElencoRichiesteAiutoFatteNonConfermate(UtenteRegistrato utente) throws SwimBeanException;
	public List<Aiuto> getElencoRichiesteAiutoFatteConfermate(UtenteRegistrato utente) throws SwimBeanException;
	public List<Aiuto> getElencoRichiesteAiutoRicevuteNonConfermate(UtenteRegistrato utente) throws SwimBeanException;
	public List<Aiuto> getElencoRichiesteAiutoRicevuteConfermate(UtenteRegistrato utente) throws SwimBeanException;
	public Abilita ricercaAbilita(String id);
	public Aiuto ricercaAiuto(String id);
	
}
