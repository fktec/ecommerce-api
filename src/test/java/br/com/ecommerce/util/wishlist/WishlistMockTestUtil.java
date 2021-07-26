package br.com.ecommerce.util.wishlist;

import org.springframework.core.io.ClassPathResource;

import br.com.ecommerce.mock.util.ResourceReader;

public class WishlistMockTestUtil {

	private static final String WISHLIST_PATH = "mocks/wishlist/";
	
	private WishlistMockTestUtil() {}

	public static final String wishlistItemResponseJSON =
			ResourceReader.asString(new ClassPathResource(WISHLIST_PATH + "payload-success-array_WISHLISTITEM.json"));
	
	public static final String wishlistItemEnrichedResponseJSON =
			ResourceReader.asString(new ClassPathResource(WISHLIST_PATH + "payload-success-array_WISHLISTITEM_ENRICHED.json"));
}
