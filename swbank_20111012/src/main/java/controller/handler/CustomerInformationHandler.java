package controller.handler;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import util.qualifier.CustomerInfo;

@Named("customerInformationHandler")
@RequestScoped
public class CustomerInformationHandler {

	@Inject
	@Category("customerInformationHandler")
	private Logger log;

	@CustomerInfo
	@Inject
	private User selectedUser;
	
	@PostConstruct
	public void init() {
		log.info("init CustomerInformationHandler");
		System.out.println("Selected User: " + selectedUser);
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

}
