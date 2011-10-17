package model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import util.LoginRole;

@NamedQueries({
		@NamedQuery(name=User.FIND_BY_CREDENTIALS, query="select u from User u where u.credentials=:credentials"),
		@NamedQuery(name=User.FIND_BY_ID, query="select u from User u where u.id=:userId")
		})
@Entity
@Table(name="UserBean", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements Serializable {
	
	private static final long serialVersionUID = -8885764866253995330L;

	public static final String FIND_BY_CREDENTIALS = "User.FIND_BY_CREDENTIALS";
	public static final String FIND_BY_ID = "User.FIND_BY_ID";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 1, max = 25, message="max. 25 Buchstaben")
	@Pattern(regexp = "[A-Za-z ]*", message = "Nur Buchstaben und Leerzeichen erlaubt!")
	private String firstname;
	
	@NotNull
	@Size(min = 1, max = 25, message="max. 25 Buchstaben")
	@Pattern(regexp = "[A-Za-z ]*", message = "Nur Buchstaben und Leerzeichen erlaubt!")
	private String lastname;

	@NotNull
	@NotEmpty
	@Email
	private String email;
	
	@Enumerated(EnumType.STRING)
	private LoginRole role = LoginRole.CUSTOMER;
	
	/**
	 * String, sonst Konverter für Date-Komponente in Primefaces schreiben.
	 */
	@NotNull
	@NotEmpty
	private String birthday;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="credentialId")
	private Credential credentials;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Credential getCredentials() {
		return credentials;
	}

	public void setCredentials(Credential credentials) {
		this.credentials = credentials;
	}

	public LoginRole getLoginRole() {
		return role;
	}

	public void setLoginRole(LoginRole role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {
		return this.firstname + ", " + this.lastname;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
}
