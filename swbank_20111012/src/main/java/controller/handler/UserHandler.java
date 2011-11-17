package controller.handler;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.Account;
import model.Transaction;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.ToggleEvent;

import controller.service.AccountService;
import controller.service.TransactionService;

@Named("userHandler")
@SessionScoped
@Stateful
public class UserHandler {

	@Inject
	@Category("userhandler")
	private Logger log;

	@Inject
	private ErrorHandler errorHandler;
	
	@Inject
	private AccountService accountService;

	@Inject
	private TransactionService transactionService;

	private User currentUser;
	
	private List<Account> accounts;
	private List<Transaction> inTransactions;
	private List<Transaction> outTransactions;
	
	@PostConstruct
	public void init() {
		loadAccounts(null);
		loadTransactions(null, null);
	}
	
	@PreDestroy
	public void destroy() {
		this.currentUser = null;
		this.accounts = null;
		this.inTransactions = null;
		this.outTransactions = null;
	}
	
	@Produces
	public User getCurrentUser() {
		if (this.currentUser != null) {
			return this.currentUser;
		}
		return null;
	}
	
	@Produces
	public List<Account> getAccounts() {
		log.info("get accounts");
		return accounts;
	}
	
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	public void loadAccounts(User user) {
		log.info("load accounts");
		try {
			this.accounts = accountService.findAccountsByUser(user==null?currentUser:user);
		} catch (Exception e) {
			errorHandler.setException(e);
			log.error(e);
		}
	}
	
	@Produces
	public List<Transaction> getInTransactions() {
		log.info("get inTransactions");
		return inTransactions;
	}
	
	public void setInTransactions(List<Transaction> inTransactions) {
		this.inTransactions = inTransactions;
	}
	
	@Produces
	public List<Transaction> getOutTransactions() {
		log.info("get outTransactions");
		return outTransactions;
	}
	
	public void setOutTransactions(List<Transaction> outTransactions) {
		this.outTransactions = outTransactions;
	}

	public void loadTransactions(Account account, User user) {
		log.info("load transactions");
		try {
			this.inTransactions = transactionService.findTransactionsByBankCodeAndAccountnumber(accountService.findAccountsByUser(user==null?currentUser:user));
			this.outTransactions = transactionService.findTransactionsByUser(user==null?currentUser:user);
		} catch (Exception e) {
			errorHandler.setException(e);
			log.error(e);
		}
	}
	
	public void setCurrentUser(User user) {
		if (user == null) {
			log.warn("User-Application-Singleton got null-user.");
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sie haben sich erfolgreich angemeldet!"));
			log.info(user + " has logged in!");
			this.currentUser = user;
			init();
		}
	}

	public void updateCustomer() {
		log.info("updateCustomer");
	}
	
	public void handleClose(CloseEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Panel Closed", "Closed panel id:'" + event.getComponent().getId() + "'");
		addMessage(message);
	}

	public void handleToggle(ToggleEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());
		addMessage(message);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
