package br.com.ecommerce.wishlist.infra.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import br.com.ecommerce.mock.util.JsonFormatterComplete;
import br.com.ecommerce.util.wishlist.WishlistMockTestUtil;
import br.com.ecommerce.wishlist.domain.wishlist.IWishlistItemRepository;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistItem;

@ExtendWith(MockitoExtension.class)
@TestPropertySource({ "classpath:application.properties" })
public class WishlistItemRepositoryTest  {
	
	@Mock
	private IWishlistItemRepository wishlistItemRepository;
	
	@Test
	public void tA1_testFindAllByClientId_Success() {
	    // # MOCK		
		String clientId = "1";
		List<WishlistItem> wishlistItemsResponse = JsonFormatterComplete.jsonToArray(WishlistMockTestUtil.wishlistItemResponseJSON, WishlistItem.class);
		
	    Mockito
	    	.when(wishlistItemRepository.findAllProductsByClientId(clientId))
	    	.thenReturn(wishlistItemsResponse);
	    
	    // # TEST
	    String clientIdRequest = clientId;
	    
	    List<WishlistItem> response = wishlistItemRepository.findAllProductsByClientId(clientIdRequest);
	    List<WishlistItem> expected = wishlistItemsResponse;

	    assertNotNull(response);
	    assertEquals(expected, response);
	    assertEquals(2, expected.size());
	    
	    Mockito.verify(wishlistItemRepository, times(1)).findAllProductsByClientId(clientIdRequest);
	}
	
	@Test
	public void tB1_testFindProductByClientId_Success() {
	    // # MOCK		
		String clientId = "1";
		String productId = "2";
		
		List<WishlistItem> wishlistItemsResponse = JsonFormatterComplete.jsonToArray(WishlistMockTestUtil.wishlistItemResponseJSON, WishlistItem.class);
		
	    Mockito
	    	.when(wishlistItemRepository.findProductByClientId(clientId, productId))
	    	.thenReturn(wishlistItemsResponse.get(0));
	    
	    // # TEST
	    String clientIdRequest = clientId;
	    String productIdRequest = productId;
	    
	    WishlistItem response = wishlistItemRepository.findProductByClientId(clientIdRequest, productIdRequest);
	    WishlistItem expected = wishlistItemsResponse.get(0);

	    assertNotNull(response);
	    assertEquals(expected, response);
	    
	    Mockito.verify(wishlistItemRepository, times(1)).findProductByClientId(clientIdRequest, productIdRequest);
	}
	
	@org.junit.jupiter.api.Test
	public void tC1_testCountByClientId_Success() {
	    // # MOCK		
		String clientId = "1";
		
	    Mockito
	    	.when(wishlistItemRepository.countByClientId(clientId))
	    	.thenReturn(1l);
	    
	    // # TEST
	    String clientIdRequest = clientId;
	    
	    Long response = wishlistItemRepository.countByClientId(clientIdRequest);
	    Long expected = 1l;

	    assertNotNull(response);
	    assertEquals(expected, response);
	    
	    Mockito.verify(wishlistItemRepository, times(1)).countByClientId(clientIdRequest);
	}
	
	@Test
	public void tD1_testFindProductByClientId_Success() {
	    // # MOCK		
		String clientId = "1";
		String productId = "2";
		
	    Mockito
	    	.when(wishlistItemRepository.countByClientIdAndProductId(clientId, productId))
	    	.thenReturn(1l);
	    
	    // # TEST
	    String clientIdRequest = clientId;
	    String productIdRequest = productId;
	    
	    Long response = wishlistItemRepository.countByClientIdAndProductId(clientIdRequest, productIdRequest);
	    Long expected = 1l;

	    assertNotNull(response);
	    assertEquals(expected, response);
	    
	    Mockito.verify(wishlistItemRepository, times(1)).countByClientIdAndProductId(clientId, productId);
	}

}
