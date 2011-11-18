package controller.handler;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import model.Account;
import model.Address;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;
import org.primefaces.event.SelectEvent;

import util.Role;
import controller.service.AccountService;
import controller.service.AddressService;
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
	
	@Inject
	private AccountService accountService;

	@Inject
	private AddressService addressService;
	
	private User selectedUser;
	
	private Account selectedAccount;
	
	private Address selectedAddress;
	
	private List<User> users;
	
	private List<Address> userAddresses;

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
		this.selectedUser = null;
		this.selectedAccount = null;
	}
	
	public List<User> getCustomers() {
		if (this.users == null) {
			this.users = loadUsers();
		}
		return users;
	}

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
	
	public SelectItem[] getActivOptions() {
		SelectItem[] options = new SelectItem[3]; 
		options[0] = new SelectItem("", "Bitte wählen"); 
		options[1] = new SelectItem("Ja", "Ja"); 
		options[2] = new SelectItem("Nein", "Nein");
		return options;
	}


	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
		this.selectedAccount = null;
		this.selectedAddress = null;
		try {
			this.userAddresses = addressService.findAddressesByUser(selectedUser);
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	public Account getSelectedAccount() {
		return selectedAccount;
	}

	public void setSelectedAccount(Account selectedAccount) {
		this.selectedAccount = selectedAccount;
	}

	public Address getSelectedAddress() {
		return selectedAddress;
	}

	public void setSelectedAddress(Address selectedAddress) {
		this.selectedAddress = selectedAddress;
	}

	public List<Address> getUserAddresses() {
		return userAddresses;
	}

	public void setUserAddresses(List<Address> userAddresses) {
		this.userAddresses = userAddresses;
	}

	public void onCustomerSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Kunde ausgewählt", event.getObject() + " wurde ausgewählt!"));
	}
	
	public void onAccountSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Konto ausgewählt", event.getObject() + " wurde ausgewählt!"));
	}
	
	public void onAddressSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Konto ausgewählt", event.getObject() + " wurde ausgewählt!"));
	}
	
	public void activate(ActionEvent event) {
		log.info("activate");
		try {
			if (selectedAccount != null) {
				if (selectedAccount.isActiv()) {
					selectedAccount.setActiv(false);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Konto deaktiviert", selectedAccount + " wurde deaktiviert!"));
				} else {
					selectedAccount.setActiv(true);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Konto aktiviert", selectedAccount + " wurde aktiviert!"));
				}
				accountService.updateAccount(selectedAccount);
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Kein Konto ausgewählt!", "Bitte wählen sie zuerst ein Konto!"));
		} catch (Exception e) {
			log.error(e);
		}
	}
	
}
