package swim.entitybeans;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UtenteRegistrato {
	
	@Id
	private String email;
	private String password;
	private String nome;
	private String cognome;
	private String url;

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
}
