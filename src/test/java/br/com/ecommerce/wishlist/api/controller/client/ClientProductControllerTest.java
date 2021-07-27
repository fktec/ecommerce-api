package br.com.ecommerce.wishlist.api.controller.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.ecommerce.mock.util.JsonFormatterComplete;
import br.com.ecommerce.util.wishlist.WishlistMockTestUtil;
import br.com.ecommerce.wishlist.app.services.WishlistItemService;
import br.com.ecommerce.wishlist.common.exceptions.ClientNotFoundException;
import br.com.ecommerce.wishlist.common.exceptions.ProductNotFoundException;
import br.com.ecommerce.wishlist.common.exceptions.WishlistItemMaxCapacityException;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistItem;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistItemDto;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(value = { "classpath:application.properties" })
public class ClientProductControllerTest  {
	
	@Mock
	private WishlistItemService wishlistItemService; 
	
	@InjectMocks
	private ClientProductController clientProductController;
	
	@Value("${rn.wishlist.item.max-capacity}")
	private Integer wishlistItemMaxCapacity;
	
	@BeforeEach
    public void setup() {
		ReflectionTestUtils.setField(wishlistItemService, "wishlistItemMaxCapacity", 20);
    }
	
	@SuppressWarnings("unchecked")
	@Test
	public void tA1_getAllProductsByClient_Ok() {
	    // # MOCK		
		String clientId = "1";
		List<WishlistItem> wishlistItemsResponse = JsonFormatterComplete.jsonToArray(WishlistMockTestUtil.wishlistItemArrayResponseJSON, WishlistItem.class);
		
	    Mockito.lenient()
	    	.when(wishlistItemService.findAllProductsByClientId(clientId))
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
	    	.when(wishlistItemService.findAllProductsByClientId(clientId))
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
	    	.when(wishlistItemService).checkIfClientExists(clientId);
		
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
	    	.when(wishlistItemService.findProductByClientId(clientId, productId))
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
	    	.when(wishlistItemService.findProductByClientId(clientId, productId))
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
	    	.when(wishlistItemService).checkIfClientExists(clientId);
		
	    // # TEST
	    String clientIdRequest = clientId;
	    String productIdRequest = productId;
	    
	    ResponseEntity<Object> response =  clientProductController.getProductByClient(clientIdRequest, productIdRequest);
	    
	    assertNotNull(response);
	    assertEquals("Client not found by id [10]", response.getBody());
	    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void tC1_addProductByClient_Ok() throws ClientNotFoundException, ProductNotFoundException, WishlistItemMaxCapacityException {
	    // # MOCK		
		String clientId = "10";
		String productId = "1";
		WishlistItemDto wishlistItemDto = WishlistItemDto.of().setProductId(productId);
		
		WishlistItem wishlistItemEnriched =  JsonFormatterComplete.jsonToObject(WishlistMockTestUtil.wishlistItemEnrichedResponseJSON, WishlistItem.class);
		Mockito.lenient()
	    	.when(wishlistItemService.addProductByClient(clientId, wishlistItemDto))
	    	.thenReturn(wishlistItemEnriched);
		
	    // # TEST
	    String clientIdRequest = clientId;
	    String productIdRequest = productId;
	    WishlistItemDto wishlistItemDtoRequest = WishlistItemDto.of().setProductId(productIdRequest);
	    
	    ResponseEntity<Object> response =  clientProductController.addProductByClient(clientIdRequest, wishlistItemDtoRequest);
	    
	    assertNull(response.getBody());
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void tC2_addProductByClient_ProductNotFound() throws ClientNotFoundException, ProductNotFoundException, WishlistItemMaxCapacityException {
	    // # MOCK		
		String clientId = "10";
		String productId = "999";
		
		 Mockito.lenient()
		 	.doThrow(new ProductNotFoundException(productId))
	    	.when(wishlistItemService).addProductByClient(ArgumentMatchers.eq(clientId), ArgumentMatchers.any(WishlistItemDto.class));
		 
	    // # TEST
	    String clientIdRequest = clientId;
	    String productIdRequest = productId;
	    WishlistItemDto wishlistItemDtoRequest = WishlistItemDto.of().setProductId(productIdRequest);
	    
	    ResponseEntity<Object> response =  clientProductController.addProductByClient(clientIdRequest, wishlistItemDtoRequest);
	    
	    assertNotNull(response);
	    assertEquals("Product not found by id [999]", response.getBody());
	    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void tC3_addProductByClient_ClientNotFound() throws ClientNotFoundException {
	    // # MOCK		
		String clientId = "10";
		String productId = "1";
		
		 Mockito
		 	.doThrow(new ClientNotFoundException(clientId))
	    	.when(wishlistItemService).checkIfClientExists(clientId);
		
	    // # TEST
	    String clientIdRequest = clientId;
	    String productIdRequest = productId;
	    WishlistItemDto wishlistItemDtoRequest = WishlistItemDto.of().setProductId(productIdRequest);
	    
	    ResponseEntity<Object> response =  clientProductController.addProductByClient(clientIdRequest, wishlistItemDtoRequest);
	    
	    assertNotNull(response);
	    assertEquals("Client not found by id [10]", response.getBody());
	    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void tC4_addProductByClient_ValidationBadRequest() throws ClientNotFoundException {
	    WishlistItemDto wishlistItemDtoRequest = WishlistItemDto.of()
	    		.setProductId("");
	    
	    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
	    Validator validator = validatorFactory.getValidator();
	    Set<ConstraintViolation<WishlistItemDto>> validate = validator.validate(wishlistItemDtoRequest);
	    
	    assertNotNull(validate);
	    assertEquals(1, validate.size());
	}
	
	@Test
	public void tC5_addProductByClient_ValidationWishlistMaxCapacity() throws ClientNotFoundException, WishlistItemMaxCapacityException, ProductNotFoundException {
		// # MOCK		
		String clientId = "10";
		String productId = "1";
		
		 Mockito
		 	.doThrow(new WishlistItemMaxCapacityException(wishlistItemMaxCapacity))
		 	.when(wishlistItemService).addProductByClient(ArgumentMatchers.eq(clientId), ArgumentMatchers.any(WishlistItemDto.class));
		
	    // # TEST
	    String clientIdRequest = clientId;
	    String productIdRequest = productId;
	    WishlistItemDto wishlistItemDtoRequest = WishlistItemDto.of().setProductId(productIdRequest);
	    
	    ResponseEntity<Object> response =  clientProductController.addProductByClient(clientIdRequest, wishlistItemDtoRequest);
	    
	    assertNotNull(response);
	    assertEquals("Cannot add new products to favorites list - limit [{0}]", response.getBody());
	    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void tD1_removeProductsByClient_NoContent() throws ClientNotFoundException {
	    // # MOCK		
		String clientId = "10";
		List<String> productIds = Arrays.asList("1", "2");
		
	    // # TEST
	    String clientIdRequest = clientId;
	    List<String> productIdsRequest = productIds;
	    
	    ResponseEntity<Object> response =  clientProductController.removeProductsByClient(clientIdRequest, productIdsRequest);
	    
	    assertNull(response.getBody());
	    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
}
