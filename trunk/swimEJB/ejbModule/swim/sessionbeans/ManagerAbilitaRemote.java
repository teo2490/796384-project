package swim.sessionbeans;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import swim.entitybeans.Abilita;
import swim.entitybeans.UtenteRegistrato;

@Remote
public interface ManagerAbilitaRemote {
	
	public List<Abilita> getElencoAbilita();
	public List<Abilita> getAbilitaUtente(UtenteRegistrato utente) throws SwimBeanException;
	public List<Abilita> getElencoAbilitaNonMie(UtenteRegistrato utente);
	public void aggiungiAbilita(UtenteRegistrato utente);
	public void invioRichiestaNuovaAbilita(String nome, String descrizione);

}
