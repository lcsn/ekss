package controller.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.persistence.Query;

import model.Message;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import util.MailInterval;

@Stateless
public class MailService extends GenericService {

	public static final String SMTP_SERVER = "bcc-sv005.bcc.intern";
	
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
	
	public boolean send(Message msg) {
		return send(msg.getSender(), msg.getRecipient().getEmail(), msg.getSubject(), msg.getText());
	}
	
	public boolean send(String sender, String recipient, String subject, String text) {
//		log.info("sending message to " + recipient);
		Properties properties = new Properties();
//		properties.put("mail.smtp.host", smtpHost);
//		properties.put("mail.smtp.auth", "true");
		properties.setProperty("mail.mime.charset", "utf-8");
		properties.put("mail.smtp.starttls.enable","false" ); 
		Session session = Session.getDefaultInstance(properties);//, new MailAuthenticator("lars-christian.simon@hotmail.de", ""));
		try {
			javax.mail.Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(sender));
			msg.setRecipient(RecipientType.TO, new InternetAddress(recipient));
			msg.setSubject(subject);
			msg.setText(text);
			msg.setSentDate(new Date());
			Transport t = session.getTransport("smtp");
			t.connect(SMTP_SERVER, null, null);
			t.sendMessage(msg, msg.getAllRecipients());
			t.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*
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
