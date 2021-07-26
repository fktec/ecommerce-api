package br.com.ecommerce.product.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.ecommerce.mock.product.ProductApiResourcesMock;
import br.com.ecommerce.mock.util.JsonFormatterComplete;
import br.com.ecommerce.product.domain.Product;

@Service
public class ProductService {

	@SuppressWarnings("unchecked")
	public Product findProductById(String id) {
		String productsArrayJson = ProductApiResourcesMock.productsArrayResponseJSON;
		
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
