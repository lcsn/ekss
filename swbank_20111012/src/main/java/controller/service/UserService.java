package controller.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;

import model.Credential;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

@Named
@Stateless
public class UserService extends GenericService {

	@Inject
	@Category("userservice")
	private Logger log;

	public User createUser(User user) throws Exception {
		log.trace("createUser");
		em.persist(user);
		em.flush();
		return em.find(User.class, user.getId());
	}
	
	public User updateUser(User user) {
		log.trace("updateUser");
		if (user.getId() != null) {
			em.merge(user);
			em.flush();
			return em.find(User.class, user.getId());
		}
		log.info("User is not persistent.");
		return user;
	}
	
	public User findUserById(Long userId) throws Exception {
		log.trace("findUserById");
		Query q = em.createNamedQuery(User.FIND_BY_ID);
		q.setParameter("userId", userId);
		return (User) q.getSingleResult();
	}
	
	public User findUserByCredentials(Credential credentials) throws Exception {
		log.trace("findUserByCredentials");
		Query q = em.createNamedQuery(User.FIND_BY_CREDENTIALS);
		q.setParameter("credentials", credentials);
		return (User) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<User> findUsers() {
		log.trace("findUsers");
		Query q = em.createNamedQuery(User.FIND_USERS);
		return (List<User>) q.getResultList();
	}

	public void confirm(Long userId) {
		log.trace("Confirm User..");
		try {
			User user = findUserById(userId);
			user.setConfirmed(true);
			updateUser(user);
			log.trace(user + " confirmed!");
		} catch (Exception e) {
			log.error(e);
		}

	}
	
}
