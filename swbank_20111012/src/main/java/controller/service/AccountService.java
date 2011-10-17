package controller.service;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.Account;
import model.Credential;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

@Named
//@Dependent
@Stateless
public class AccountService extends GenericService {
	
	@Inject
	@Category("swbank_20111012")
	private Logger log;
	
	public Account createAccount(Account account) throws Exception {
		log.trace("persist account..");
		em.persist(account);
		em.flush();
		return em.find(Account.class, account.getId());
	}
	
	public Account findAccountByAccountNumber(String accountNumber) throws Exception {
		Query q = em.createNamedQuery(Account.FIND_BY_ACCOUNTNUMBER);
		q.setParameter("accountNumber", accountNumber);
		return (Account) q.getSingleResult();
	}
	
	public Account findAccountById(Long accountId) throws Exception {
		Query q = em.createNamedQuery(Account.FIND_BY_ID);
		q.setParameter("accountId", accountId);
		return (Account) q.getSingleResult();
	}
	
}
