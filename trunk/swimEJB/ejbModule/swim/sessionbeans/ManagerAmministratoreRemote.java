package swim.sessionbeans;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import swim.entitybeans.Abilita;
import swim.entitybeans.Amministratore;

@Remote
public interface ManagerAmministratoreRemote {
	
	public Amministratore verificaLogin(String email, String password);
	//public void logout();
	public List<Abilita> getElencoRichieste();
	public void aggiungiAbilita();
	public void eliminaAbilita();
	public void cambiaDatiAbilita(String nome, String descr);

}
