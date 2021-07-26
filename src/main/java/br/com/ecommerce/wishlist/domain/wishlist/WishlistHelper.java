package br.com.ecommerce.wishlist.domain.wishlist;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WishlistHelper {
	
	private WishlistHelper() {}
	
	public static Wishlist generateWishlist(String clientId) {
		return generateWishlist(clientId, null);
	}
	public static Wishlist generateWishlist(String clientId, List<WishlistProduct> products) {
		return Wishlist.of()
				.setClientId(clientId)
				.setProducts(products);
	}
	
	public static List<String> generateProductIds(List<WishlistProduct> wishlistProducts) {
		if (wishlistProducts == null || wishlistProducts.isEmpty()) return Collections.emptyList();
		return wishlistProducts
				.stream()
				.map(WishlistProduct::getProductId)
				.collect(Collectors.toList());
	}
	
	public static boolean hasProducts(List<String> productIds) {
		return productIds != null && !productIds.isEmpty();
	}
}
