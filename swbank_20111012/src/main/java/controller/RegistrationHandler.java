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

import util.exception.RegistrationException;

@Named("registrationHandler")
@RequestScoped
@Stateful
public class RegistrationHandler extends GenericDAO {

	@Inject
	@Category("swbank_20111012")
	private Logger log;
	
	private User newUser;
	
	private Credential newCredentials;
	
	@Inject
	private PasswordService service;
	
	@Inject
	private CredentialDAOBean credentialDAO;
	
	@Inject
	private UserDAOBean userDAO;
	
	@Produces
	public User getNewUser() {
		return newUser;
	}
	
	@Produces
	public Credential getNewCredentials() {
		return newCredentials;
	}
	
	@PostConstruct
	public void init() {
		this.newCredentials = new Credential();
		this.newUser = new User();
	}
	
	public String doRegister() throws RegistrationException {
		log.trace("register..");
		
//		newCredentials = credentialDAO.createCredential(newCredentials);
		if(newCredentials.verify()) {
			String hash = service.encrypt(newCredentials.getPass());
			newCredentials.setPass(hash);
			newUser.setCredentials(newCredentials);
			userDAO.createUser(newUser);
			log.trace("registered!");
			return "success";
		}
		else {
			log.trace("registering " + newUser + " failed!");
			return "failure";
//			throw new RegistrationException(newUser);
		}
	}
}
