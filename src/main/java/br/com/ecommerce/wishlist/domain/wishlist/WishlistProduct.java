package br.com.ecommerce.wishlist.domain.wishlist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.ecommerce.product.domain.Product;

@JsonInclude(value = Include.NON_EMPTY)
public class WishlistProduct {

	private String productId;
	private Product product;
	
	private WishlistProduct() {}
	
	public static WishlistProduct of() {
		return new WishlistProduct();
	}
	
	public String getProductId() {
		return productId;
	}
	public WishlistProduct setProductId(String productId) {
		this.productId = productId;
		return this;
	}
	public Product getProduct() {
		return product;
	}
	public WishlistProduct enrichProduct(Product product) {
		this.product = product;
		return this;
	}
	
}
