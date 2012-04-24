package controller.logic.web.service.app;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import model.entity.user.User;

import org.jboss.logging.Logger;

@Singleton
@ApplicationScoped
@Startup
public class CDBOAppSingletonService {

	private Logger log = Logger.getLogger(CDBOAppSingletonService.class);

	@Named
	private User currentUser;
	
	private static CDBOAppSingletonService instance;
	
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
	
	public static synchronized CDBOAppSingletonService getInstance() {
		if(instance == null) {
			instance = new CDBOAppSingletonService();
		}
		return instance;
	}
}
