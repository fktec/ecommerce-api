package br.com.ecommerce.wishlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "br.com.ecommerce" })
public class EcommerceWishlistApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(EcommerceWishlistApiApplication.class, args);
	}

}
