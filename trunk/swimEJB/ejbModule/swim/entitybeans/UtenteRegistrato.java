package swim.entitybeans;

import java.util.Set;

import javax.persistence.*;

@Entity
public class UtenteRegistrato {
	
	@Id
	@Column(name="U_ID")
	private String email;
	private String password;
	private String nome;
	private String cognome;
	private String url;
	
	//Aiuti forniti
	@OneToMany(mappedBy="utFornisce")
	private Set<Aiuto> sHelp;
	
	//Aiuti ricevuti e richieste di amicizia
	@OneToMany(mappedBy="utRiceve")
	private Set<Aiuto> rHelp;
	
	//Abilità possedute DA SISTEMARE
	@ManyToMany
	@JoinTable(
		    name="EJB_ROSTER_TEAM_PLAYER",
		    joinColumns=
		        @JoinColumn(name="UtenteRegistrato_ID", referencedColumnName="U_ID"),
		    inverseJoinColumns=
		        @JoinColumn(name="Abilita_ID", referencedColumnName="A_ID")
		)
	private Set<Abilita> abilita;


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPsw() {
		return password;
	}

	public void setPsw(String psw) {
		this.password = psw;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UtenteRegistrato other = (UtenteRegistrato) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
}