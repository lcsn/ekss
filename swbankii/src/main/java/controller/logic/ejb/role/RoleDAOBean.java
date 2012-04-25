package controller.logic.ejb.role;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import model.entity.Role;

import org.jboss.logging.Logger;

import controller.logic.ejb.common.GenericCRUDBean;

@Stateless
@Remote(RoleDAORemote.class)
public class RoleDAOBean extends GenericCRUDBean<Role> implements RoleDAO {

	private static Logger log = Logger.getLogger(RoleDAOBean.class);

	@Override
	public Role findRoleByRolename(String rolename) {
		log.debug("findRoleByRolename");
		Query query = em.createNamedQuery(Role.FIND_ROLE_BY_ROLENAME);
		query.setParameter("rolename", rolename);
		return (Role) query.getSingleResult();
	}

}