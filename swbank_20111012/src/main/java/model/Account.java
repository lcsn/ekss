package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import util.AccountType;

@NamedQueries({
	@NamedQuery(name=Account.FIND_BY_ACCOUNTNUMBER, query="select a from Account a where a.accountNumber=:accountNumber"),
	@NamedQuery(name=Account.FIND_BY_ACCOUNTNUMBER_AND_BANKCODE, query="select a from Account a where a.accountNumber=:accountNumber and a.bankCode=:bankCode"),
	@NamedQuery(name=Account.FIND_BY_ID, query="select a from Account a where a.id=:accountId"),
	@NamedQuery(name=Account.FIND_BY_USER, query="select a from Account a where a.user=:user")
	})
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="AccountBean", uniqueConstraints = @UniqueConstraint(columnNames = "accountNumber"))
public abstract class Account implements Serializable {
	
	private static final long serialVersionUID = 5008343162571576351L;

	public static final String FIND_BY_ACCOUNTNUMBER = "Account.FIND_BY_ACCOUNTNUMBER";
	public static final String FIND_BY_ACCOUNTNUMBER_AND_BANKCODE = "Account.FIND_BY_ACCOUNTNUMBER_AND_BANKCODE";
	public static final String FIND_BY_ID = "Account.FIND_BY_ID";
	public static final String FIND_BY_USER = "Account.FIND_BY_USER";
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	@NotEmpty
	@NotNull
	private String bankName = "S & W Bank";

	@NotEmpty
	@NotNull
	@Size(min = 8, max = 8, message="BLZ hat die Laenge 8")
	private String bankCode = "11235813";

	@NotEmpty
	@NotNull
	@Size(min = 9, max = 9, message="Kontonummer hat die Laenge 9")
	private String accountNumber;
	
	@NotNull
	private BigDecimal amount = BigDecimal.ZERO;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userId")
	private User user;
	
	@Temporal(TemporalType.DATE)
	private Date lastDebit;

	@OneToMany(mappedBy="account", fetch=FetchType.LAZY)
	private List<Transaction> transactions;
	
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getLastDebit() {
		return lastDebit;
	}

	public void setLastDebit(Date lastDebit) {
		this.lastDebit = lastDebit;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public AccountType getType() {
		return null;
	}

	@Override
	public String toString() {
		return this.getType() + "/" + this.accountNumber + "/" + this.bankCode;
	}
	
}
