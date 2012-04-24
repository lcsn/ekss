package controller.logic.ejb.common;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import model.entity.common.AbstractSWBankEntity;

import org.jboss.logging.Logger;

public class GenericCRUDBean<T extends AbstractSWBankEntity> implements GenericDAO<T> {
	
	private static Logger log = Logger.getLogger(GenericCRUDBean.class);

	@Inject
	protected EntityManager em;

	@Override
	public T create(T entity) throws Exception {
		log.info("persisting instance of " + entity.getClass());
		em.persist(entity);
		return read(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T read(T entity) {
		log.info("reading instance of " + entity.getClass());
		return (T) em.find(entity.getClass(), entity.getId());
	}

	@Override
	public T update(T entity) {
		log.info("updating instance of " + entity.getClass());
		return em.merge(entity);
	}

	@Override
	public void delete(T entity) {
		log.info("deleting instance of " + entity.getClass());
		em.remove(entity);
	}
	
}
