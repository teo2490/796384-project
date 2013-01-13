package swim.entitybeans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;

import org.hibernate.validator.Size;

public class AmiciziaPK implements Serializable{
	
	private UtenteRegistrato utRichiedente;
	private UtenteRegistrato utRichiesto;
	
	public UtenteRegistrato getUtenteRichiedente(){
		return this.utRichiedente;
	}
	
	public void setUtenteRichiedente(UtenteRegistrato u){
		this.utRichiedente = u;
	}
	
	public UtenteRegistrato getUtenteRichiesto(){
		return this.utRichiesto;
	}
	
	public void setUtenteRichiesto(UtenteRegistrato u){
		this.utRichiesto = u;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((utRichiedente == null) ? 0 : utRichiedente
						.hashCode());
		result = prime * result
				+ ((utRichiesto == null) ? 0 : utRichiesto.hashCode());
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
		AmiciziaPK other = (AmiciziaPK) obj;
		if (utRichiedente == null) {
			if (other.utRichiedente != null)
				return false;
		} else if (!utRichiedente.equals(other.utRichiedente))
			return false;
		if (utRichiesto == null) {
			if (other.utRichiesto != null)
				return false;
		} else if (!utRichiesto.equals(other.utRichiesto))
			return false;
		return true;
	}
	
	

}
