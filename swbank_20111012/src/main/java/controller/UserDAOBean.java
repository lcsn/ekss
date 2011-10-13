package controller;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;

import model.Credential;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

@Named
@RequestScoped
@Stateful
public class UserDAOBean extends GenericDAO {

	@Inject
	@Category("swbank_20111012")
	private Logger log;

	public User createUser(User user) {
		log.trace("persist user..");
		em.persist(user);
		em.flush();
		return em.find(User.class, user);
	}
	
	public User findUserByCredentials(Credential credentials) {
		Query q = em.createNamedQuery(User.FIND_BY_CREDENTIALS);
		q.setParameter("credentials", credentials);
		return (User) q.getSingleResult();
	}
}
