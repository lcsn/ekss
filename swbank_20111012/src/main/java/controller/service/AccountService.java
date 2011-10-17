package controller.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.Account;
import model.Credential;
import model.User;

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
		log.trace("createAccount");
		em.persist(account);
		em.flush();
		return em.find(Account.class, account.getId());
	}
	
	public Account findAccountByAccountNumber(String accountNumber) throws Exception {
		log.trace("findAccountByAccountNumber");
		Query q = em.createNamedQuery(Account.FIND_BY_ACCOUNTNUMBER);
		q.setParameter("accountNumber", accountNumber);
		return (Account) q.getSingleResult();
	}
	
	public Account findAccountByAccountNumberAndBankcode(String accountNumber, String bankCode) throws Exception {
		log.trace("findAccountByAccountNumberAndBankcode");
		Query q = em.createNamedQuery(Account.FIND_BY_ACCOUNTNUMBER_AND_BANKCODE);
		q.setParameter("accountNumber", accountNumber);
		q.setParameter("bankCode", bankCode);
		return (Account) q.getSingleResult();
	}
	
	public Account findAccountById(Long accountId) throws Exception {
		log.trace("findAccountById");
		Query q = em.createNamedQuery(Account.FIND_BY_ID);
		q.setParameter("accountId", accountId);
		return (Account) q.getSingleResult();
	}
	
	public List<Account> findAccountsByUser(User user) throws Exception {
		log.trace("findAccountsByUser");
		Query q = em.createNamedQuery(Account.FIND_BY_USER);
		q.setParameter("user", user);
		return (List<Account>) q.getResultList();
	}
	
}
