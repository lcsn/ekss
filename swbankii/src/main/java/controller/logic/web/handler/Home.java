package controller.logic.web.handler;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import model.entity.user.User;

import org.jboss.logging.Logger;

@Named("homeModule")
@RequestScoped
public class Home {

	private Logger log = Logger.getLogger(Home.class);
	
	private User selectedUser;

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

}
