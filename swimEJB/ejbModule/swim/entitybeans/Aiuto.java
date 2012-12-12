package swim.entitybeans;

import javax.persistence.*;

@Entity
@SequenceGenerator(name="seq",sequenceName="CUST_SEQ")
public class Aiuto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	private int id;
	private String tipo;
	private boolean conferma;
	private String feedback;
	
	@ManyToOne
	private UtenteRegistrato utFornisce;
	
	@ManyToOne
	private UtenteRegistrato utRiceve;
}
