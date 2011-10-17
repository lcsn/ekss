package util.exception;

public class VerificationException extends Exception {

	private static final long serialVersionUID = -5660028232260198541L;
	
	private String msg;

	public VerificationException(Object o, Object o1) {
		this.msg = o1 + " does not match " + o1;
	}
	
	public VerificationException(String message) {
		super(message);
	}

	public VerificationException(Throwable cause) {
		super(cause);
	}
	
	public VerificationException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
