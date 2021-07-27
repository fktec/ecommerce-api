package br.com.ecommerce.wishlist.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import br.com.ecommerce.client.service.ClientService;
import br.com.ecommerce.wishlist.common.exceptions.ClientNotFoundException;
import br.com.ecommerce.wishlist.common.exceptions.ProductNotFoundException;
import br.com.ecommerce.wishlist.common.exceptions.WishlistItemMaxCapacityException;
import br.com.ecommerce.wishlist.domain.wishlist.IWishlistItemRepository;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistHelper;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistItem;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistItemDto;

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
	
	@Value("${rn.wishlist.item.max-capacity}")
	private Integer wishlistItemMaxCapacity;
	
	public List<WishlistItem> findAllProductsByClientId(String clientId) {
		return wishlistItemRepository.findAllProductsByClientId(clientId);
	}
	
	public WishlistItem findProductByClientId(String clientId, String productId) {
		return wishlistItemRepository.findProductByClientId(clientId, productId);
	}
	
	public WishlistItem addProductByClient(String clientId, WishlistItemDto wishlistItemDto) throws ProductNotFoundException, WishlistItemMaxCapacityException {
		if (wishlistItemDto == null) return null;
		checkIfMaxCapacity(clientId);
		return persist(wishlistEnrichService.enrichProduct(clientId, wishlistItemDto.getProductId()));
	}

	private WishlistItem persist(WishlistItem wishlistItemEnriched) {
		if (wishlistItemEnriched != null) {
			if (hasWishlistItemByClientIdAndProductId(wishlistItemEnriched.getClientId(), wishlistItemEnriched.getProductId())) {
				return update(wishlistItemEnriched);
			} else {
				return save(wishlistItemEnriched);
			}
		}
		return null;
	}
	
	public WishlistItem save(WishlistItem wishlistItem) {
		return mongoTemplate.insert(wishlistItem);
	}
	
	public WishlistItem update(WishlistItem currentWishlistItem) {
		WishlistItem oldwishlistItem = wishlistItemRepository.findProductByClientId(currentWishlistItem.getClientId(), currentWishlistItem.getProductId());
		
		if (oldwishlistItem != null) {
			currentWishlistItem.setId(oldwishlistItem.getId());
			return mongoTemplate.save(currentWishlistItem, WishlistItem.DOCUMENT_NAME);
		}
		return null;
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
	
	public void checkIfMaxCapacity(String clientId) throws WishlistItemMaxCapacityException {
		if (wishlistItemMaxCapacity <= wishlistItemRepository.countByClientId(clientId)) {
			throw new WishlistItemMaxCapacityException(wishlistItemMaxCapacity);
		}
	}
	
}
