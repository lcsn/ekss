package controller.service;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import model.Account;
import model.Transaction;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;


@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/transactionQueue")},
		name = "TransactionMDB")
public class TransactionHandlerMDB implements MessageListener {

	@Inject
	@Category("TransactionMDB")
	private Logger log;

	@Inject
	private AccountService accountService;
	
	@Inject
	private TransactionService transactionService;
	
	public TransactionHandlerMDB() {
	}

	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;
		try {
			Transaction transaction = (Transaction) objectMessage.getObject();
			log.info("Received message: " + transaction);
			Account sourceAccount = transaction.getAccount();
			Account targetAccount = accountService.findAccountByBankCodeAndAccountNumber(transaction.getBankCode(), transaction.getAccountNumber());
			accountService.transferCash(sourceAccount, targetAccount, transaction.getAmount());
			transactionService.markTransactionAsProcessed(transaction.getId());
		} catch (JMSException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			log.error("Error while transferring ca$h.");
			e.printStackTrace();
		}
	}
}
