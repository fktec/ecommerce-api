package br.com.ecommerce.wishlist.app.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ecommerce.product.domain.Product;
import br.com.ecommerce.product.service.ProductService;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistDto;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistItem;

@Service
public class WishlistItemEnrichService {

	@Autowired
	private ProductService productService;
	
	public List<WishlistItem> enrichProducts(WishlistDto wishlistDto) {
		if (wishlistDto.getProductIds() == null || wishlistDto.getProductIds().isEmpty()) return Collections.emptyList();
		
		List<Product> products = productService.findProductsByIds(wishlistDto.getProductIds());
		List<WishlistItem> wishlistItemsEnriched = new ArrayList<>();
		
		if (products != null) {
			for (Product product : products) {
				wishlistItemsEnriched.add(WishlistItem.of()
						.setClientId(wishlistDto.getClientId())
						.setProductId(product.getId())
						.enrichProduct(product));
			}
		}
		return wishlistItemsEnriched;
	}
	
}
