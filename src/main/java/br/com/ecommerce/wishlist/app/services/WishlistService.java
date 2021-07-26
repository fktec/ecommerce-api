package br.com.ecommerce.wishlist.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;

import br.com.ecommerce.client.service.ClientService;
import br.com.ecommerce.wishlist.domain.wishlist.IWishlistRepository;
import br.com.ecommerce.wishlist.domain.wishlist.Wishlist;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistHelper;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistProduct;

@Service
public class WishlistService {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	@Qualifier("wishlistRepository")
	private IWishlistRepository wishlistRepository;
	
	@Autowired
	private WishlistProductEnrichService wishlistEnrichService;
	
	@Autowired
	private ClientService clientService;
	
	
	public Wishlist createWishlist(Wishlist wishlist) {
		return createWishlist(wishlist, null);
	}
	public Wishlist createWishlist(Wishlist wishlist, List<String> productIds) {
		if (wishlist == null) return null;
		
		Wishlist wishlistResponse = wishlistRepository.insert(wishlist);
		
		if (WishlistHelper.hasProducts(productIds))
			saveProductsByClient(wishlist.getClientId(), productIds);
		
		return wishlistResponse;
	}
	
	public Wishlist findWishlistByClient(String clientId) {
		return wishlistRepository.findByClientId(clientId);
	}
	
	public Wishlist findProductByClient(String clientId, String productId) {
		return wishlistRepository.findProductByClientId(clientId, productId);
	}
	
	public void addProductsByClient(String clientId, List<String> productIds) {
		checkIfClientExists(clientId);
		if (!hasWishlistByClient(clientId)) 
			createWishlist(WishlistHelper.generateWishlist(clientId), productIds);
		else
			saveProductsByClient(clientId, productIds);
	}
	
	private UpdateResult saveProductsByClient(String clientId, List<String> productIds) {
		return mongoTemplate.updateFirst(
				Query.query(Criteria.where("clientId").is(clientId)), 
				new Update().addToSet("products").each(wishlistEnrichService.enrichProducts(productIds)), 
				Wishlist.class.getSimpleName().toLowerCase());
	}
	
	public void removeProductsByClient(String clientId, List<String> productIds) {
		checkIfClientExists(clientId);
		if (hasWishlistByClient(clientId) && WishlistHelper.hasProducts(productIds)) {
			mongoTemplate.updateFirst(
					new Query(), 
					new Update().pull("products", Query.query(Criteria.where("productId").in(productIds))), 
					Wishlist.class.getSimpleName().toLowerCase());
		}
	}
	
	private boolean hasWishlistByClient(String clientId) {
		return wishlistRepository.countByClientId(clientId) > 0;
	}
	
	private void checkIfClientExists(String clientId) {
		if (!clientService.clientExists(clientId)) {
			throw new NullPointerException("Client not found");
		}
	}
	
}
