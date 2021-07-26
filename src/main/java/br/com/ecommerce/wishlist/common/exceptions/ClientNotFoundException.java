package br.com.ecommerce.wishlist.common.exceptions;

import java.text.MessageFormat;

public class ClientNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public	ClientNotFoundException(String clientId) {
		super(MessageFormat.format("Client not found by id [{0}]", clientId));
	}
	
	public String getMessage() {
		return this.getMessage();
	}
}
