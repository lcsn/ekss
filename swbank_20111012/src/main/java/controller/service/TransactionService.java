package controller.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;

import model.Transaction;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

@Named
//@Dependent
@Stateless
public class TransactionService extends GenericService {
	
	@Inject
	@Category("swbank_20111012")
	private Logger log;
	
	public Transaction createTransaction(Transaction transaction) throws Exception {
		log.trace("persist transaction..");
		em.persist(transaction);
		em.flush();
		return em.find(Transaction.class, transaction.getId());
	}
	
	public Transaction findTransactionByTransactionNumber(String transactionNumber) throws Exception {
		Query q = em.createNamedQuery(Transaction.FIND_BY_TRANSACTIONNUMBER);
		q.setParameter("transactionNumber", transactionNumber);
		return (Transaction) q.getSingleResult();
	}
	
	public Transaction findTransactionById(Long transactionId) throws Exception {
		Query q = em.createNamedQuery(Transaction.FIND_BY_ID);
		q.setParameter("transactionId", transactionId);
		return (Transaction) q.getSingleResult();
	}
	
	public List<Transaction> findTransactionByTransactionDateGreaterGivenDate(Date date) throws Exception {
		Query q = em.createNamedQuery(Transaction.FIND_BY_TRANSACTIONDATE_GREATER_GIVEN_DATE);
		q.setParameter("date", date);
		return (List<Transaction>) q.getResultList();
	}
	
}
