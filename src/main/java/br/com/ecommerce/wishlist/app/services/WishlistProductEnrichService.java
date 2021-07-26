package br.com.ecommerce.wishlist.app.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ecommerce.product.domain.Product;
import br.com.ecommerce.product.service.ProductService;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistProduct;

@Service
public class WishlistProductEnrichService {

	@Autowired
	private ProductService productService;
	
	public List<WishlistProduct> enrichProducts(List<String> productIds) {
		if (productIds == null || productIds.isEmpty()) return Collections.emptyList();
		
		List<Product> products = productService.findProductsByIds(productIds);
		List<WishlistProduct> wishlistProductsEnriched = new ArrayList<>();
		
		if (products != null) {
			for (String productId : productIds) {
				for (Product product : products) {
					if (productId.equals(product.getId())) {
						wishlistProductsEnriched.add(WishlistProduct.of()
								.setProductId(productId)
								.enrichProduct(product));
						break;
					}
				}
			}
		}
		return wishlistProductsEnriched;
	}
	
}
