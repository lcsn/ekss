package controller.handler;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import model.Credential;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import util.exception.VerificationException;
import controller.service.CredentialService;
import controller.service.GenericService;
import controller.service.PasswordService;
import controller.service.UserService;

@Named("registrationHandler")
@RequestScoped
@Stateful
public class RegistrationHandler extends GenericService {

	@Inject
	@Category("swbank_20111012")
	private Logger log;
	
	private User newUser;
	
	private Credential newCredentials;
	
	@Inject
	private PasswordService service;
	
	@Inject
	private CredentialService credentialService;
	
	@Inject
	private UserService userService;
	
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
	
	public String doRegister() {
		log.trace("register..");
		try {
			if(newCredentials.verify()) {
				String hash = service.encrypt(newCredentials.getPass());
				newCredentials.setPass(hash);
				newUser.setCredentials(newCredentials);
				userService.createUser(newUser);
				log.trace("registered!");
				return "success";
			}
		} catch (VerificationException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		log.trace("registering " + newUser + " failed!");
		return "failure";
	}
}
