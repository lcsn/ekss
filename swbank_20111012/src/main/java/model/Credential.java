package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="CredentialBean", uniqueConstraints = @UniqueConstraint(columnNames = "identity"))
public class Credential implements Serializable {

	private static final long serialVersionUID = 284068800839251475L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[A-Za-z ]*", message = "Nur Buchstaben und Leerzeichen erlaubt!")
	private String identity;

	@NotNull
	@Size(min = 1, max = 25)
	private String pass;
	
	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		boolean sEquals = super.equals(obj);
		Credential c = (Credential) obj;
		if(this.identity.equals(c.getIdentity()) && this.pass.equals(c.getPass())) {
			return true;
		}
		return sEquals;
	}
}
