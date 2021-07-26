package br.com.ecommerce.wishlist.domain.wishlist;

public class WishlistDto  {

	private String clientId;
	
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

}
