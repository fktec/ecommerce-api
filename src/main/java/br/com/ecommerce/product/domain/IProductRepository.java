package br.com.ecommerce.product.domain;

public interface IProductRepository {
	
	public Product findProductById(String id);
}
