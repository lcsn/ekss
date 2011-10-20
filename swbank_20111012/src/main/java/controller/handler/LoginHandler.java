package controller.handler;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
@Stateless
public class LoginHandler extends GenericService {

	@Inject
	@Category("loginhandler")
	private Logger log;

	private User currentUser;

	private Credential credentials;

	@Inject
	private CredentialService credentialService;

	@Inject
	private UserService userService;

	@Inject
	private PasswordService service;

//	@Inject
//	private Event<User> userEventSrc;

	@Inject
	private UserHandler userHandler;
	
	@Inject
	private ErrorHandler errorHandler;

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
				errorHandler.setException(e);
				log.error(e);
			}
			if (c != null) {
				User user = userService.findUserByCredentials(c);
				if (user != null) {
					this.currentUser = user;
					userHandler.setCurrentUser(user);
//					userEventSrc.fire(currentUser);
//					log.info(currentUser + " logged in!");
					return "success";
				}
			}
		} catch (Exception e) {
			errorHandler.setException(e);
			log.error(e);
		}
		return "failure";
	}

	public String doLogout() {
		log.info(userHandler.getCurrentUser() + " has logged out");
		currentUser = new User();
		credentials = new Credential();
		userHandler.setCurrentUser(null);
//		userEventSrc.fire(null);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Abmeldung!", "Sie haben sich erfolgreich abgemeldet!"));
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
