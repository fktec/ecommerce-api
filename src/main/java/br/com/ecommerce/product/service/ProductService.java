package br.com.ecommerce.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.ecommerce.product.domain.IProductRepository;
import br.com.ecommerce.product.domain.Product;

@Service
public class ProductService {
	
	@Autowired
	@Qualifier("ProductRepository")
	private IProductRepository productRepository;

	public Product findProductById(String id) {
		return productRepository.findProductById(id);
	}
}
