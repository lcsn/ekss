package controller.handler;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@SuppressWarnings("serial")
@Named("errorHandler")
@ApplicationScoped
public class ErrorHandler implements Serializable {

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
