package br.com.ecommerce.wishlist.api.resources.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ecommerce.wishlist.app.services.WishlistService;
import br.com.ecommerce.wishlist.domain.wishlist.Wishlist;

@RestController
@RequestMapping("clients/{clientId}")
public class ClientResource {
	
	@Autowired
	private WishlistService wishlistService; 
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Wishlist> getWishlistByClient(@PathVariable("clientId") String clientId) {
		final Wishlist wishlist = wishlistService.findWishlistByClient(clientId);
		
		if (wishlist == null) 
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return  new ResponseEntity<>(wishlist, HttpStatus.OK);
	}

}
