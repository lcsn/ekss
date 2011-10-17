package controller.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;

import model.Credential;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

@Named
@Stateless
public class CredentialService extends GenericService {
	
	@Inject
	@Category("swbank_20111012")
	private Logger log;
	
	@Inject
	private PasswordService pwService;

	public Credential createCredential(Credential credentials) throws Exception {
		log.trace("createCredential");
		String pass = credentials.getPass();
		credentials.setPass(pwService.encrypt(pass));
		em.persist(credentials);
		em.flush();
		return em.find(Credential.class, credentials.getId());
	}
	
	public Credential findCredentialByIdentityAndPass(String identity, String pass) throws Exception {
		log.trace("findCredentialByIdentityAndPass");
		Query q = em.createNamedQuery(Credential.FIND_BY_IDENTITY_AND_PASS);
		q.setParameter("identity", identity);
		q.setParameter("pass", pass);
		return (Credential) q.getSingleResult();
	}
	
}
