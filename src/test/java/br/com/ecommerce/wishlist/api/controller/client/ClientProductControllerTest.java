package br.com.ecommerce.wishlist.api.controller.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import br.com.ecommerce.mock.util.JsonFormatterComplete;
import br.com.ecommerce.util.wishlist.WishlistMockTestUtil;
import br.com.ecommerce.wishlist.app.services.WishlistItemService;
import br.com.ecommerce.wishlist.common.exceptions.ClientNotFoundException;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistItem;

@ExtendWith(MockitoExtension.class)
@TestPropertySource({ "classpath:application.properties" })
public class ClientProductControllerTest  {
	
	@Mock
	private WishlistItemService wishlistService; 
	
	@InjectMocks
	private ClientProductController clientProductController;
	
	@SuppressWarnings("unchecked")
	@Test
	public void tA1_getAllProductsByClient_Ok() {
	    // # MOCK		
		String clientId = "1";
		List<WishlistItem> wishlistItemsResponse = JsonFormatterComplete.jsonToArray(WishlistMockTestUtil.wishlistItemArrayResponseJSON, WishlistItem.class);
		
	    Mockito.lenient()
	    	.when(wishlistService.findAllProductsByClientId(clientId))
	    	.thenReturn(wishlistItemsResponse);
	    
	    // # TEST
	    String clientIdRequest = clientId;
	    
	    ResponseEntity<Object> response =  clientProductController.getAllProductsByClient(clientIdRequest);
	    List<WishlistItem> body = (List<WishlistItem>) response.getBody();

	    assertNotNull(response);
	    assertNotNull(body);
	    assertEquals(2, body.size());
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void tA2_getAllProductsByClient_NoContent() {
	    // # MOCK		
		String clientId = "1";
		
	    Mockito.lenient()
	    	.when(wishlistService.findAllProductsByClientId(clientId))
	    	.thenReturn(Collections.emptyList());
	    
	    // # TEST
	    String clientIdRequest = clientId;
	    
	    ResponseEntity<Object> response =  clientProductController.getAllProductsByClient(clientIdRequest);
	    List<WishlistItem> body = (List<WishlistItem>) response.getBody();

	    assertNotNull(response);
	    assertNull(body);
	    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	
	@Test
	public void tA3_getAllProductsByClient_ClientNotFound() throws ClientNotFoundException {
	    // # MOCK		
		String clientId = "10";
		
		 Mockito
		 	.doThrow(new ClientNotFoundException(clientId))
	    	.when(wishlistService).checkIfClientExists(clientId);
		
	    // # TEST
	    String clientIdRequest = clientId;
	    
	    ResponseEntity<Object> response =  clientProductController.getAllProductsByClient(clientIdRequest);
	    
	    assertNotNull(response);
	    assertEquals("Client not found by id [10]", response.getBody());
	    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void tB1_getProductByClient_Ok() {
	    // # MOCK		
		String clientId = "1";
		String productId = "1";
		List<WishlistItem> wishlistItemsResponse = JsonFormatterComplete.jsonToArray(WishlistMockTestUtil.wishlistItemArrayResponseJSON, WishlistItem.class);
		
	    Mockito.lenient()
	    	.when(wishlistService.findProductByClientId(clientId, productId))
	    	.thenReturn(wishlistItemsResponse.get(0));
	    
	    // # TEST
	    String clientIdRequest = clientId;
	    String productIdRequest = productId;
	    
	    ResponseEntity<Object> response =  clientProductController.getProductByClient(clientIdRequest, productIdRequest);
	    WishlistItem body = (WishlistItem) response.getBody();

	    assertNotNull(response);
	    assertNotNull(body);
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void tB2_getProductByClient_NotFound() {
	    // # MOCK		
		String clientId = "1";
		String productId = "1";
		
	    Mockito.lenient()
	    	.when(wishlistService.findProductByClientId(clientId, productId))
	    	.thenReturn(null);
	    
	    // # TEST
	    String clientIdRequest = clientId;
	    String productIdRequest = productId;
	    
	    ResponseEntity<Object> response =  clientProductController.getProductByClient(clientIdRequest, productIdRequest);
	    WishlistItem body = (WishlistItem) response.getBody();

	    assertNull(body);
	    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void tB3_getProductByClient_ClientNotFound() throws ClientNotFoundException {
	    // # MOCK		
		String clientId = "10";
		String productId = "1";
		
		 Mockito
		 	.doThrow(new ClientNotFoundException(clientId))
	    	.when(wishlistService).checkIfClientExists(clientId);
		
	    // # TEST
	    String clientIdRequest = clientId;
	    String productIdRequest = productId;
	    
	    ResponseEntity<Object> response =  clientProductController.getProductByClient(clientIdRequest, productIdRequest);
	    
	    assertNotNull(response);
	    assertEquals("Client not found by id [10]", response.getBody());
	    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void tC1_addProductsByClient_Ok() {
	    // # MOCK		
		String clientId = "1";
		String productId = "1";
		
	    // # TEST
	    String clientIdRequest = clientId;
	    String productIdRequest = productId;
	    
	    ResponseEntity<Object> response =  clientProductController.getProductByClient(clientIdRequest, productIdRequest);
	    WishlistItem body = (WishlistItem) response.getBody();

	    assertNotNull(response);
	    assertNotNull(body);
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
