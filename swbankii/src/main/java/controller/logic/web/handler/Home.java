package controller.logic.web.handler;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import model.entity.Address;
import model.entity.User;

import org.jboss.logging.Logger;

@Named("homeModule")
@RequestScoped
public class Home {

	private Logger log = Logger.getLogger(Home.class);
	
	private User selectedUser = new User();
	
	private Address selectedAddress = new Address();

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public Address getSelectedAddress() {
		return selectedAddress;
	}

	public void setSelectedAddress(Address selectedAddress) {
		this.selectedAddress = selectedAddress;
	}

}
