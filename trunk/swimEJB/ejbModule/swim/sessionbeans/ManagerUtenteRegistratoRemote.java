package swim.sessionbeans;

import swim.entitybeans.UtenteRegistrato;

import javax.ejb.Remote;

@Remote
public interface ManagerUtenteRegistratoRemote {
	
	public UtenteRegistrato verificaLogin(String email, String password);
	public UtenteRegistrato ricercaUtente(String email);
	public boolean controlloEmail(String email);
	public boolean esisteMail(String email);
	public void aggiungiUtente(String email,String password, String nome, String cognome);
	public void cambioPsw(String email, String newPsw, String oldPsw) throws SwimBeanException;
	public void cambioImm(String email, String url);
	//public void logout();
}
