package controller.logic.web.handler;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.entity.User;

import org.jboss.logging.Logger;

import util.Role;

import controller.logic.ejb.user.UserDAO;

@Named("registrationModule")
@RequestScoped
public class Registration {
	
	private Logger log = Logger.getLogger(Registration.class);
	
	@Inject
	private UserDAO userDAO;
	
	private User newUser;
	
	@PostConstruct
	public void init() {
		this.newUser = new User();
	}

	public String register() {
		log.info("registering a new user");
		String url = "/register.xhtml";
		if(newUser.verify()) {
			try {
				newUser.addRole(Role.swbankiicustomer);
				userDAO.create(newUser);
				url = "/login.xhtml";
			} catch (Exception e) {
				addMessage(new FacesMessage(FacesMessage.SEVERITY_WARN, "E-Mail Adresse nicht verfügbar!", "E-Mail Adresse nicht verfügbar!"));
				e.printStackTrace();
			}
		}
		else {
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwörter stimmen nicht überein!", "Passwörter stimmen nicht überein!"));
		}
		return url;
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage("", message);
	}
	
	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

}
