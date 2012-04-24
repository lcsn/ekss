package controller.logic.ejb.common;

public interface GenericDAO<T> {

	public T create(T entity) throws Exception;
	
	public T read(T entity);
	
	public T update(T entity);

	public void delete(T entity);
	
}
