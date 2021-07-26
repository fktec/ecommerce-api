package br.com.ecommerce.wishlist.common.exceptions;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	public	ValidationException(String message) {
		super(message);
	}
	
	public String getMessage() {
		return this.getMessage();
	}
}
