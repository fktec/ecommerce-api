package br.com.ecommerce.wishlist.common.exceptions;

public class WishlistItemMaxCapacityException extends Exception {

	private static final long serialVersionUID = 1L;

	public	WishlistItemMaxCapacityException() {
		super("Cannot add new products to favorites list");
	}
	
}
