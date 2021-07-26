package br.com.ecommerce.wishlist.domain.wishlist;

import java.util.List;

public class WishlistDto  {

	private String clientId;
	private List<String> productIds;
	
	private WishlistDto() {}
	
	public static WishlistDto of() {
		return new WishlistDto();
	}
	
	public String getClientId() {
		return clientId;
	}
	public WishlistDto setClientId(String clientId) {
		this.clientId = clientId;
		return this;
	}

	public List<String> getProductIds() {
		return productIds;
	}
	public WishlistDto setProductIds(List<String> productIds) {
		this.productIds = productIds;
		return this;
	}

}
