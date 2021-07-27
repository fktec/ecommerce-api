package br.com.ecommerce.wishlist.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import br.com.ecommerce.mock.util.JsonFormatterComplete;
import br.com.ecommerce.product.domain.Product;
import br.com.ecommerce.product.service.ProductService;
import br.com.ecommerce.util.product.ProductMockTestUtil;
import br.com.ecommerce.util.wishlist.WishlistMockTestUtil;
import br.com.ecommerce.wishlist.common.exceptions.ProductNotFoundException;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistItem;

@ExtendWith(MockitoExtension.class)
@TestPropertySource({ "classpath:application.properties" })
public class WishlistItemEnrichServiceTest  {
	
	@Mock
	private ProductService productService;
	
	@InjectMocks
	private WishlistItemEnrichService wishlistItemEnrichService;
	
	@Test
	public void tA1_testEnrichProducts_Success() throws ProductNotFoundException {
	    // # MOCK		
		String clientId = "1";
		String productId = "1";
		
		List<Product> productsResponse= JsonFormatterComplete.jsonToArray(ProductMockTestUtil.productsArrayResponseJSON, Product.class);
		
		Mockito
	    	.when(productService.findProductById(productId))
	    	.thenReturn(productsResponse.get(0));
	    
	    // # TEST
		String clientIdRequest = clientId;
		String productIdRequest = productId;
	    
		WishlistItem wishlistItemEnriched = wishlistItemEnrichService.enrichProduct(clientIdRequest, productIdRequest);
		String response = JsonFormatterComplete.objectToJson(wishlistItemEnriched);
		String expected =  WishlistMockTestUtil.wishlistItemEnrichedResponseJSON;
		
		assertNotNull(wishlistItemEnriched);
	    assertEquals(JsonFormatterComplete.convert(expected), JsonFormatterComplete.convert(response));
//	    Assert.assertEquals(expected, response);
	    
	    Mockito.verify(productService, times(1)).findProductById(productId);
	}
	
	@Test
	public void tA2_testEnrichProducts_NoContent() throws ProductNotFoundException {
		
	    // # MOCK		
		String clientId = "1";
		String productId = "2";
		
		Mockito
	    	.when(productService.findProductById(productId))
	    	.thenReturn(null);
	    
	    // # TEST
		String clientIdRequest = clientId;
		String productIdRequest = productId;
		
		Exception exception = assertThrows(ProductNotFoundException.class, () -> {
			wishlistItemEnrichService.enrichProduct(clientIdRequest, productIdRequest);
		});
		
		assertNotNull(exception);
		assertEquals("Product not found by id [2]", exception.getMessage());
		
	    Mockito.verify(productService, times(1)).findProductById(productId);
	}
	
}
