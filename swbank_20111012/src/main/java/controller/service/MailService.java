package controller.service;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import model.Message;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import util.MailInterval;

@Stateless
public class MailService extends GenericService {

	@Inject
	@Category("mailservice")
	private Logger log;
	
	public Message createMessage(Message msg) {
		log.info("createMessage");
		msg.setTimestamp(new Date());
		em.persist(msg);
		return em.find(Message.class, msg.getId());
	}
	
	public Message updateMessage(Message msg) {
		log.info("updateMessage");
		em.merge(msg);
		return em.find(Message.class, msg.getId());
	}
	
	public Message findMessageById(Long id) {
		log.trace("findMessageById");
		Query q = em.createNamedQuery(Message.FIND_BY_ID);
		q.setParameter("id", id);
		return (Message) q.getSingleResult();
	}

	public Date findLatestMessageDateByUser(User user) {
		log.trace("findLatestMessageDateByUser");
		Query q = em.createNamedQuery(Message.FIND_LATEST_MESSAGEDATE_BY_USER);
		q.setParameter("user", user);
		return (Date) q.getSingleResult();
	}
	
	public boolean wasUserMailedWithinTheGivenInterval(User user, MailInterval interval) {
		Date latestMessageDate = findLatestMessageDateByUser(user);
		Calendar cal = Calendar.getInstance();
		long todayInMillis = cal.getTimeInMillis();
		cal.setTimeInMillis(todayInMillis - interval.getIntervalInMillis());
		if(latestMessageDate != null && cal.getTime().before(latestMessageDate)) {
			return false;
		}
		return true;
	}
	
/*	
	public void send(String smtpHost, String sender, String recipient, String subject, String text) {
//		log.info("sending message to " + recipient);
		Properties properties = new Properties();
		properties.put("mail.smtp.host", smtpHost);
//		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable","false" ); 
		Session session = Session.getDefaultInstance(properties);//, new MailAuthenticator("lars-christian.simon@hotmail.de", ""));
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(sender));
			msg.setRecipient(RecipientType.TO, new InternetAddress(recipient));
			msg.setSubject(subject);
			msg.setText(text);
			msg.setSentDate(new Date());
			Transport.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MailService mailman = new MailService();
		
		String txt = "Dear customer,\n" +
				"\nthis is your bank.\n" +
				"We would like to offer you a credit.";
		mailman.send("smtp.live.com", "swbank@customercare.de", "lars-christian.simon@hotmail.de", "A special offer for you!", txt );
	}

}

class MailAuthenticator extends Authenticator {
	
	private String id;
	
	private String pass;

	public MailAuthenticator(String id, String pass) {
		this.id = id;
		this.pass = pass;
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(id, pass);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
*/
	
}
