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
	
	private boolean conferma;
	
	public int getId(){
		return this.id;
	}
	
	public void switchConferma(){
		this.conferma = true;
	}
	
	public boolean getConferma(){
		return this.conferma;
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
