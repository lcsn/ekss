package controller.handler;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@Singleton
@ApplicationScoped
@Startup
public class ErrorHandler {

	private static ErrorHandler instance;

	private Exception exception;

	@Named
	@Produces
	public Exception getException() {
		if (this.exception != null) {
			return exception;
		}
		return null;
	}
	
	public void setException(Exception exception) {
		this.exception = exception;
	}
	
	public static ErrorHandler getInstance() {
		if(instance == null) {
			instance = new ErrorHandler();
		}
		return instance;
	}
	
}
