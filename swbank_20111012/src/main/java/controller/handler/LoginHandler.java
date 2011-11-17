package controller.handler;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import model.Credential;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import util.Role;

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
					
					if(this.currentUser.getRole().equals(Role.CUSTOMER)) {
						return "customer_success";
					}
					else if (this.currentUser.getRole().equals(Role.EMPLOYEE)) {
						return "employee_success";
					}
					else if(this.currentUser.getRole().equals(Role.DEVELOPER)) {
						return "developer_success";
					}
					else {
						errorHandler.setException(new Exception("Unbekannte Login-Rolle!"));
						return "failure";
					}
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
		
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.invalidate();
		
//		userHandler.setCurrentUser(null);
//		userHandler.setAccounts(null);
//		userHandler.setTransactions(null);
//		userEventSrc.fire(null);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sie haben sich erfolgreich abgemeldet!"));
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
