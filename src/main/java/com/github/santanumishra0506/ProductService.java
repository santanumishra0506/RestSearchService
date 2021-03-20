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


	public List<Product> searchProductsWithPagination(String keyWord, Integer from, Integer size) {
		// TODO Auto-generated method stub
		return productRepository.searchProductsinPostswithPagination(keyWord, from, size);
	}

	public List<String> getSuggestionTerms(String term) {
		// TODO Auto-generated method stub
		return productRepository.retrieveSuggestions(term);
	}

	

}
