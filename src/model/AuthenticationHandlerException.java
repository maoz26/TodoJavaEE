/**
 * 
 */
package model;

/**
 * Deals with authentication
 *
 */
public class AuthenticationHandlerException extends Exception {
	/**
	 * constructor
	 * @param msg
	 */
	public AuthenticationHandlerException(String msg) {
		super(msg);
	}
	/**
	 * AuthenticationHandlerException 
	 * @param msg
	 * @param throwable
	 */
	public AuthenticationHandlerException(String msg, Throwable throwable) {
		super(msg,throwable);
	}
}
