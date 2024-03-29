package swim.sessionbeans;

import swim.entitybeans.Amicizia;
import swim.entitybeans.UtenteRegistrato;

import javax.ejb.Remote;

import java.util.List;

@Remote
public interface ManagerAmiciziaRemote {
	
	public void invioRichiestaAmicizia(UtenteRegistrato richiedente, UtenteRegistrato richiesto);
	public List<UtenteRegistrato> getElencoAmici(UtenteRegistrato utente) throws SwimBeanException;
	public List<Amicizia> getElencoRichiesteAmiciziaRicevute(UtenteRegistrato utente) throws SwimBeanException;
	public List<Amicizia> getElencoRichiesteAmiciziaInviate(UtenteRegistrato utente) throws SwimBeanException;
	public void accettaAmicizia(String id);
	public boolean esisteAmicizia(UtenteRegistrato u1, UtenteRegistrato u2);
	public Amicizia ricercaAmicizia(String id);

}
