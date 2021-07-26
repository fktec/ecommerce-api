package br.com.ecommerce.wishlist.domain.wishlist;

import java.util.List;

public class WishlistHelper {
	
	private WishlistHelper() {}
	
	public static boolean hasProducts(List<String> productIds) {
		return productIds != null && !productIds.isEmpty();
	}
}
