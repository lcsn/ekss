package controller.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import model.Account;
import model.Message;
import model.Transaction;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import util.MailInterval;

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
	private AccountService accountService;
	
	@Inject
	private TransactionService transactionService;
	
	@Inject
	private MailService mailService;


	@SuppressWarnings("unused")
	@Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
	private void creditOffering() {
		log.info("Check customer accounts for possible credit offers");
		List<Account> accounts = accountService.findAllAccounts();
		int count = 0;
		for (Account acc : accounts) {
			User user = acc.getUser();
			boolean receivedMailBefore = mailService.wasUserMailedWithinTheGivenInterval(user, MailInterval.h24);
			if(receivedMailBefore && acc.getAmount().compareTo(BigDecimal.ZERO) == -1) {
				Message msg = new Message();
				msg.setRecipient(user);
				msg.setSender("swbank@customercare.de");
				msg.setSmtpHost("smtp.live.com");
				msg.setSubject("A special offer for you!");
				msg.setText("Dear customer,\n" +
				"\nthis is your bank.\n" +
				"We would like to offer you a credit.\n" +
				"\nyours sincerely,\n" +
				"Your Bank");
				msg.setTimestamp(new Date());
				mailService.createMessage(msg);
				log.info("A creditoffer-message to " + user.getEmail() + " was stored and is ready to be sent!");
				count++;
			}
		}
		log.info(count + " message(s) have been stored!");
	}
	
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
