package controller.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import model.Account;
import model.Transaction;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

@Stateless
public class TransactionService extends GenericService {
	
	@Inject
	@Category("transactionservice")
	private Logger log;
	
	public Transaction createTransaction(Transaction transaction) throws Exception {
		log.trace("createTransaction");
		transaction.generateTransactionNumber();
		em.persist(transaction);
		em.flush();
		return em.find(Transaction.class, transaction.getId());
	}
	
	public Transaction updateTransaction(Transaction transaction) {
		log.trace("updateTransaction");
		if(transaction.getId() != null) {
			em.merge(transaction);
			em.flush();
			return em.find(Transaction.class, transaction.getId());
		}
		log.info("Transaction is not persistent.");
		return transaction;
	}
	
	public boolean markTransactionAsProcessed(Long transactionId) throws Exception {
		log.trace("markTransactionAsProcessed");
		Transaction transaction = findTransactionById(transactionId);
		transaction.setProcessed(true);
		updateTransaction(transaction);
		return true;
	}
	
	public Transaction findTransactionByTransactionNumber(String transactionNumber) throws Exception {
		log.trace("findTransactionByTransactionNumber");
		Query q = em.createNamedQuery(Transaction.FIND_BY_TRANSACTIONNUMBER);
		q.setParameter("transactionNumber", transactionNumber);
		return (Transaction) q.getSingleResult();
	}
	
	public Transaction findTransactionById(Long transactionId) throws Exception {
		log.trace("findTransactionById");
		Query q = em.createNamedQuery(Transaction.FIND_BY_ID);
		q.setParameter("transactionId", transactionId);
		return (Transaction) q.getSingleResult();
	}
	
//	public List<Transaction> findTransactionsByTransactionDateGreaterGivenDate(Date date) throws Exception {
//		log.trace("findTransactionsByTransactionDateGreaterGivenDate");
//		Query q = em.createNamedQuery(Transaction.FIND_BY_TRANSACTIONDATE_GREATER_GIVEN_DATE);
//		q.setParameter("date", date);
//		return (List<Transaction>) q.getResultList();
//	}
	
//	public List<Transaction> findTransactionsByTransactionDateGreaterGivenDateAndUser(Date date, User user) throws Exception {
//		log.trace("findTransactionsByTransactionDateGreaterGivenDateAndUser");
//		Query q = em.createNamedQuery(Transaction.FIND_BY_TRANSACTIONDATE_GREATER_GIVEN_DATE_AND_USER);
//		q.setParameter("date", date);
//		q.setParameter("user", user);
//		return (List<Transaction>) q.getResultList();
//	}

	@SuppressWarnings("unchecked")
	public List<Transaction> findTransactionsByUser(User user) throws Exception {
		Query q = em.createNamedQuery(Transaction.FIND_BY_USER);
		q.setParameter("user", user);
		return (List<Transaction>) q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> findTransactionsByBankCodeAndAccountnumber(String bankCode, String accountNumber) {
		Query q = em.createNamedQuery(Transaction.FIND_BY_BANKCODE_AND_ACCOUNTNUMBER);
		q.setParameter("bankCode", bankCode);
		q.setParameter("accountNumber", accountNumber);
		return (List<Transaction>) q.getResultList();
	}
	
	public List<Transaction> findTransactionsByBankCodeAndAccountnumber(List<Account> accounts) {
		List<Transaction> outgoing = new ArrayList<Transaction>();
		for (Account account : accounts) {
			outgoing.addAll(findTransactionsByBankCodeAndAccountnumber(account.getBankCode(), account.getAccountNumber()));
		}
		return outgoing;
	}

	@SuppressWarnings("unchecked")
	public List<Transaction> findTransactionsByAccount(Account account) {
		Query q = em.createNamedQuery(Transaction.FIND_TRANSACTIONS_BY_ACCOUNT);
		q.setParameter("account", account);
		return (List<Transaction>) q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Transaction> findTransactionsByDate(Calendar cal) {
		Query q = em.createNamedQuery(Transaction.FIND_TRANSACTIONS_BY_DAY_AND_MONTH);
		q.setParameter("day", cal.get(Calendar.DAY_OF_MONTH));
		q.setParameter("month", (cal.get(Calendar.MONTH)+1));
		return (List<Transaction>) q.getResultList();
	}

}
