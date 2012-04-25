package model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import model.entity.common.AbstractSWBankEntity;

@SuppressWarnings("serial")
@NamedQueries({
		@NamedQuery(name=Address.FIND_BY_USER , query="select a from Address a where a.user=:user"),
		@NamedQuery(name=Address.FIND_DEFAULTADDRESS_BY_USER , query="select a from Address a where a.user=:user and a.defaultAddress=true")
	})
@Entity
@Table(name="addressbean")
public class Address extends AbstractSWBankEntity {

	public static final String FIND_BY_USER = "Address.FIND_BY_USER";
	public static final String FIND_DEFAULTADDRESS_BY_USER = "Address.FIND_DEFAULTADDRESS_BY_USER";

	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="userId")
	private User user;
	
	@NotNull
	private String street;
	
	@NotNull
	private String city;

	@NotNull
	private String zipcode;
	
	private boolean defaultAddress = false;

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
	
	public boolean isDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	@Override
	public String toString() {
		return this.street + "\n" + this.zipcode + " " + this.city;
	}
	
}
