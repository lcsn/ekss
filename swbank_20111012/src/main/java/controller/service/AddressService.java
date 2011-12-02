package controller.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import model.Address;
import model.User;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

@Stateless
public class AddressService extends GenericService {
	
	@Inject
	@Category("addressservice")
	private Logger log;
	
	public Address createAddress(Address address) {
		log.trace("createAddress");
		em.persist(address);
		em.flush();
		return em.find(Address.class, address.getId());
	}

	public Address updateAddress(Address address) {
		log.trace("updateAddress");
		if (address.getId() != null) {
			em.merge(address);
			em.flush();
			return em.find(Address.class, address.getId());
		}
		log.info("Address is not persistent.");
		return address;
	}
	
	@SuppressWarnings("unchecked")
	public List<Address> findAddressesByUser(User user) throws Exception {
		log.trace("findAddressByUser");
		Query q = em.createNamedQuery(Address.FIND_BY_USER);
		q.setParameter("user", user);
		return (List<Address>) q.getResultList();
	}
	
	public Address findDefaultAddressByUser(User user) throws Exception {
		log.trace("findDefaultAddressByUser");
		Query q = em.createNamedQuery(Address.FIND_DEFAULTADDRESS_BY_USER);
		q.setParameter("user", user);
		return (Address) q.getSingleResult();
	}
	
}
