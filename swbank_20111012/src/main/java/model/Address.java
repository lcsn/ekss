package model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamedQueries(
		@NamedQuery(name=Address.FIND_BY_USER , query="select a from Address a where a.user=:user")
		)
@Entity
@Table(name="AddressBean")
public class Address implements Serializable {

	private static final long serialVersionUID = -1020692856549419032L;

	public static final String FIND_BY_USER = "Address.FIND_BY_USER";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="userId")
	private User user;
	
	@NotNull
	private String street;
	
	@NotNull
	private String city;

	@NotNull
	private String zipcode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
