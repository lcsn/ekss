package controller.logic.web.handler;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.entity.user.User;

import org.jboss.logging.Logger;

import util.ApplicationConstants;

import controller.logic.ejb.user.UserDAO;
import controller.logic.web.service.app.CDBOAppSingletonService;

@Named("loginModule")
@RequestScoped
public class Login {

	private Logger log = Logger.getLogger(Login.class);

	private String username;

	private String password;

	@EJB
	private UserDAO userDAO;

	@Inject
	private CDBOAppSingletonService cdboAppSingletonService;

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		log.debug("request login bean");
	}

	public String login() {
		log.debug("login");
		String url = "/login.jsf";
		try {
			HttpServletRequest request = getHttpRequest();
			request.login(username, password);
			Principal principal = request.getUserPrincipal();
			log.info("Remote-User: " + request.getRemoteUser());
			if (request.isUserInRole("swbankiiadmin")) {
				addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Login successful. Welcome, " + principal.getName()));
				addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Your role is: ", "coreDbUser".toUpperCase()));
				url = "/swbankii/bank/admin/home.jsf";
				FacesContext.getCurrentInstance().getExternalContext().redirect(url);
			} else if (request.isUserInRole("swbankiiemployee")) {
				addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Login successful. Welcome, " + principal.getName()));
				addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Your role is: ", "coreDbUser".toUpperCase()));
				url = "/swbankii/bank/employee/home.jsf";
				FacesContext.getCurrentInstance().getExternalContext().redirect(url);
			} else if (request.isUserInRole("swbankiicustomer")) {
				addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Login successful. Welcome, " + principal.getName()));
				addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Your role is: ", "coreDbAdmin".toUpperCase()));
				url = "/swbankii/bank/customer/home.jsf";
				FacesContext.getCurrentInstance().getExternalContext().redirect(url);
			} else {
				addMessage(new FacesMessage(FacesMessage.SEVERITY_WARN, "Unauthorized", "Sorry, but you've not been redirected!"));
				request.logout();
			}
			User user = userDAO.findUserByUsername(principal.getName());
			cdboAppSingletonService.setCurrentUser(user);
			 getHttpSession().setAttribute(ApplicationConstants.LOGIN_CREDENTIAL_KEY, principal);
			 getHttpSession().setAttribute(ApplicationConstants.LOGIN_USER_KEY, user);
		} catch (ServletException e) {
			addMessage(new FacesMessage(FacesMessage.SEVERITY_WARN, "Login fehlgeschlagen", "Benutzername bzw. Passwort ist falsch!"));
			log.error(e);
		} catch (IOException e) {
			addMessage(new FacesMessage(FacesMessage.SEVERITY_WARN, "IOException", "Sorry, but you've not been redirected!"));
			log.error(e);
		}
		return null;
	}

	public String logout() {
		log.info("logout");
		String url = "/swbankii/login.jsf";
		try {
			getHttpRequest().logout();
			cdboAppSingletonService.setCurrentUser(null);

			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			if (session != null) {
				session.invalidate();
			}
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (ServletException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		return null;
	}

	private FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	private void addMessage(FacesMessage msg) {
		getFacesContext().addMessage(null, msg);
	}

	private HttpSession getHttpSession() {
		return (HttpSession) getFacesContext().getExternalContext().getSession(false);
	}

	private HttpServletRequest getHttpRequest() {
		return (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		// String tmp = PasswordService.encrypt(password);
		// log.info("encryption: " + tmp);
		// this.password = tmp;
		this.password = password;
	}

}