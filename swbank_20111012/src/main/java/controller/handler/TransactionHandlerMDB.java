package controller.handler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import model.Transaction;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import controller.service.TransactionService;

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
	private TransactionService transactionService;
	
	public TransactionHandlerMDB() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onMessage(Message message) {
		Long entityId = null;
		try {
			entityId = message.getLongProperty("id");
			log.info("Received message: " + entityId);
		} catch (JMSException e1) {
			e1.printStackTrace();
		}
		
		if(entityId != null) {
			try {
//				Transaction transaction = transactionService.findTransactionById(entityId);
//				log.info("Transactionnumber: " + transaction.getTransactionNumber());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
