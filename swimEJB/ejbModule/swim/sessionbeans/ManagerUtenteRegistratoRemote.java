package swim.sessionbeans;

import swim.entitybeans.UtenteRegistrato;

import javax.ejb.Remote;

@Remote
public interface ManagerUtenteRegistratoRemote {
	
	public UtenteRegistrato verificaLogin(String email, String password);
	public boolean controlloEmail(String email);
	public void cambioPsw(String newPsw, String oldPsw) throws SwimBeanException;
	public void cambioImm(String url);
	public void logout();

}
