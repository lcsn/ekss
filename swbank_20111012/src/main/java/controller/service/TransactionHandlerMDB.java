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
import model.WebResourceInformation;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import util.RequestType;
import util.TargetType;
import util.WebServiceHelper;


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
	private BankInformationService bankInformationService;
	
	@Inject
	private AccountService accountService;
	
	@Inject
	private TransactionService transactionService;
	
	@Inject
	private WebResourceInformationService webResourceInformationService;
	
	public TransactionHandlerMDB() {
	}

	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;
		try {
			Transaction transaction = (Transaction) objectMessage.getObject();
			log.info("Received message: " + transaction);
			Account sourceAccount = transaction.getAccount();
			Account targetAccount = null;
			if (bankInformationService.getBankCode().equals(transaction.getBankCode())) {
				targetAccount = accountService.findAccountByBankCodeAndAccountNumber(transaction.getBankCode(), transaction.getAccountNumber());
			}
			else {
				WebResourceInformation webResInf = webResourceInformationService.findWebResourceInformationsByBankCodeAndMediaTypeAndRequestType(transaction.getBankCode(), "text/xml", RequestType.POST, TargetType.TRANSACTION);
//				Is it possible to send the user and the sourceAccount in this transactionObject as well or must they be null?
//				user and sourceAccount with their ids are unknown or possibly other entities on the other side
//				the members are xmltransient. sufficient?
				WebServiceHelper.getInstance().doPostXML(webResInf.getPath(), transaction);
			}
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
