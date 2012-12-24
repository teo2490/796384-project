package swim.entitybeans;

import java.util.List;

import javax.persistence.*;

@Entity
@SequenceGenerator(name="seq",sequenceName="CUST_SEQ")
public class Abilita {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	@Column(name="A_ID")
	private int id;
	private String nome;
	private boolean conferma = false;
	private String descrizione;
	
	@ManyToOne
	private Amministratore admin;
	
	@ManyToMany(mappedBy="abilita")
	private List<UtenteRegistrato> utente;
	
	public String getNome(){
		return this.nome;
	}
	
	public String getDescrizioni(){
		return this.descrizione;
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
