package controller.handler;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.Account;
import model.CommonAccount;
import model.GiroAccount;
import model.SmartAccount;
import model.Transaction;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import util.AccountType;
import controller.service.GenericService;

@Named("bankingHandler")
@Stateless
public class BankingHandler extends GenericService {

	@Inject
	@Category("bankinghandler")
	private Logger log;
	
	@Inject
	private UserHandler userHandler;
	
	private Account newAccount;
	private Transaction newTransaction;
	
	private AccountType type;
	
	@PostConstruct
	public void init() {
//		this.newAccount = new Account();
		this.newTransaction = new Transaction();
	}
	
	@Produces
	public AccountType getType() {
		return type;
	}
	
	public void setType(AccountType type) {
		this.type = type;
	}
	
	@Produces
	public Account getNewAccount() {
		if (this.newAccount != null) {
			return this.newAccount;
		}
		return null;
	}
	
	@Produces
	public Transaction getNewTransaction() {
		if (this.newTransaction != null) {
			return this.newTransaction;
		}
		return null;
	}
	
	public void saveNewAccount() {
		log.info("saveNewAccount");
		if(type.equals(AccountType.GIROACCOUNT)) {
			log.info("it is a " + AccountType.GIROACCOUNT);
			this.newAccount = new GiroAccount();
		}
		else if(type.equals(AccountType.COMMONACCOUNT)) {
			log.info("it is a " + AccountType.COMMONACCOUNT);
			this.newAccount = new CommonAccount();
		}
		else if(type.equals(AccountType.SMARTACCOUNT)) {
			log.info("it is a " + AccountType.SMARTACCOUNT);	
			this.newAccount = new SmartAccount();	
		}
	}
	
	public void saveNewTransaction() {
		log.info("saveNewTransaction");
	}
	
}
