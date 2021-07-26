package br.com.ecommerce.wishlist.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import br.com.ecommerce.client.service.ClientService;
import br.com.ecommerce.wishlist.common.exceptions.ClientNotFoundException;
import br.com.ecommerce.wishlist.domain.wishlist.IWishlistItemRepository;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistDto;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistHelper;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistItem;

@Service
public class WishlistItemService {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	@Qualifier("wishlistItemRepository")
	private IWishlistItemRepository wishlistItemRepository;
	
	@Autowired
	private WishlistItemEnrichService wishlistEnrichService;
	
	@Autowired
	private ClientService clientService;
	
	public List<WishlistItem> findAllProductsByClientId(String clientId) {
		return wishlistItemRepository.findAllProductsByClientId(clientId);
	}
	
	public WishlistItem findProductByClientId(String clientId, String productId) {
		return wishlistItemRepository.findProductByClientId(clientId, productId);
	}
	
	public void addProductsByClient(String clientId, List<String> productIds) throws ClientNotFoundException {
		List<WishlistItem> wishListEnriched = wishlistEnrichService.enrichProducts(WishlistDto.of().setClientId(clientId), productIds);
		
		if (wishListEnriched != null) {
			for (WishlistItem wishlistItem : wishListEnriched) {
				if (hasWishlistItemByClientIdAndProductId(wishlistItem.getClientId(), wishlistItem.getProductId())) {
					update(wishlistItem);
				} else {
					save(wishlistItem);
				}
			}
		}
	}
	
	public void save(WishlistItem wishlistItem) {
		mongoTemplate.insert(wishlistItem);
	}
	
	public void update(WishlistItem currentWishlistItem) {
		WishlistItem oldwishlistItem = wishlistItemRepository.findProductByClientId(currentWishlistItem.getClientId(), currentWishlistItem.getProductId());
		
		if (oldwishlistItem != null) {
			currentWishlistItem.setId(oldwishlistItem.getId());
			mongoTemplate.save(currentWishlistItem, WishlistItem.DOCUMENT_NAME);
		}
	}
	
	public void removeProductsByClient(String clientId, List<String> productIds) {
		if (WishlistHelper.hasProducts(productIds)) {
			mongoTemplate.remove(
					Query.query(Criteria
							.where("clientId").is(clientId)
							.and("productId").in(productIds)), 
					WishlistItem.class);
		}
	}
	
	private boolean hasWishlistItemByClientIdAndProductId(String clientId, String productId) {
		return wishlistItemRepository.countByClientIdAndProductId(clientId, productId) > 0;
	}
	
	public void checkIfClientExists(String clientId) throws ClientNotFoundException {
		if (!clientService.clientExists(clientId)) {
			throw new ClientNotFoundException(clientId);
		}
	}
	
}
