package controller.handler;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
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
import org.primefaces.event.DateSelectEvent;

import util.AccountType;
import controller.service.AccountService;
import controller.service.BankInformationService;
import controller.service.GenericService;

@Named("bankingHandler")
@Stateless
public class BankingHandler extends GenericService {

	@Inject
	@Category("bankinghandler")
	private Logger log;
	
	@Inject
	private ErrorHandler errorHandler;
	
	@Inject
	private UserHandler userHandler;
	
	@Inject
	private AccountService accountService;
	
//	@Inject
//	private TransactionService transactionService;
	
	@Inject
	private BankInformationService bankInformationService;
	
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
	
	private void setCommonAccountAttributes() {
		log.info("setCommonAccountAttributes");
		this.newAccount.setUser(userHandler.getCurrentUser());
		this.newAccount.setBankName(bankInformationService.getBankName());
		this.newAccount.setBankCode(bankInformationService.getBankCode());
		this.newAccount.setAccountNumber(bankInformationService.getNewAccountNumber());
		this.newAccount.setAmount(BigDecimal.ZERO);
		this.newAccount.setLastDebit(null);
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
		setCommonAccountAttributes();
		try {
			accountService.createAccount(newAccount);
		} catch (Exception e) {
			errorHandler.setException(e);
			log.error(e);
		}
		userHandler.init();
		init();
	}
	
	public void saveNewTransaction() {
		log.info("saveNewTransaction");
	}
	
	public void handleDateSelection(DateSelectEvent event) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ausführungsdatum: ", sdf.format(event.getDate())));
	}
}
