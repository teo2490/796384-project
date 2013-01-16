package swim.sessionbeans;

import swim.entitybeans.Abilita;
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
	public boolean utentePossiedeAbilita(UtenteRegistrato u, Abilita a);
	
	//Parte commentata perché riguardante l'immagine del profilo (possibile aggiungere in seguito)
	//public void cambioImm(String email, String url);

}
