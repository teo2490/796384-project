package swim.entitybeans;

import java.util.Date;

import javax.persistence.*;

@Entity
//@SequenceGenerator(name="seq",sequenceName="CUST_SEQ")
public class Aiuto {
	
	@Id
	@Temporal(TemporalType.TIMESTAMP)
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	private /*int*/Date id;
	private String tipo;
	private boolean conferma = false;
	private String feedback;
	
	@ManyToOne
	private UtenteRegistrato utFornisce;
	
	@ManyToOne
	private UtenteRegistrato utRiceve;
	
	public /*int*/Date getID(){
		return id;
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
}