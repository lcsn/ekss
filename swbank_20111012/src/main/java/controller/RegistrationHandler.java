package controller;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

@Named("registrationHandler")
@RequestScoped
@Stateful
public class RegistrationHandler extends GenericDAO {

	@Inject
	@Category("swbank")
	private Logger log;
	
}
