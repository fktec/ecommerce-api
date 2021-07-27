package br.com.ecommerce.wishlist.domain.wishlist;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_EMPTY)
public class WishlistItemDto {

	@NotBlank
	private String productId;
	
	private WishlistItemDto() {}
	
	public static WishlistItemDto of() {
		return new WishlistItemDto();
	}
	
	public String getProductId() {
		return productId;
	}
	public WishlistItemDto setProductId(String productId) {
		this.productId = productId;
		return this;
	}
	
}
