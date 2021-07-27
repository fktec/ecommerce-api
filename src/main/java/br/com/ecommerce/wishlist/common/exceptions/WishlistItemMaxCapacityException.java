package br.com.ecommerce.wishlist.common.exceptions;

import java.text.MessageFormat;

public class WishlistItemMaxCapacityException extends Exception {

	private static final long serialVersionUID = 1L;

	public	WishlistItemMaxCapacityException(Integer maxCapacity) {
		super(MessageFormat.format("Cannot add new products to favorites list - limit [{0}]", maxCapacity));
	}
	
}
