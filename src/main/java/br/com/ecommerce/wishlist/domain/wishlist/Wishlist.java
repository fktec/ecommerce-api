package br.com.ecommerce.wishlist.domain.wishlist;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_EMPTY)
@Document(collection = "wishlist")
public class Wishlist  {

	private String id;
	private String clientId;
	private List<WishlistProduct> products;
	
	private Wishlist() {}
	
	public static Wishlist of() {
		return new Wishlist();
	}
	
	public String getId() {
		return id;
	}
	public Wishlist setId(String id) {
		this.id = id;
		return this;
	}
	public String getClientId() {
		return clientId;
	}
	public Wishlist setClientId(String clientId) {
		this.clientId = clientId;
		return this;
	}
	public List<WishlistProduct> getProducts() {
		return products;
	}
	public Wishlist setProducts(List<WishlistProduct> products) {
		this.products = products;
		return this;
	}

}
