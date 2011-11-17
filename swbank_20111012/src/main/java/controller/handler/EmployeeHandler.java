package controller.handler;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import model.Account;
import model.Transaction;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;

import util.Role;

import controller.service.AccountService;
import controller.service.TransactionService;
import controller.service.UserService;

@Named("employeeHandler")
@SessionScoped
@Stateful
public class EmployeeHandler {

	@Inject
	@Category("employeeHandler")
	private Logger log;
	
	@Inject
	private UserService userService;
	
	private User selectedUser;
	
	private List<User> users;

	@PostConstruct
	public void init() {
		this.users = loadUsers();
	}
	
	private List<User> loadUsers() {
		return userService.findUsers();
	}

	@PreDestroy
	public void destroy() {
		this.users = null;
	}
	
	@Produces
	public List<User> getCustomers() {
		if (this.users == null) {
			this.users = loadUsers();
		}
		return users;
	}

	@Produces
	public SelectItem[] getUserOptions() {
		Role[] roles = Role.values();
		SelectItem[] options = new SelectItem[roles.length + 1]; 
		options[0] = new SelectItem("", "Bitte wählen");
		SelectItem item = null;
		int i = 1;
		for (Role role : roles) {
			item = new SelectItem(role, role.toString());
			options[i++] = item;
		}
		return options;
	}
	
	@Produces
	public SelectItem[] getActivOptions() {
		SelectItem[] options = new SelectItem[3]; 
		options[0] = new SelectItem("", "Bitte wählen"); 
		options[1] = new SelectItem("Ja", "Ja"); 
		options[2] = new SelectItem("Nein", "Nein");
		return options;
	}


	@Produces
	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	public void onCustomerSelect(SelectEvent event) {
		User user = (User) event.getObject();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Kunde ausgewählt", user + " wurde ausgewählt!"));
	}
	
}
