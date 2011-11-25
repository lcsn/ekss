package model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlRootElement;

import util.AccountType;

/**
 * Girokonto
 * @author lars
 */
@XmlRootElement
@Entity(name="GiroAccountBean")
public class GiroAccount extends Account {

	private static final long serialVersionUID = -7770931531163003341L;

	@Enumerated(EnumType.STRING)
	private AccountType type = AccountType.GIROACCOUNT;
	
	private BigDecimal dispo = new BigDecimal("1000.00");

//	Zinsen
	private BigDecimal dispoInterest = new BigDecimal("14.50");
	
	private BigDecimal dispoOverdrawInterest = new BigDecimal("21.50");

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public BigDecimal getDispo() {
		return dispo;
	}

	public void setDispo(BigDecimal dispo) {
		this.dispo = dispo;
	}

	public BigDecimal getDispoInterest() {
		return dispoInterest;
	}

	public void setDispoInterest(BigDecimal dispoInterest) {
		this.dispoInterest = dispoInterest;
	}

	public BigDecimal getDispoOverdrawInterest() {
		return dispoOverdrawInterest;
	}

	public void setDispoOverdrawInterest(BigDecimal dispoOverdrawInterest) {
		this.dispoOverdrawInterest = dispoOverdrawInterest;
	}

}
