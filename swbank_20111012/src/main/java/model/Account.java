package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

@NamedQueries({
	@NamedQuery(name=Account.FIND_BY_ACCOUNTNUMBER, query="select a from Account a where a.accountNumber=:accountNumber"),
	@NamedQuery(name=Account.FIND_BY_ID, query="select a from Account a where a.id=:accountId")
	})
@Entity
@Table(name="AccountBean", uniqueConstraints = @UniqueConstraint(columnNames = "accountNumber"))
public class Account implements Serializable {
	
	private static final long serialVersionUID = 5008343162571576351L;

	public static final String FIND_BY_ACCOUNTNUMBER = "Account.FIND_BY_ACCOUNTNUMBER";
	public static final String FIND_BY_ID = "Account.FIND_BY_ID";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@NotNull
	@Size(min = 8, max = 8, message="BLZ hat die Länge 8")
	private String bankCode;

	@NotEmpty
	@NotNull
	@Size(min = 9, max = 9, message="Kontonummer hat die Länge 9")
	private String accountNumber;
	
	@NotEmpty
	@NotNull
	private BigDecimal amount;
	
	@Temporal(TemporalType.DATE)
	private Date lastDebit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getLastDebit() {
		return lastDebit;
	}

	public void setLastDebit(Date lastDebit) {
		this.lastDebit = lastDebit;
	}
	
}
