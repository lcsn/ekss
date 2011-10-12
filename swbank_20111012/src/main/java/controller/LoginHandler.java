package controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;

import model.Credential;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;


@Named("loginHandler")
@SessionScoped
@Stateful
public class LoginHandler extends GenericDAO {

	@Inject
	@Category("hello")
	private Logger log;

	private User currentUser;
	
	private Credential credentials;

	@Produces
	@SessionScoped
	@Named
	public Credential getCredentials() {
		return credentials;
	}
	
	@PostConstruct
	public void init() {
		credentials = new Credential();
	}
	
	public String doLogin() {
		log.trace("login..");
		Query query4credentials = em.createQuery("select c from Credential c where c.identity=:identity and c.pass=:pass");
		query4credentials.setParameter("identity", credentials.getIdentity());
		query4credentials.setParameter("pass", credentials.getPass());
		List res = query4credentials.getResultList();
		Credential c = (Credential) res.get(0);
		
		if(c != null) {
			Query query = em.createQuery("select u from User u where u.credentials=:credentials");
			query.setParameter("credentials", c);
			List<User> results = query.getResultList();
			if (results.isEmpty()) {
				return "failure";
			} else if (results.size() > 1) {
				throw new IllegalStateException("Illegal state: More than one user found!");
			} else {
				this.currentUser = results.get(0);
				return "success";
			}
		}
		return "failure";
	}
	
	@Produces
	public User getCurrentUser() {
		return currentUser;
	}
	
}
