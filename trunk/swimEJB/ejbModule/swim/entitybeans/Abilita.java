package swim.entitybeans;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Abilita implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="A_ID")
	private int id;
	private String nome;
	private boolean conferma = false;
	private String descrizione;
	
	@ManyToOne
	private Amministratore admin;
	
	@ManyToMany(mappedBy="abilita")
	private Set<UtenteRegistrato> utente;
	
	public int getId(){
		return this.id;
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public String getDescrizione(){
		return this.descrizione;
	}
	
	public boolean getConferma(){
		return this.conferma;
	}
	
	public Amministratore getAdmin(){
		return this.admin;
	}
	
	public void setNome(String nome){
		this.nome = nome;
	}
	
	public void setDescrizione(String descr){
		this.descrizione = descr;
	}
	
	public void switchConferma(){
		this.conferma = true;
	}
	
	public void setAmministratore(Amministratore admin){
		this.admin = admin;
	}

}
