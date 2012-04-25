package controller.logic.ejb.user;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.entity.User;

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

	@Override
	public List<User> findAll() {
//		log.debug("findAll");
//		Query query = em.createNamedQuery(User.FIND_ALL);
//		return query.getResultList();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> userRoot = criteria.from(User.class);
		criteria.select(userRoot);
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public User findUserById(String userId) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> userRoot = criteria.from(User.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("id"), userId));
		return em.createQuery(criteria).getSingleResult();
	}

}