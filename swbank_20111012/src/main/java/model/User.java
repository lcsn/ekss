package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import util.Role;

@NamedQueries({
		@NamedQuery(name=User.FIND_BY_CREDENTIALS, query="select u from User u where u.credentials=:credentials"),
		@NamedQuery(name=User.FIND_BY_ID, query="select u from User u where u.id=:userId"),
		@NamedQuery(name=User.FIND_USERS, query="select u from User u")
		})
@Entity
@Table(name="UserBean", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements Serializable {
	
	private static final long serialVersionUID = -8885764866253995330L;

	public static final String FIND_BY_CREDENTIALS = "User.FIND_BY_CREDENTIALS";
	public static final String FIND_BY_ID = "User.FIND_BY_ID";
	public static final String FIND_USERS = "User.FIND_CUSTOMER";
	
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
	private Role role = Role.CUSTOMER;
	
//	@Past
	@NotNull
	@NotEmpty
//	@Temporal(TemporalType.DATE)
	private String birthday;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="credentialId")
	private Credential credentials;

	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private List<Account> accounts;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private List<Address> addresses;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private List<Transaction> transactions;
	
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

	@XmlTransient
	public Credential getCredentials() {
		return credentials;
	}

	public void setCredentials(Credential credentials) {
		this.credentials = credentials;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@XmlTransient
	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	@XmlTransient
	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	@XmlTransient
	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public String toString() {
		return this.firstname + ", " + this.lastname;
	}
	
}
