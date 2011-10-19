package controller.handler;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

@Named
@SessionScoped
@Stateful
public class UserHandler {

	@Inject
	@Category("userhandler")
	private Logger log;

	private User currentUser;

	private static UserHandler instance;

	@Produces
	public User getCurrentUser() {
		if (this.currentUser != null) {
			return this.currentUser;
		}
		return null;
	}

//	public void onCurrentUserChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final User user) {
//		setCurrentUser(user);
//	}

	public void setCurrentUser(User user) {
		if (user == null) {
			log.warn("User-Application-Singleton got null-user.");
		} else {
			log.info(user + " has logged in!");
			this.currentUser = user;
		}
	}

	public static UserHandler getInstance() {
		if (instance == null) {
			instance = new UserHandler();
		}
		return instance;
	}

}
