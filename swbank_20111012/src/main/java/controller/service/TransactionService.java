package controller.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;

import model.Transaction;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

@Named
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

	
}
