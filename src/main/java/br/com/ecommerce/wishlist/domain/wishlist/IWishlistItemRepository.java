package br.com.ecommerce.wishlist.domain.wishlist;

import java.util.List;

public interface IWishlistItemRepository {
	
	List<WishlistItem> findAllProductsByClientId(String clientId);
	WishlistItem findProductByClientId(String clientId, String productId);
	
	Long countByClientId(String clientId);
	Long countByClientIdAndProductId(String clientId, String productId);
}
