package model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import model.entity.common.AbstractSWBankEntity;

import org.hibernate.annotations.IndexColumn;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import util.security.PasswordService;

@SuppressWarnings("serial")
@NamedQueries({
		@NamedQuery(name=User.FIND_ALL, query="select u from User u"),
		@NamedQuery(name=User.FIND_BY_ID, query="select u from User u where u.id=:userId"),
		@NamedQuery(name=User.FIND_BY_EMAIL, query="select u from User u where u.email=:email"),
		@NamedQuery(name=User.FIND_BY_USERNAME, query="select u from User u where u.username=:username")
		})
@Entity
@Table(name = "userbean", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User extends AbstractSWBankEntity {
	
	public static final String FIND_BY_ID = "User.FIND_BY_ID";
	public static final String FIND_BY_EMAIL = "User.FIND_BY_EMAIL";
	public static final String FIND_BY_USERNAME = "User.FIND_BY_USERNAME";
	public static final String FIND_ALL = "User.FIND_ALL";
	
	@NotNull
	@Size(min = 1, max = 25, message="max. 25 Buchstaben")
	@Pattern(regexp = "[A-Za-z- ]*", message = "Nur Buchstaben und Leerzeichen erlaubt!")
	private String firstname;
	
	@NotNull
	@Size(min = 1, max = 25, message="max. 25 Buchstaben")
	@Pattern(regexp = "[A-Za-z- ]*", message = "Nur Buchstaben und Leerzeichen erlaubt!")
	private String lastname;

	@NotNull
	@NotEmpty
	@Email
	private String email;

//	@IndexColumn(name="id")
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Role> roles = new ArrayList<Role>();
	
	@Past
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	@NotNull
	@Size(min = 3, max = 25, message="Benutzername muss mind. 3 und max. 25 Zeichen haben!")
	@Pattern(regexp = "[A-Za-z-._ ]*", message = "Nur Buchstaben und Leerzeichen erlaubt!")
	private String username;
	
	@NotNull
	@Size(min = 6, message="Passwort muss mind. 6 Zeichen haben!")
	private String pass;
	
	@Transient
	private String pass2;
	
	private boolean confirmed = false;
	
//	@IndexColumn(name="id")
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Address> addresses = new ArrayList<Address>();
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = PasswordService.encrypt(pass);
	}

	public String getPass2() {
		return pass2;
	}

	public void setPass2(String pass2) {
		this.pass2 = PasswordService.encrypt(pass2);
	}

	public String toString() {
		return this.firstname + " " + this.lastname;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void addRole(util.Role role) {
		Role _role = new Role(role.toString());
		roles.add(_role);
		_role.setUser(this);
	}

//	@SuppressWarnings("unused")
//	@PrePersist
//	private void encrypt() {
//		this.pass = PasswordService.encrypt(pass);
//	}
	
	public boolean verify() {
		if (pass.equals(pass2)) {
			return true;
		}
		return false;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
}
