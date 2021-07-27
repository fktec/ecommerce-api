package br.com.ecommerce.wishlist.common.exceptions;

import java.text.MessageFormat;

public class ProductNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public	ProductNotFoundException(String productId) {
		super(MessageFormat.format("Product not found by id [{0}]", productId));
	}
	
}
