package controller.service;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import model.Account;
import model.BankInformation;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

@Stateless
public class AccountService extends GenericService {
	
	@Inject
	@Category("accountservice")
	private Logger log;
	
	@Inject
	private BankInformationService bankInformationService;
	
	public Account createAccount(Account account) throws Exception {
		log.trace("createAccount");
		em.persist(account);
		em.flush();
		BankInformation bi = bankInformationService.findInformationByName("maxAccountNumber");
		bi.setName("maxAccountNumber");
		BankInformationService.getInstance().incrementAccountCounter();
		bi.setValue(String.valueOf(BankInformationService.getInstance().accountCounter));
		bi.setValueClass(Integer.class.getName());
		bankInformationService.updateBankInformation(bi);
		return em.find(Account.class, account.getId());
	}
	
	public Account updateAccount(Account account) throws Exception {
		log.trace("updateAccount");
		if (account.getId() != null) {
			em.merge(account);
			em.flush();
			return em.find(Account.class, account.getId());
		}
		log.info("Account is not persistent.");
		return account;
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
	
	@SuppressWarnings("unchecked")
	public List<Account> findAccountsByUser(User user) throws Exception {
		log.trace("findAccountsByUser");
		Query q = em.createNamedQuery(Account.FIND_BY_USER);
		q.setParameter("user", user);
		return (List<Account>) q.getResultList();
	}

	public Account findAccountByBankCodeAndAccountNumber(String bankCode, String accountNumber) {
		Query q = em.createNamedQuery(Account.FIND_BY_BANKCODE_AND_ACCOUNTNUMBER);
		q.setParameter("bankCode", bankCode);
		q.setParameter("accountNumber", accountNumber);
		return (Account) q.getSingleResult();
	}
	
	public boolean transferCash(Account source, Account target, BigDecimal amount) throws Exception {
		source.debit(amount);
		log.info(amount + " from " + source + " was debited.");
		target.add(amount);
		log.info(amount + " was added to " + target + ".");
		updateAccount(source);
		updateAccount(target);
		return true;
		
	}
}
