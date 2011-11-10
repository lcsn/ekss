package controller.handler;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

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
import controller.service.TransactionService;

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

	@Inject
	private TransactionService transactionService;

	@Inject
	private BankInformationService bankInformationService;

	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "java:/queue/transactionQueue")
	private Queue queue;

	private Account newAccount;
	private Transaction newTransaction;

	private AccountType type;

	@PostConstruct
	public void init() {
		// this.newAccount = new Account();
		this.newAccount = null;
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
		if (type.equals(AccountType.GIROACCOUNT)) {
			log.info("it is a " + AccountType.GIROACCOUNT);
			this.newAccount = new GiroAccount();
		} else if (type.equals(AccountType.COMMONACCOUNT)) {
			log.info("it is a " + AccountType.COMMONACCOUNT);
			this.newAccount = new CommonAccount();
		} else if (type.equals(AccountType.SMARTACCOUNT)) {
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
		this.newAccount = null;
	}

	public void abortNewAccount() {
		log.info("abortNewAccount");
		this.newAccount = null;
	}

	public void saveNewTransaction() {
		log.info("saveNewTransaction");

		try {
			newTransaction.setUser(userHandler.getCurrentUser());
			transactionService.createTransaction(newTransaction);

//			Send JMS message
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);
			Message message = session.createTextMessage();
			message.setLongProperty("id", newTransaction.getId());
			producer.send(message);

		} catch (JMSException e) {
			errorHandler.setException(e);
			log.error(e);
		} catch (Exception e) {
			errorHandler.setException(e);
			log.error(e);
		}

		userHandler.init();
		this.newTransaction = new Transaction();
	}

	public void abortNewTransaction() {
		log.info("abortNewTransaction");
		this.newTransaction = new Transaction();
	}

	public void handleDateSelection(DateSelectEvent event) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ausführungsdatum: ", sdf.format(event.getDate())));
	}
}
