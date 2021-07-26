package br.com.ecommerce.wishlist.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ecommerce.product.domain.Product;
import br.com.ecommerce.product.service.ProductService;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistItem;

@Service
public class WishlistItemEnrichService {

	@Autowired
	private ProductService productService;
	
	public WishlistItem enrichProduct(String clientId, String productId) {
		if (productId == null || productId.isEmpty()) return null;
		
		Product product = productService.findProductById(productId);
	
		if (product != null) {
			return WishlistItem.of()
				.setClientId(clientId)
				.setProductId(product.getId())
				.enrichProduct(product);
		}
		return null;
	}
	
}
