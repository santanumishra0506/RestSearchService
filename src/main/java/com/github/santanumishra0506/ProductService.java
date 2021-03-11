package com.github.santanumishra0506;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> getAllProductInfo() {
		return productRepository.findAllProductDetailsFromElastic();
	}

	public List<Product> getProductsByName(String productName) {
		// TODO Auto-generated method stub
		return productRepository.findAllProductInfoFromElastic(productName);
	}

	public List<Product> searchProducts(String keyWord) {
		// TODO Auto-generated method stub
		return productRepository.searchProductsinPosts(keyWord);
	}

	

}
