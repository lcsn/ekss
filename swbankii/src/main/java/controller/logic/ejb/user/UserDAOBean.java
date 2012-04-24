package controller.logic.ejb.user;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import model.entity.user.User;

import org.jboss.logging.Logger;

import controller.logic.ejb.common.GenericCRUDBean;

@Stateless
@Remote(UserDAORemote.class)
public class UserDAOBean extends GenericCRUDBean<User> implements UserDAO {

	private static Logger log = Logger.getLogger(UserDAOBean.class);

	@Override
	public User findUserByEmail(String email) {
		log.debug("findUserByEmail");
		Query query = em.createNamedQuery(User.FIND_BY_EMAIL);
		query.setParameter("email", email);
		return (User) query.getSingleResult();
	}

	@Override
	public User findUserByUsername(String username) {
		log.debug("findUserByUsername");
		Query query = em.createNamedQuery(User.FIND_BY_USERNAME);
		query.setParameter("username", username);
		return (User) query.getSingleResult();
	}

}