package swim.entitybeans;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Amministratore implements Serializable{

	@Id
	private String email;
	private String password;
	
	@OneToMany(mappedBy="admin")
	private Set<Abilita> declAbil;

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
	
}
