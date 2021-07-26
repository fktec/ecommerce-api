package br.com.ecommerce.wishlist.api.resources.client;

import java.util.List;

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

import br.com.ecommerce.wishlist.app.services.WishlistService;
import br.com.ecommerce.wishlist.domain.wishlist.Wishlist;
import br.com.ecommerce.wishlist.domain.wishlist.WishlistProduct;

@RestController
@RequestMapping("clients/{clientId}/products")
public class ClientProductResource {
	
	@Autowired
	private WishlistService wishlistService; 
	
	/**
	 * 
	 * TODO: OBS:
	 * - Decidi não trabalhar com DTO's nesse momento.
	 *  
	 */
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Wishlist> getAllProductsByClient(@PathVariable("clientId") String clientId) {
		final Wishlist wishlist = wishlistService.findWishlistByClient(clientId);
		
		if (wishlist == null) 
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return  new ResponseEntity<>(wishlist, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{productId}/check", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Wishlist> getProductByClient(@PathVariable("clientId") String clientId, @PathVariable("productId") String productId) {
		final Wishlist wishlist = wishlistService.findProductByClient(clientId, productId);
		
		if (wishlist == null) 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return  new ResponseEntity<>(wishlist, HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addProductsByClient(@PathVariable("clientId") String clientId, @RequestBody List<String> productIds) {
		wishlistService.addProductsByClient(clientId, productIds);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> removeProductsByClient(@PathVariable("clientId") String clientId, @RequestBody List<String> productIds) {
		wishlistService.removeProductsByClient(clientId, productIds);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
