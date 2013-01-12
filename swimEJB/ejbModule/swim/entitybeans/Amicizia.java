package swim.entitybeans;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@IdClass(AmiciziaPK.class)
public class Amicizia implements Serializable{
	
	@Id
	@ManyToOne
	private UtenteRegistrato utRichiedente;
	
	@Id
	@ManyToOne
	private UtenteRegistrato utRichiesto;
	
	/*@Id
	@Column(name="Email_Richiedente")
	private String emailRichiedente = utRichiedente.getEmail();
	@Id
	@Column(name="Email_Richiesto")
	private String emailRichiesto = utRichiesto.getEmail();*/
	
	private boolean conferma;
	
	/*public String getEmailRichiedente(){
		return utenteRichiedente.getEmail();
	}
	
	public String getEmailRichiesto(){
		return utenteRichiesto.getEmail();
	}*/
	
	public void switchConferma(){
		this.conferma = true;
	}
	
	public UtenteRegistrato getRichiedente(){
		return utRichiedente;
	}
	
	public UtenteRegistrato getRichiesto(){
		return utRichiesto;
	}
	
	public void setRichiedente(UtenteRegistrato u){
		this.utRichiedente = u;
	}
	
	public void setRichiesto(UtenteRegistrato u){
		this.utRichiesto = u;
	}

}
