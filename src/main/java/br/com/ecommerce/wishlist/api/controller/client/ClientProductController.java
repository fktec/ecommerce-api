package br.com.ecommerce.wishlist.api.controller.client;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ecommerce.wishlist.app.services.WishlistItemService;
import br.com.ecommerce.wishlist.common.exceptions.ClientNotFoundException;
import br.com.ecommerce.wishlist.common.exceptions.ProductNotFoundException;
import br.com.ecommerce.wishlist.common.exceptions.WishlistItemMaxCapacityException;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistItem;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistItemDto;

@RestController
@RequestMapping("clients/{clientId}/products")
public class ClientProductController {
	
	@Autowired
	private WishlistItemService wishlistService; 
	
	/**
	 * TODO: MELHORIAS:
	 * - Handler exception para customizar e padronizar as mensagens de resposta. (por isso, algumas respostas estão como OBJECT, devido a geração das mensagens de erro, enquanto isso)
	 */
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllProductsByClient(@PathVariable(name = "clientId", required = true) String clientId) {
		try {
			wishlistService.checkIfClientExists(clientId);
			List<WishlistItem> wishlistItems = wishlistService.findAllProductsByClientId(clientId);
			
			if (wishlistItems == null || wishlistItems.isEmpty()) 
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return  new ResponseEntity<>(wishlistItems, HttpStatus.OK);
			
		} catch (ClientNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(path = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getProductByClient(@PathVariable(name = "clientId", required = true) String clientId, @PathVariable(name = "productId", required = true) String productId) {
		try {
			wishlistService.checkIfClientExists(clientId);
			final WishlistItem wishlistItem = wishlistService.findProductByClientId(clientId, productId);
			
			if (wishlistItem == null) 
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return  new ResponseEntity<>(wishlistItem, HttpStatus.OK);
			
		} catch (ClientNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addProductByClient(@PathVariable(name = "clientId", required = true) String clientId, @Valid @RequestBody WishlistItemDto wishlistItemDto) {
		try {
			wishlistService.checkIfClientExists(clientId);
			wishlistService.addProductByClient(clientId, wishlistItemDto);
			return new ResponseEntity<>(HttpStatus.OK);
			
		} catch (ClientNotFoundException | ProductNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (WishlistItemMaxCapacityException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} 
	}
	
	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> removeProductsByClient(@PathVariable(name = "clientId", required = true) String clientId, @PathVariable(name = "productId", required = true) String productId) {
		wishlistService.removeProductsByClient(clientId, productId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
