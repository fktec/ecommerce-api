package br.com.ecommerce.wishlist.common.exceptions;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WishlistItemMaxCapacityException extends ResponseStatusException {

	private static final long serialVersionUID = 1L;
	private static final String message = "Cannot add new products to favorites list - limit [{0}]";
	private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

	public WishlistItemMaxCapacityException() {
		super(httpStatus);
	}
	public WishlistItemMaxCapacityException(Integer maxCapacity) {
		super(httpStatus, MessageFormat.format(message, maxCapacity));
	}
	public WishlistItemMaxCapacityException(Integer maxCapacity, java.lang.Throwable cause) {
		super(httpStatus,  MessageFormat.format(message, maxCapacity), cause);
	}
	
}
