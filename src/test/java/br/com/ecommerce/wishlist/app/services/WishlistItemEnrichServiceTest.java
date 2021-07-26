package br.com.ecommerce.wishlist.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.Collections;
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
import br.com.ecommerce.wishlist.domain.wishlist.WishlistDto;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistItem;

@ExtendWith(MockitoExtension.class)
@TestPropertySource({ "classpath:application.properties" })
public class WishlistItemEnrichServiceTest  {
	
	@Mock
	private ProductService productService;
	
	@InjectMocks
	private WishlistItemEnrichService wishlistItemEnrichService;
	
	@Test
	public void tA1_testEnrichProducts_Success() {
	    // # MOCK		
		String clientId = "1";
		List<String> productIds = Arrays.asList("1", "2");
		
		WishlistDto wishlistDto = WishlistDto.of()
			.setClientId(clientId)
			.setProductIds(productIds);
		
		List<Product> productsResponse= JsonFormatterComplete.jsonToArray(ProductMockTestUtil.productsArrayResponseJSON, Product.class);
		
		Mockito
	    	.when(productService.findProductsByIds(wishlistDto.getProductIds()))
	    	.thenReturn(productsResponse);
	    
	    // # TEST
		WishlistDto wishlistDtoRequest = wishlistDto;
	    
		List<WishlistItem> wishlistItemEnriched = wishlistItemEnrichService.enrichProducts(wishlistDtoRequest);
		String response = JsonFormatterComplete.objectToJson(wishlistItemEnriched);
		String expected =  WishlistMockTestUtil.wishlistItemEnrichedResponseJSON;
		
		assertNotNull(wishlistItemEnriched);
		assertEquals(2, wishlistItemEnriched.size());
	    assertEquals(JsonFormatterComplete.convert(expected), JsonFormatterComplete.convert(response));
//	    Assert.assertEquals(expected, response);
	    
	    Mockito.verify(productService, times(1)).findProductsByIds(wishlistDto.getProductIds());
	}
	
	@Test
	public void tA2_testEnrichProducts_NoContent() {
	    // # MOCK		
		String clientId = "1";
		List<String> productIds = Arrays.asList("3", "4");
		
		WishlistDto wishlistDto = WishlistDto.of()
			.setClientId(clientId)
			.setProductIds(productIds);
		
		Mockito
	    	.when(productService.findProductsByIds(wishlistDto.getProductIds()))
	    	.thenReturn(Collections.emptyList());
	    
	    // # TEST
		WishlistDto wishlistDtoRequest = wishlistDto;
	    
		List<WishlistItem> wishlistItemEnriched = wishlistItemEnrichService.enrichProducts(wishlistDtoRequest);
		String response = JsonFormatterComplete.objectToJson(wishlistItemEnriched);
		String expected =  "[]";
		
		assertNotNull(wishlistItemEnriched);
		assertEquals(0, wishlistItemEnriched.size());
	    assertEquals(JsonFormatterComplete.convert(expected), JsonFormatterComplete.convert(response));
//	    assertEquals(expected, response);
	    
	    Mockito.verify(productService, times(1)).findProductsByIds(wishlistDto.getProductIds());
	}
	
}
