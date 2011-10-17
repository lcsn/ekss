package controller.handler;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import model.Credential;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import controller.service.CredentialService;
import controller.service.GenericService;
import controller.service.PasswordService;
import controller.service.UserService;

@Named("loginHandler")
@SessionScoped
@Stateful
public class LoginHandler extends GenericService {

	@Inject
	@Category("swbank_20111012")
	private Logger log;

	private User currentUser;

	private Credential credentials;
	
	@Inject
	private CredentialService credentialService;
	
	@Inject
	private UserService userService;
	
	@Inject
	private PasswordService service;

	@PostConstruct
	public void init() {
		credentials = new Credential();
	}

	public String doLogin() {
		log.trace("login..");
		try {
			Credential c = null;
			try {
				c = credentialService.findCredentialByIdentityAndPass(credentials.getIdentity(), service.encrypt(credentials.getPass()));
			} catch (Exception e) {
				log.error(e);
			}
			if (c != null) {
				User user = userService.findUserByCredentials(c);
				if (user != null) {
					this.currentUser = user;
					log.trace(currentUser + " logged in!");
					return "success";
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return "failure";
	}

	public String doLogout() {
		currentUser = new User();
		credentials = new Credential();
		return "success";
	}
	
	public boolean isLoggedIn() {
		return currentUser != null;
	}
	
	@Produces
	public Credential getCredentials() {
		return credentials;
	}

	@Produces
	public User getCurrentUser() {
		return currentUser;
	}

}
