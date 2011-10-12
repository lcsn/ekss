package controller;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class GenericDAO {

	@Inject
	protected EntityManager em;
	
}
