package swim.entitybeans;

import javax.persistence.*;

@Entity
public class Amicizia {
	
	@ManyToOne
	private UtenteRegistrato utRichiedente;
	
	@ManyToOne
	private UtenteRegistrato utRichiesto;
	
	@Id
	@Column(name="Email_Richiedente")
	private String emailRichiedente = utRichiedente.getEmail();
	@Id
	@Column(name="Email_Richiesto")
	private String emailRichiesto = utRichiesto.getEmail();
	private boolean conferma = false;
	
	public String getEmailRichiedente(){
		return utRichiedente.getEmail();
	}
	
	public String getEmailRichiesto(){
		return utRichiesto.getEmail();
	}
	
	public void switchConferma(){
		this.conferma = true;
	}
	
	public UtenteRegistrato getRichiedente(){
		return utRichiedente;
	}
	
	public UtenteRegistrato getRichisto(){
		return utRichiesto;
	}
	
	public void setRichiedente(UtenteRegistrato richiedente){
		this.utRichiedente = richiedente;
	}
	
	public void setRichiesto(UtenteRegistrato richiesto){
		this.utRichiesto = richiesto;
	}

}
