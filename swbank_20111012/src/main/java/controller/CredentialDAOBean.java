package controller;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Credential;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;


@Named
@RequestScoped
@Stateful
public class CredentialDAOBean extends GenericDAO {
	
	@Inject
	@Category("hello")
	private Logger log;
	
	@Inject
	private PasswordService pwService;

	public void createCredential(Credential c) {
		String pass = c.getPass();
		c.setPass(pwService.encrypt(pass));
		em.persist(c);
	}
	
	public void findCredentialByIdentityAndPass(String identity, String pass) {
		
	}
	
}
