package swim.sessionbeans;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import swim.entitybeans.Abilita;
import swim.entitybeans.Amministratore;

@Remote
public interface ManagerAmministratoreRemote {
	
	public Amministratore verificaLogin(String email, String password);
	public Amministratore cercaAdmin(String email);
	//public void logout();
	public List<Abilita> getElencoRichieste();
	public Abilita cercaAbilita(int id);
	public void aggiungiAbilita(String id, String nome, String desc, String email);
	public void eliminaAbilita(Abilita abilita);
	//public void cambiaDatiAbilita(String nome, String descr);


}
