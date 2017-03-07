package model;

/**
 * Deals with authentication
 * Created by maoztamir on 31/12/2016.
 */
public class AuthenticationHandlerException extends Exception {

	/**
	 * @param msg constructor
	 */
	public AuthenticationHandlerException(String msg) {
		super(msg);
	}

	/**
	 * AuthenticationHandlerException 
	 * @param msg the exception msg
	 * @param throwable default
	 */
	public AuthenticationHandlerException(String msg, Throwable throwable) {
		super(msg,throwable);
	}
}
