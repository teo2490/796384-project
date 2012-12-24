package swim.sessionbeans;

import swim.entitybeans.Aiuto;
import swim.entitybeans.UtenteRegistrato;

import javax.ejb.Remote;

import java.util.Set;

@Remote
public interface ManagerAiutoRemote {
	
	public Set<UtenteRegistrato> ricercaPerAbilita(String abilita) throws SwimBeanException;
	public void invioRichiestaAiuto(String tipo, UtenteRegistrato richiedente, UtenteRegistrato richiesto);
	public void confermaRichiesta();
	public String controlloFeedback() throws SwimBeanException;
	public void aggiungiFeedback(String testo);
	public Set<Aiuto> getElencoRichiesteAiuto(UtenteRegistrato utente) throws SwimBeanException;

}
