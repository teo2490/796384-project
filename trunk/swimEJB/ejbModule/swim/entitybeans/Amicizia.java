package swim.entitybeans;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Amicizia implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	private UtenteRegistrato utRichiedente;
	
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
	

	public int getId(){
		return this.id;
	}
	
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
