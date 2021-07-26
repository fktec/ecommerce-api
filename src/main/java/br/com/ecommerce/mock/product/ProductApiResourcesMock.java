package br.com.ecommerce.mock.product;

import org.springframework.core.io.ClassPathResource;

import br.com.ecommerce.mock.util.ResourceReader;

public class ProductApiResourcesMock {

	private static final String PRODUCT_PATH = "mocks/product/";
	
	private ProductApiResourcesMock() {}

	public static final String productsArrayResponseJSON =
			ResourceReader.asString(new ClassPathResource(PRODUCT_PATH + "payload-success-response_PRODUCTS.json"));
}
