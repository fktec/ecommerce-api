package br.com.ecommerce.wishlist.common.exceptions;

import java.text.MessageFormat;

public class DuplicateWishlistException extends Exception {

	private static final long serialVersionUID = 1L;

	public	DuplicateWishlistException(String clientId) {
		super(MessageFormat.format("There is already a wish list for this customer [{0}]", clientId));
	}
}
