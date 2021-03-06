package controller.handler;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;

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
import controller.service.TransactionService;

@SuppressWarnings("serial")
@Named("bankingHandler")
@RequestScoped
public class BankingHandler implements Serializable {

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

//	@Resource(mappedName = "java:/ConnectionFactory")
//	private ConnectionFactory connectionFactory;

//	@Resource(mappedName = "java:/queue/transactionQueue")
//	private Queue queue;

	private Account newAccount;
	private Transaction newTransaction;

	private AccountType type;

	@PostConstruct
	public void init() {
		// this.newAccount = new Account();
		this.newAccount = null;
		this.newTransaction = new Transaction();
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public Account getNewAccount() {
		if (this.newAccount != null) {
			return this.newAccount;
		}
		return null;
	}

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
//			Connection connection = connectionFactory.createConnection();
//			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//			MessageProducer producer = session.createProducer(queue);
//			ObjectMessage objectMessage = session.createObjectMessage(newTransaction.clone());
			
//			producer.send(objectMessage);
//			producer.close();
//			session.close();
//			connection.close();
			
//			Message message = session.createTextMessage();
//			message.setLongProperty("id", newTransaction.getId());
//			producer.send(message);

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
