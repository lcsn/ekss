package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotEmpty;

@XmlRootElement
@NamedQueries({
	@NamedQuery(name=Transaction.FIND_BY_TRANSACTIONNUMBER, query="select t from Transaction t where t.transactionNumber=:transactionNumber"),
	@NamedQuery(name=Transaction.FIND_BY_ID, query="select t from Transaction t where t.id=:transactionId"),
	@NamedQuery(name=Transaction.FIND_BY_BANKCODE_AND_ACCOUNTNUMBER, query="select t from Transaction t where t.bankCode=:bankCode and t.accountNumber=:accountNumber"),
	@NamedQuery(name=Transaction.FIND_TRANSACTIONS_BY_ACCOUNT, query="select t from Transaction t where t.account=:account"),
//	@NamedQuery(name=Transaction.FIND_BY_TRANSACTIONDATE_GREATER_GIVEN_DATE, query="select t from Transaction t where t.transactionDate>:date"),
//	@NamedQuery(name=Transaction.FIND_BY_TRANSACTIONDATE_GREATER_GIVEN_DATE_AND_USER, query="select t from Transaction t where t.transactionDate>:date and t.user:=user"),
	@NamedQuery(name=Transaction.FIND_BY_USER, query="select t from Transaction t where t.user=:user")
	})
@Entity
@Table(name="TransactionBean", uniqueConstraints = @UniqueConstraint(columnNames = "transactionNumber"))
public class Transaction implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 2013925495267385363L;

	public static final String FIND_BY_TRANSACTIONNUMBER = "Transaction.FIND_BY_ACCOUNTNUMBER";
	public static final String FIND_BY_ID = "Transaction.FIND_BY_ID";
//	public static final String FIND_BY_TRANSACTIONDATE_GREATER_GIVEN_DATE = "Transaction.FIND_BY_TRANSACTIONDATE_GREATER_GIVEN_DATE";
//	public static final String FIND_BY_TRANSACTIONDATE_GREATER_GIVEN_DATE_AND_USER = "Transaction.FIND_BY_TRANSACTIONDATE_GREATER_GIVEN_DATE_AND_USER";
	public static final String FIND_BY_USER = "Transaction.FIND_BY_USER";
	public static final String FIND_BY_BANKCODE_AND_ACCOUNTNUMBER = "Transaction.FIND_BY_BANKCODE_AND_ACCOUNTNUMBER";
	public static final String FIND_TRANSACTIONS_BY_ACCOUNT = "Transaction.FIND_TRANSACTIONS_BY_ACCOUNT";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Long foreignId;

	@NotNull
	private String transactionNumber;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="accountId")
	private Account account;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userId")
	private User user;
	
	@NotNull
	@Size(min = 1, max = 25, message="max. 25 Buchstaben")
	@Pattern(regexp = "[A-Za-z ]*", message = "Nur Buchstaben und Leerzeichen erlaubt!")
	private String firstnameOfReceiver;
	
	@NotNull
	@Size(min = 1, max = 25, message="max. 25 Buchstaben")
	@Pattern(regexp = "[A-Za-z ]*", message = "Nur Buchstaben und Leerzeichen erlaubt!")
	private String lastnameOfReceiver;
	
	@NotEmpty
	@NotNull
	@Size(min = 8, max = 8, message="BLZ hat die Laenge 8")
	private String bankCode;

	@NotEmpty
	@NotNull
	@Size(min = 9, max = 9, message="Kontonummer hat die Laenge 9")
	private String accountNumber;
	
	@NotNull
	@DecimalMin(value="0.01", message="Minimaler Betrag ist 0,01 €!")
	@DecimalMax(value="2500.00", message="Maximaler Betrag ist 2500,00 €")
	private BigDecimal amount;

	@Future
	@Temporal(TemporalType.DATE)
	private Date transactionDate;

	@NotNull
	private boolean processed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getForeignId() {
		return foreignId;
	}

	public void setForeignId(Long foreignId) {
		this.foreignId = foreignId;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
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

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	@XmlTransient
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFirstnameOfReceiver() {
		return firstnameOfReceiver;
	}

	public void setFirstnameOfReceiver(String firstnameOfReceiver) {
		this.firstnameOfReceiver = firstnameOfReceiver;
	}

	public String getLastnameOfReceiver() {
		return lastnameOfReceiver;
	}

	public void setLastnameOfReceiver(String lastnameOfReceiver) {
		this.lastnameOfReceiver = lastnameOfReceiver;
	}

	public void generateTransactionNumber() {
		Calendar calendar = Calendar.getInstance();
		this.transactionNumber = (this.user!=null?this.user.getId():foreignId) + "/" + calendar.getTimeInMillis();
	}
	
	@Override
	public String toString() {
		return bankCode + "/" + accountNumber + "/" + transactionNumber;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Transaction && (this.id != null) ? this.id.equals(((Transaction) obj).getId()) : (obj == this);
	}

	@Override
	public Transaction clone() {
		try {
			return (Transaction) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return this;
		}
	}
	
}