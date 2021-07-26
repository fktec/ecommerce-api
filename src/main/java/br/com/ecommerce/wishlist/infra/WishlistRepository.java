package br.com.ecommerce.wishlist.infra;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ecommerce.wishlist.domain.wishlist.IWishlistRepository;
import br.com.ecommerce.wishlist.domain.wishlist.Wishlist;

@Repository(value = "wishlistRepository")
public interface WishlistRepository extends MongoRepository<Wishlist, String>, IWishlistRepository  {

	@Query(value = "{ 'clientId' : ?0 }")
	Wishlist findByClientId(String clientId);
	
	@Query(value = "{'code': ?0, 'phones.subscribeNumber': {$in: [?1]}}")
	Wishlist findProductByClientId(String clientId, String productId);
	
	@Query(value = "{ 'clientId' : ?0 }", count = true)
	Long countByClientId(String clientId);
}
