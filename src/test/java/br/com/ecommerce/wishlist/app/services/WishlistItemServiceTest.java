package br.com.ecommerce.wishlist.app.services;

import static org.mockito.Mockito.times;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;

import br.com.ecommerce.mock.util.JsonFormatterComplete;
import br.com.ecommerce.util.wishlist.WishlistMockTestUtil;
import br.com.ecommerce.wishlist.domain.wishlist.IWishlistItemRepository;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistItem;

@ExtendWith(MockitoExtension.class)
@TestPropertySource({ "classpath:application.properties" })
public class WishlistItemServiceTest  {
	
	@Mock
	private IWishlistItemRepository wishlistItemRepository;
	
	@Mock
	private MongoTemplate mongoTemplate;
	
	@Mock
	private WishlistItemEnrichService wishlistItemEnrichService;
	
	@InjectMocks
	private WishlistItemService wishlistItemService;
	
	
	@Test
	public void tA1_addProductsByClient_SaveSuccess() {
	    // # MOCK		
		String clientId = "1";
		String productId ="1";
		List<WishlistItem> wishlistItemEnrichedResponse = JsonFormatterComplete.jsonToArray(WishlistMockTestUtil.wishlistItemArrayEnrichedResponseJSON, WishlistItem.class);
		
		Mockito.lenient()
	    	.when(wishlistItemRepository.countByClientIdAndProductId(clientId, productId))
	    	.thenReturn(0l);

		Mockito.lenient()
	    	.when(wishlistItemEnrichService.enrichProduct(clientId, productId))
	    	.thenReturn(wishlistItemEnrichedResponse.get(0));
//	    
	    // # TEST
	    String clientIdRequest = clientId;
	    String productIdRequest = productId;
	    
	    wishlistItemService.addProductByClient(clientIdRequest, productIdRequest);

	    ArgumentCaptor<WishlistItem> argument = ArgumentCaptor.forClass(WishlistItem.class);
	    Mockito.verify(mongoTemplate, times(1)).insert(argument.capture());
	}
	
	@Test
	public void tA2_addProductsByClient_UpdateSuccess() {
	    // # MOCK		
		String clientId = "1";
		String productId ="1";
//		
	    Mockito.lenient()
	    	.when(wishlistItemRepository.countByClientIdAndProductId(clientId, productId))
	    	.thenReturn(1l);
	    
	    List<WishlistItem> wishlistItemEnrichedResponse = JsonFormatterComplete.jsonToArray(WishlistMockTestUtil.wishlistItemArrayEnrichedResponseJSON, WishlistItem.class);
	    Mockito.lenient()
	    	.when(wishlistItemRepository.findProductByClientId(clientId, productId))
	    	.thenReturn(wishlistItemEnrichedResponse.get(0));
	    
	    WishlistItem wishlistItemEnriched =  JsonFormatterComplete.jsonToObject(WishlistMockTestUtil.wishlistItemEnrichedResponseJSON, WishlistItem.class);
		Mockito.lenient()
	    	.when(wishlistItemEnrichService.enrichProduct(clientId, productId))
	    	.thenReturn(wishlistItemEnriched);
//	    
	    // # TEST
		String clientIdRequest = clientId;
	    String productIdRequest = productId;
	    
	    wishlistItemService.addProductByClient(clientIdRequest, productIdRequest);

	    ArgumentCaptor<WishlistItem> argument = ArgumentCaptor.forClass(WishlistItem.class);
	    Mockito.verify(mongoTemplate, times(1)).save(argument.capture(),  ArgumentMatchers.eq(WishlistItem.DOCUMENT_NAME));
	}
	
	@Test
	public void tA3_addProductsByClient_Fail() {
	    // # MOCK		
		Mockito.lenient()
	    	.when(wishlistItemEnrichService.enrichProduct(null, null))
	    	.thenReturn(null);
//	    
	    // # TEST
	    wishlistItemService.addProductByClient(null, null);

	    ArgumentCaptor<WishlistItem> argument = ArgumentCaptor.forClass(WishlistItem.class);
	    Mockito.verify(mongoTemplate, times(0)).insert(argument.capture());
	    Mockito.verify(mongoTemplate, times(0)).save(argument.capture(),  ArgumentMatchers.eq(WishlistItem.DOCUMENT_NAME));
	}

}
