package swim.sessionbeans;

import swim.entitybeans.Amicizia;
import swim.entitybeans.UtenteRegistrato;

import javax.ejb.Remote;

import java.util.Set;

@Remote
public interface ManagerAmiciziaRemote {
	
	public void invioRichiestaAmicizia(UtenteRegistrato richiedente, UtenteRegistrato richiesto) throws SwimBeanException;
	public Set<UtenteRegistrato> getElecoAmici(UtenteRegistrato utente);
	public Set<Amicizia> getElencoRichiesteAmicizia(UtenteRegistrato utente) throws SwimBeanException;
	public void accettaAmicizia();

}
