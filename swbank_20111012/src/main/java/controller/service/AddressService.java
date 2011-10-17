package controller.service;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.Address;
import model.Credential;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

@Named
//@Dependent
@Stateless
public class AddressService extends GenericService {
	
	@Inject
	@Category("swbank_20111012")
	private Logger log;
	
	public Address createAddress(Address address) {
		log.trace("persist address..");
		em.persist(address);
		em.flush();
		return em.find(Address.class, address.getId());
	}
	
	public Address findAddressByUserId(Long userId) throws Exception {
		return findAddressByUser(em.find(User.class, userId));
	}
	
	public Address findAddressByUser(User user) throws Exception {
		Query q = em.createNamedQuery(Address.FIND_BY_USER);
		q.setParameter("user", user);
		return (Address) q.getSingleResult();
	}
	
}
