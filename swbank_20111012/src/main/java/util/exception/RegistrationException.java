package util.exception;

public class RegistrationException extends Exception {

	private Object target;

	public RegistrationException(Object o) {
		this.target = o;
	}
	
	public RegistrationException(String message) {
		super(message);
	}

	public RegistrationException(Throwable cause) {
		super(cause);
	}
	
	public RegistrationException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
