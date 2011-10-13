package controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import model.Credential;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

@Named("registrationHandler")
@RequestScoped
@Stateful
public class RegistrationHandler extends GenericDAO {

	@Inject
	@Category("swbank_20111012")
	private Logger log;
	
//	private User user;
	
//	private Credential credentials;
	
//	@EJB
//	private CredentialDAOBean credentialDAO;
	
//	@EJB
//	private UserDAOBean userDAO;
	
//	@Produces
//	@SessionScoped
//	@Named
//	public User getUser() {
//		return user;
//	}
	
//	@Produces
//	@SessionScoped
//	@Named
//	public Credential getCredentials() {
//		return credentials;
//	}
	
	@PostConstruct
	public void init() {
//		this.credentials = new Credential();
//		this.user = new User();
	}
	
	public void doRegister() {
		log.trace("register..");
		
//		credentials = credentialDAO.createCredential(credentials);
//		user.setCredentials(credentials);
//		userDAO.createUser(user);
		
		log.trace("registered!");
	}
}
