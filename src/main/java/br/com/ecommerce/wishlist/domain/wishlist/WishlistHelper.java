package br.com.ecommerce.wishlist.domain.wishlist;

public class WishlistHelper {
	
	private WishlistHelper() {}
	
	public static boolean hasProduct(String productId) {
		return productId != null && !productId.isEmpty();
	}
}
