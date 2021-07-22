package br.com.ecommerce.wishlist.api.resources.client;

import java.text.MessageFormat;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("clients/{clientId}/products")
public class ClientProductResource {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getProductsByClient(@PathVariable("clientId") Long clientId) {
		String responseMessage = MessageFormat.format("Buscando todos os produtos do client {0}", clientId);
		return ResponseEntity.ok(responseMessage);
	}
	
	@GetMapping(path = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getProductsByClient(@PathVariable("clientId") Long clientId, @PathVariable("productId") Long productId) {
		String responseMessage = MessageFormat.format("Buscando produto {0} do client {1}", productId, clientId);
		return ResponseEntity.ok(responseMessage);
	}
	
	@PostMapping
	public ResponseEntity<String> addProduct() {
		return ResponseEntity.ok("## TESTE RESPONSE ##");
	}
}
