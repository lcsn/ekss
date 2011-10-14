package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NamedQueries(
		@NamedQuery(name=Credential.FIND_BY_IDENTITY_AND_PASS, query="select c from Credential c where identity=:identity and pass=:pass")
		)
@Entity
@Table(name="CredentialBean", uniqueConstraints = @UniqueConstraint(columnNames = "identity"))
public class Credential implements Serializable {

	private static final long serialVersionUID = 284068800839251475L;

	public static final String FIND_BY_IDENTITY_AND_PASS = "Credential.FIND_BY_IDENTITY_AND_PASS";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[A-Za-z ].*", message = "Nur Buchstaben und Leerzeichen erlaubt!")
	private String identity;

	@NotNull
	@Size(min = 1, max = 25)
	private String pass;
	
	@Transient
	private String pass2;
	
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
	
	public String getPass2() {
		return pass2;
	}

	public void setPass2(String pass2) {
		this.pass2 = pass2;
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

	public boolean verify() {
		if(pass.equals(pass2)) {
			return true;
		}
		return false;
	}
	
}
