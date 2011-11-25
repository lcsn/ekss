package model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlRootElement;

import util.AccountType;
import util.ContractDuration;

/**
 * Festzinskonto
 * @author lars
 */
@XmlRootElement
@Entity(name="SmartAccountBean")
public class SmartAccount extends Account {

	private static final long serialVersionUID = -1239396368412292474L;

	@Enumerated(EnumType.STRING)
	private AccountType type = AccountType.SMARTACCOUNT;

	@Enumerated(EnumType.STRING)
	private ContractDuration duration = ContractDuration.ONE_YEAR;
	
	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public ContractDuration getDuration() {
		return duration;
	}

	public void setDuration(ContractDuration duration) {
		this.duration = duration;
	}

}