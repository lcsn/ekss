package controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import model.Visitor;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

@Named("visitorDAOBean")
@RequestScoped
@Stateful
public class VisitorDAOBean {

	@Inject
	@Category("hello")
	private Logger log;

	@Inject
	private EntityManager em;

	private Visitor newVisitor;

	@Produces
	@RequestScoped
	@Named
	public Visitor getNewVisitor() {
		return newVisitor;
	}

	public String sayHello() {
		log.info("Registering " + newVisitor.getName());
		em.persist(newVisitor);
		initNewVisitor();
		return "sayHello";
	}

	@Produces
	@Named
	@RequestScoped
	public Visitor getVisitor() throws Exception {
		Long id = (Long) em.createQuery("select max(v.id) from Visitor v").getSingleResult();
		List<Visitor> results = em.createQuery("select v from Visitor v where v.id=:id").setParameter("id", id).getResultList();
		if (results.isEmpty()) {
			return null;
		} else if (results.size() > 1) {
			throw new IllegalStateException("Cannot have more than one user with the same username!");
		} else {
			return results.get(0);
		}
	}

	@PostConstruct
	public void initNewVisitor() {
		newVisitor = new Visitor();
	}

}
