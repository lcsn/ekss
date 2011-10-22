package controller.handler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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

@Named("userHandler")
@SessionScoped
@Stateful
public class UserHandler {

	@Inject
	@Category("userhandler")
	private Logger log;

	private User currentUser;

	private List<Account> accounts;
	private List<Transaction> transactions;
	
	private Account newAccount;
	private Transaction newTransaction;
	
	private static UserHandler instance;

	@PostConstruct
	public void init() {
		this.newAccount = new Account();
		this.newTransaction = new Transaction();
		loadAccounts(null);
		loadTransactions(null, null);
	}
	
	@Produces
	public User getCurrentUser() {
		if (this.currentUser != null) {
			return this.currentUser;
		}
		return null;
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
	
	@Produces
	public List<Account> getAccounts() {
		log.info("get accounts");
		return accounts;
	}
	
	private void loadAccounts(User user) {
		log.info("load accounts");
	}
	
	@Produces
	public List<Transaction> getTransactions() {
		log.info("get transactions");
		return transactions;
	}

	private void loadTransactions(Account account, User user) {
		log.info("load transactions");
	}
	
	@Produces
	public void saveNewAccount() {
		log.info("save account");
		loadAccounts(null);
	}
	
	@Produces
	public void saveNewTransaction() {
		log.info("save transaction");
		loadTransactions(null, null);
	}

	// public void onCurrentUserChanged(@Observes(notifyObserver =
	// Reception.IF_EXISTS) final User user) {
	// setCurrentUser(user);
	// }

	public void setCurrentUser(User user) {
		if (user == null) {
			log.warn("User-Application-Singleton got null-user.");
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sie haben sich erfolgreich angemeldet!"));
			log.info(user + " has logged in!");
			this.currentUser = user;
		}
	}

	public void updateCustomer() {
		log.info("updateCustomer");
		System.out.println("TEEEST: " + this.currentUser);
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

	public static UserHandler getInstance() {
		if (instance == null) {
			instance = new UserHandler();
		}
		return instance;
	}

}
