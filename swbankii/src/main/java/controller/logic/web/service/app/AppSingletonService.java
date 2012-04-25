package controller.logic.web.service.app;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.entity.User;

import org.jboss.logging.Logger;

import controller.logic.ejb.user.UserDAO;


@Named
@Singleton
@ApplicationScoped
@Startup
public class AppSingletonService {

	private Logger log = Logger.getLogger(AppSingletonService.class);

	@Inject
	private UserDAO userDAO;
	
	private User currentUser;
	
	private static AppSingletonService instance;
	
	public void setCurrentUser(User user) {
		if(user == null) {
			log.warn("The current user was set to null!");
		}
		else {
			log.info("Current user is " + user);
		}
		this.currentUser = user;
	}

	public User getCurrentUser() {
		if(this.currentUser == null) {
			log.warn("Currently is no user set!");
		}
		return this.currentUser;
	}
	
	public static synchronized AppSingletonService getInstance() {
		if(instance == null) {
			instance = new AppSingletonService();
		}
		return instance;
	}

	public void refreshUser() {
		this.currentUser = userDAO.findUserById(currentUser.getId());
	}

}