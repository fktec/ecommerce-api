package br.com.ecommerce.wishlist.infra.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ecommerce.wishlist.domain.wishlist.IWishlistItemRepository;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistItem;

@Repository(value = "wishlistItemRepository")
public interface WishlistItemRepository extends MongoRepository<WishlistItem, String>, IWishlistItemRepository  {

	@Query(value = "{ 'clientId' : ?0 }")
	List<WishlistItem> findAllProductsByClientId(String clientId);
	
	@Query(value = "{ 'clientId' : ?0, 'productId': ?1 }")
	WishlistItem findProductByClientId(String clientId, String productId);
	
	@Query(value = "{ 'clientId' : ?0 }", count = true)
	Long countByClientId(String clientId);
	
	@Query(value = "{ 'clientId' : ?0, 'productId': ?1 }", count = true)
	Long countByClientIdAndProductId(String clientId, String productId);
}
