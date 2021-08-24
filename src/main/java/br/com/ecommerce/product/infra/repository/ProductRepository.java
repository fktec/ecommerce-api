package br.com.ecommerce.product.infra.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import br.com.ecommerce.mock.product.ProductApiResourcesMock;
import br.com.ecommerce.mock.util.JsonFormatterComplete;
import br.com.ecommerce.product.domain.IProductRepository;
import br.com.ecommerce.product.domain.Product;

@Repository(value = "ProductRepository")
public class ProductRepository implements IProductRepository {

	@Override
	public Product findProductById(String id) {
		
		/**
		 *  MOCK
		 */
		String productsArrayJson = ProductApiResourcesMock.productsArrayResponseJSON;
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> productsMap = JsonFormatterComplete.jsonToObject(productsArrayJson, List.class);
		List<Product> products = productsMap.stream()
			.map(pm -> JsonFormatterComplete.jsonToObject(JsonFormatterComplete.objectToJson(pm),Product.class))
			.collect(Collectors.toList());

		for (Product product : products) {
			if (product.getId().equals(id))
				return product;
		}
		return null;
	}

}
