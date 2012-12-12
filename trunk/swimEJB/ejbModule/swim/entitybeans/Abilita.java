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
	private String Nome;
	private boolean conferma;
	private String Descrizione;
	
	@ManyToOne
	private Amministratore admin;
	
	@ManyToMany(mappedBy="abilita")
	private List<UtenteRegistrato> utente;

}
