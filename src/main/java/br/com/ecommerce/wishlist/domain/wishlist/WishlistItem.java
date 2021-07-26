package br.com.ecommerce.wishlist.domain.wishlist;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.ecommerce.product.domain.Product;

@JsonInclude(value = Include.NON_EMPTY)
@Document(collection = "wishlist_item")
public class WishlistItem {

	@Id
	private String id;
	
	 @Indexed(name = "client_id_index", direction = IndexDirection.ASCENDING)
	private String clientId;
	private String productId;
	private Product product;
	
	private WishlistItem() {}
	
	public static WishlistItem of() {
		return new WishlistItem();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public WishlistItem setClientId(String clientId) {
		this.clientId = clientId;
		return this;
	}
	public String getProductId() {
		return productId;
	}
	public WishlistItem setProductId(String productId) {
		this.productId = productId;
		return this;
	}
	public Product getProduct() {
		return product;
	}
	public WishlistItem enrichProduct(Product product) {
		this.product = product;
		return this;
	}
	
}
