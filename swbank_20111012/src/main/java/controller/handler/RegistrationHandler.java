package controller.handler;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Address;
import model.Credential;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import util.exception.VerificationException;
import controller.service.AddressService;
import controller.service.PasswordService;
import controller.service.UserService;

@SuppressWarnings("serial")
@Named("registrationHandler")
@RequestScoped
public class RegistrationHandler implements Serializable {

	@Inject
	@Category("registrationHandler")
	private Logger log;
	
	private User newUser;
	
	private Credential newCredentials;
	
	private Address newAddress;
	
	@Inject
	private PasswordService service;
	
	@Inject
	private UserService userService;
	
	@Inject
	private AddressService addressService;
	
	@Inject
	private ErrorHandler errorHandler;
	
	public User getNewUser() {
		return newUser;
	}
	
	public Credential getNewCredentials() {
		return newCredentials;
	}
	
	public Address getNewAddress() {
		return newAddress;
	}
	
	@PostConstruct
	public void init() {
		this.newCredentials = new Credential();
		this.newUser = new User();
		this.newAddress = new Address();
	}
	
	public String doRegister() {
		log.info("register");
		try {
			if(newCredentials.verify()) {
				String hash = service.encrypt(newCredentials.getPass());
				newCredentials.setPass(hash);
				newUser.setCredentials(newCredentials);
				userService.createUser(newUser);
				
				newAddress.setUser(newUser);
				addressService.createAddress(newAddress);
				
				log.trace(newUser + " has registered!");
				return "success";
			}
		} catch (VerificationException e) {
			errorHandler.setException(e);
			log.error(e);
		} catch (Exception e) {
			errorHandler.setException(e);
			log.error(e);
		}
		log.warn("registering " + newUser + " failed!");
		return "failure";
	}
}
