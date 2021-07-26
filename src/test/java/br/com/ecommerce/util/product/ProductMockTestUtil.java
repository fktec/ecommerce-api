package br.com.ecommerce.util.product;

import org.springframework.core.io.ClassPathResource;

import br.com.ecommerce.mock.util.ResourceReader;

public class ProductMockTestUtil {

	private static final String PRODUCT_PATH = "mocks/product/";
	
	private ProductMockTestUtil() {}

	public static final String productsArrayResponseJSON =
			ResourceReader.asString(new ClassPathResource(PRODUCT_PATH + "payload-success-array_PRODUCT.json"));
	
	
}
