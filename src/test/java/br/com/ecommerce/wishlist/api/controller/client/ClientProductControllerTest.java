//package br.com.ecommerce.wishlist.api.controller.client;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Assert;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.test.context.TestPropertySource;
//
//import br.com.ecommerce.mock.util.JsonFormatterComplete;
//import br.com.ecommerce.util.wishlist.WishlistMockTestUtil;
//import br.com.ecommerce.wishlist.app.services.WishlistItemService;
//import br.com.ecommerce.wishlist.domain.wishlist.WishlistItem;
//import br.com.ecommerce.wishlist.infra.repository.WishlistItemRepository;
//
//@RunWith(MockitoJUnitRunner.class)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@TestPropertySource({ "classpath:application.properties" })
//public class ClientProductControllerTest  {
//	
//	@InjectMocks
//	private WishlistItemService wishlistService; 
//	
//	@Mock
//	private WishlistItemRepository wishlistRepository;
//	
//	
//	@Test
//	public void tA1_findAllByClientId_Success() {
//	    // # MOCK		
//		String clientId = "1";
//		List<WishlistItem> wishlistItemsResponse = JsonFormatterComplete.jsonToArray(WishlistMockTestUtil.wishlistItemResponseJSON, WishlistItem.class);
//		
//	    Mockito
//	    	.when(wishlistRepository.findAllProductsByClientId(clientId))
//	    	.thenReturn(wishlistItemsResponse);
//	    
//	    // # TEST
//	    String clientIdRequest = null;
//	    
//	    List<WishlistItem> response = wishlistService.findAllProductsByClientId(clientIdRequest);
//	    List<WishlistItem> expected = new ArrayList<>();;
//
//	    Assert.assertNotNull(expected);
//	    Assert.assertEquals(0, expected.size());
//	    Assert.assertEquals(expected, response);
//	    
//	    Mockito.verify(wishlistRepository).findAllProductsByClientId(clientIdRequest);
//	}
//	
//}
