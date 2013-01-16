package swim.entitybeans;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Aiuto implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String tipo;
	private boolean conferma = false;
	private String feedback;
	
	@ManyToOne
	private UtenteRegistrato utFornisce;
	
	@ManyToOne
	private UtenteRegistrato utRiceve;
	
	public int getId(){
		return id;
	}
	
	public boolean getConferma(){
		return this.conferma;
	}
	
	public String getTipo(){
		return tipo;
	}
	
	public void setTipo(String tipo){
		this.tipo=tipo;
	}
	
	public void switchConferma(){
		this.conferma=true;
	}
	
	public String getFeedback(){
		return feedback;
	}
	
	public void setFeedback(String feedback){
		this.feedback=feedback;
	}
	
	public void setUtFornisce(UtenteRegistrato richiesto){
		this.utFornisce = richiesto;
	}
	
	public void setUtRiceve(UtenteRegistrato richiedente){
		this.utRiceve = richiedente;
	}
	
	public UtenteRegistrato getRichiedente(){
		return this.utRiceve;
	}
	
	public UtenteRegistrato getRichiesto(){
		return this.utFornisce;
	}
	
}
