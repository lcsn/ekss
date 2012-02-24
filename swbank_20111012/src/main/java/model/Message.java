package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

@NamedQueries({
	@NamedQuery(name=Message.FIND_BY_ID, query="select m from Message m where m.id=:id"),
	@NamedQuery(name=Message.FIND_LATEST_MESSAGEDATE_BY_USER, query="select max(m.timestamp) from Message m where m.recipient=:user")
	})
@Entity
@Table(name="MessageBean")
public class Message implements Serializable {
	
	private static final long serialVersionUID = -5886580565426006476L;

	public static final String FIND_BY_ID = "MESSAGE.FIND_BY_ID";
	public static final String FIND_LATEST_MESSAGEDATE_BY_USER = "MESSAGE.FIND_LATEST_MESSAGEDATE_BY_USER";
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	@NotNull
	private String mailHost;
	
	@NotNull
	@Email
	private String sender;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userId")
	private User recipient;
	
	private String subject;

	@NotNull
	private String text;
	
	private boolean send = false;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMailHost() {
		return mailHost;
	}

	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public User getRecipient() {
		return recipient;
	}

	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isSend() {
		return send;
	}

	public void setSend(boolean send) {
		this.send = send;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
