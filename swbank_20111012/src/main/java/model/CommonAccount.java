package model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlRootElement;

import util.AccountType;

/**
 * TagesgeldKonto
 * @author lars
 */
@XmlRootElement
@Entity(name="CommonAccountBean")
public class CommonAccount extends Account {

	private static final long serialVersionUID = 1944743466524276321L;

	@Enumerated(EnumType.STRING)
	private AccountType type = AccountType.COMMONACCOUNT;

	private BigDecimal interest = new BigDecimal("1.80");
	
	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

}
