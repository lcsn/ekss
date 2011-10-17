package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@NamedQueries({
	@NamedQuery(name=Transaction.FIND_BY_TRANSACTIONNUMBER, query="select t from Transaction t where t.transactionNumber=:transactionNumber"),
	@NamedQuery(name=Transaction.FIND_BY_ID, query="select t from Transaction t where t.id=:transactionId"),
	@NamedQuery(name=Transaction.FIND_BY_TRANSACTIONDATE_GREATER_GIVEN_DATE, query="select t from Transaction t where t.transactionDate>:date")
	})
@Entity
@Table(name="TransactionBean", uniqueConstraints = @UniqueConstraint(columnNames = "transactionNumber"))
public class Transaction implements Serializable {
	
	private static final long serialVersionUID = 2013925495267385363L;

	public static final String FIND_BY_TRANSACTIONNUMBER = "Transaction.FIND_BY_ACCOUNTNUMBER";
	public static final String FIND_BY_ID = "Transaction.FIND_BY_ID";
	public static final String FIND_BY_TRANSACTIONDATE_GREATER_GIVEN_DATE = "Transaction.FIND_BY_TRANSACTIONDATE_GREATER_GIVEN_DATE";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@NotNull
	private String transactionNumber;
	
	@NotEmpty
	@NotNull
	@Size(min = 8, max = 8, message="BLZ hat die L�nge 8")
	private String bankCode;

	@NotEmpty
	@NotNull
	@Size(min = 9, max = 9, message="Kontonummer hat die L�nge 9")
	private String accountNumber;
	
	@NotEmpty
	@NotNull
	@DecimalMin(value="0.01", message="Minimum ist ein 1 cent!")
	private BigDecimal amount;

	@Temporal(TemporalType.DATE)
	private Date transactionDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	@PrePersist
	private void setTransactionNumber() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.transactionDate);
		this.transactionNumber = "" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR) + "/" + calendar.getTimeInMillis();
	}
	
	@Override
	public String toString() {
		return bankCode + "/" + accountNumber + "/" + transactionNumber;
	}
	
}