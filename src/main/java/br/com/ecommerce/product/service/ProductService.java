package br.com.ecommerce.product.service;

import java.util.Collections;
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
	public List<Product> findProductsByIds(List<String> ids) {
		String productsArrayJson = ProductApiResourcesMock.productsArrayResponseJSON;
		
		List<Map<String, Object>> productsMap = JsonFormatterComplete.jsonToObject(productsArrayJson, List.class);
		List<Product> products = productsMap.stream()
			.map(pm -> JsonFormatterComplete.jsonToObject(JsonFormatterComplete.objectToJson(pm),Product.class))
			.collect(Collectors.toList());

		if (products != null && !products.isEmpty()) {
			return products.stream()
					.filter(p -> ids.contains(p.getId()))
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
}
