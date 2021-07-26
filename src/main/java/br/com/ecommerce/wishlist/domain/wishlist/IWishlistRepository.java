package br.com.ecommerce.wishlist.domain.wishlist;

public interface IWishlistRepository {
	
	Wishlist insert(Wishlist wishlist);

	Wishlist findByClientId(String clientId);
	Wishlist findProductByClientId(String clientId, String productId);
	
	Long countByClientId(String clientId);
}
