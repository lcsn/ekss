package util.exception;

@SuppressWarnings("serial")
public class PasswordMatchingException extends Exception {

	public PasswordMatchingException() {
		super();
	}
	
	public PasswordMatchingException(String message) {
		super(message==null?"Password does not match!":message);
	}
	
}
