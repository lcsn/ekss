package controller.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import model.Transaction;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

@Singleton
public class TransactionProcessingService extends GenericService {

	@Inject
	@Category("transactionProcessingService")
	private Logger log;

	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "java:/queue/transactionQueue")
	private Queue queue;

	@Inject
	private TransactionService transactionService;

	@SuppressWarnings("unused")
	@Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
	private void processTransactions() {
		log.info("processing transactions");
		try {
			Calendar cal = Calendar.getInstance(); // today
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			log.info("Time: " + sdf.format(cal.getTime()));
			List<Transaction> transactions = transactionService.findTransactionsByDate(cal);
			log.info("Found " + transactions.size() + " transactions!");
			// Send JMS message
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);
			ObjectMessage objectMessage;
			for (Transaction t : transactions) {
				log.info("processing: " + t);
				objectMessage = session.createObjectMessage(t.clone());
				producer.send(objectMessage);
			}
			producer.close();
			session.close();
			connection.close();
		} catch (JMSException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
	}

}
