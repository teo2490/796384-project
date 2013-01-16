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
	public void aggiungiFeedback(Aiuto at, String feedback);
	public void eliminaAiuto(Aiuto at);
	public List<String> getElencoFeedbackUtente(UtenteRegistrato u);
	public List<Aiuto> getElencoRichiesteAiutoRicevuteConfermateConFeedback(UtenteRegistrato utente) throws SwimBeanException;
	public List<Aiuto> getElencoRichiesteAiutoFatteConfermateSenzaFeedback(UtenteRegistrato utente) throws SwimBeanException;
	public List<Aiuto> getElencoRichiesteAiutoRicevuteNonConfermate(UtenteRegistrato utente) throws SwimBeanException;
	public List<Aiuto> getElencoRichiesteAiutoRicevuteConfermate(UtenteRegistrato utente) throws SwimBeanException;
	public Abilita ricercaAbilita(String id); //Portata anche qui per non dover usare il manager delle abilità  nelle servlet di aiuto
	public Aiuto ricercaAiuto(String id);
	
	//Possibile implementarle in seguito qualora dovessero servire
	/*public List<Aiuto> getElencoRichiesteAiutoFatteNonConfermate(UtenteRegistrato utente) throws SwimBeanException;
	public List<Aiuto> getElencoRichiesteAiutoFatteConfermate(UtenteRegistrato utente) throws SwimBeanException;*/
	
}
