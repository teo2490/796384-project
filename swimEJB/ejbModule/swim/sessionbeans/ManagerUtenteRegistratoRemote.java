package swim.sessionbeans;

import swim.entitybeans.UtenteRegistrato;

import javax.ejb.Remote;

@Remote
public interface ManagerUtenteRegistratoRemote {
	
	public UtenteRegistrato verificaLogin(String email, String password);
	public boolean controlloEmail(String email);
	public boolean esisteMail(String email);
	public void aggiungiUtente(String email,String password, String nome, String cognome);
	public void cambioPsw(String newPsw, String oldPsw) throws SwimBeanException;
	public void cambioImm(String url);
	public void logout();
}
